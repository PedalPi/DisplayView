package io.github.pedalpi.displayview.params

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.ListView
import android.widget.Toast
import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.int
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonElement
import io.github.pedalpi.displayview.Data
import io.github.pedalpi.displayview.R
import io.github.pedalpi.displayview.communication.message.request.Messages
import io.github.pedalpi.displayview.communication.message.response.EventMessage
import io.github.pedalpi.displayview.communication.message.response.EventType
import io.github.pedalpi.displayview.communication.message.response.ResponseMessage
import io.github.pedalpi.displayview.communication.message.response.ResponseVerb
import io.github.pedalpi.displayview.communication.server.Server
import io.github.pedalpi.displayview.effects.EffectsActivity
import io.github.pedalpi.displayview.popToRoot


class ParamsActivity : AppCompatActivity() {
    private var index: Int = 0
    private lateinit var effect: JsonElement
    private lateinit var plugin: JsonElement

    private lateinit var listView: ListView
    private lateinit var adapter: ParamsListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_params)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.index = intent.getIntExtra(EffectsActivity.EFFECT_INDEX, 0)
        this.effect = Data.currentPedalboard["effects"][index]
        this.plugin = Data.plugin(effect["plugin"].string)

        title = title(this.plugin)

        populateViews()

        Server.setListener({ onMessage(it) })
    }

    private fun title(plugin: JsonElement): String {
        return plugin["name"].string
    }

    private fun populateViews() {
        this.listView = findViewById(R.id.paramsList) as ListView
        this.adapter = ParamsListItemAdapter(this, generateData(this.effect, this.plugin))

        this.adapter.valueChangeListener = { requestChangeParamValue(it) }

        this.listView.adapter = adapter
        this.adapter.notifyDataSetChanged()
    }

    private fun generateData(effect: JsonElement, plugin: JsonElement): List<ParamsListItemDTO> {
        val data = ArrayList<ParamsListItemDTO>()

        val params = effect["params"].array
        val pluginControls = plugin["ports"]["control"]["input"].array

        for (i in (0 until params.array.size()))
            data.add(ParamsListItemDTO(i, params[i], pluginControls[i]))

        return data
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun requestChangeParamValue(param: ParamsListItemDTO) {
        Server.sendBroadcast(Messages.Companion.PARAM_VALUE_CHANGE(this.index, param.index, param.value))
    }

    private fun onMessage(message: ResponseMessage) {
        if (message.verb == ResponseVerb.ERROR)
            runOnUiThread({
                Toast.makeText(applicationContext, message.content["message"].string, Toast.LENGTH_SHORT).show()
            })

        else if (message.verb == ResponseVerb.EVENT) {
            val event = EventMessage(message.content)

            if (event.type == EventType.CURRENT) {
                this.popToRoot()

            } else if (event.type == EventType.PARAM) {
                if (event.content["effect"].int != index)
                    return

                updateParamValue(adapter[event.content["param"].int])
            }
        }
    }

    private fun updateParamValue(dto: ParamsListItemDTO) {
        runOnUiThread {
            dto.viewHolder.update(applicationContext)
        }
    }
}