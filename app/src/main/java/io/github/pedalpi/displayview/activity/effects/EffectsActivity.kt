package io.github.pedalpi.displayview.activity.effects

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.ListView
import android.widget.Toast
import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.int
import com.github.salomonbrys.kotson.string
import io.github.pedalpi.displayview.R
import io.github.pedalpi.displayview.activity.params.ParamsActivity
import io.github.pedalpi.displayview.communication.message.request.Messages
import io.github.pedalpi.displayview.communication.message.response.EventMessage
import io.github.pedalpi.displayview.communication.message.response.EventType
import io.github.pedalpi.displayview.communication.message.response.ResponseMessage
import io.github.pedalpi.displayview.communication.message.response.ResponseVerb
import io.github.pedalpi.displayview.communication.server.Server
import io.github.pedalpi.displayview.model.Data
import java.util.*


class EffectsActivity : AppCompatActivity() {

    companion object {
        @JvmField val EFFECT_INDEX = "EFFECT_INDEX"
    }

    private lateinit var listView: ListView
    private lateinit var adapter: EffectsListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_effects)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = title()

        populateViews()
    }

    private fun title(): String {
        return "%02d - %s".format(Data.pedalboardIndex, Data.currentPedalboard["name"].string)
    }

    private fun populateViews() {
        this.listView = findViewById(R.id.effectsList) as ListView
        this.adapter = EffectsListItemAdapter(this, generateData())

        this.adapter.toggleStatusListener = { requestToggleStatusEffect(it) }
        this.adapter.selectEffectListener = { goToParamsList(it) }

        this.listView.adapter = adapter
        this.adapter.notifyDataSetChanged()
    }

    private fun generateData(): List<EffectsListItemDTO> {
        val elements = ArrayList<EffectsListItemDTO>()

        val pedalboard = Data.currentPedalboard

        pedalboard["effects"].array.mapIndexedTo(elements) {
            index, effectData -> EffectsListItemDTO(index, effectData, Data.plugin(effectData["plugin"].string))
        }

        return elements
    }

    public override fun onResume() {
        super.onResume()

        Server.setListener({ onMessage(it) })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun goToParamsList(effect : EffectsListItemDTO) {
        val intent = Intent(baseContext, ParamsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra(EffectsActivity.EFFECT_INDEX, effect.index)

        startActivityForResult(intent, 0)
    }

    private fun requestToggleStatusEffect(effect: EffectsListItemDTO) {
        Server.sendBroadcast(Messages.CURRENT_PEDALBOARD_TOGGLE_EFFECT(effect.index))
    }

    private fun onMessage(message: ResponseMessage) {
        if (message.verb == ResponseVerb.ERROR)
            runOnUiThread({
                Toast.makeText(applicationContext, message.content["message"].string, Toast.LENGTH_SHORT).show()
            })

        else if (message.verb == ResponseVerb.EVENT) {
            val event = EventMessage(message.content)

            if (event.type == EventType.CURRENT)
                onSupportNavigateUp()

            else if (event.type == EventType.EFFECT_TOGGLE) {
                val index = event.content["effect"].int

                toggleStatusEffectView(this.adapter[index])
            } else {
                //se for de pedalboard ou de param, é bom pegar os dados da aplicação
            }
        }
    }

    private fun toggleStatusEffectView(dto: EffectsListItemDTO) {
        runOnUiThread {
            dto.viewHolder.update()
        }
    }
}