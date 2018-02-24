package io.github.pedalpi.displayview.effects

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.WindowManager
import android.widget.ListView
import android.widget.Toast
import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import io.github.pedalpi.displayview.Data
import io.github.pedalpi.displayview.R
import io.github.pedalpi.displayview.communication.message.request.Messages
import io.github.pedalpi.displayview.communication.message.response.EventMessage
import io.github.pedalpi.displayview.communication.message.response.EventType
import io.github.pedalpi.displayview.communication.message.response.ResponseMessage
import io.github.pedalpi.displayview.communication.message.response.ResponseVerb
import io.github.pedalpi.displayview.communication.server.Server
import io.github.pedalpi.displayview.params.ParamsActivity
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
        return "%02d - %s".format(Data.currentPedalboardPosition, Data.currentPedalboard["name"].string)
    }

    private fun populateViews() {
        this.listView = findViewById(R.id.effectsList) as ListView
        this.adapter = EffectsListItemAdapter(this, generateData())

        this.adapter.toggleStatusListener = { toggleStatusEffect(it) }
        this.adapter.selectEffectListener = { goToParamsList(it) }

        this.listView.adapter = adapter
        this.adapter.notifyDataSetChanged()
    }

    private fun generateData(): List<EffectsListItemDTO> {
        val elements = ArrayList<EffectsListItemDTO>()

        val pedalboard = Data.currentPedalboard
        pedalboard["effects"].array.mapIndexedTo(elements) { index, value -> EffectsListItemDTO(index, value) }

        return elements
    }

    public override fun onResume() {
        super.onResume()

        Server.getInstance().setListener({ onMessage(it) })
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

    private fun toggleStatusEffect(effect: EffectsListItemDTO) {
        Server.getInstance().sendBroadcast(Messages.CURRENT_PEDALBOARD_TOGGLE_EFFECT(effect.index))
        //effect.toggleStatus()
        runOnUiThread {
            //Toast.makeText(applicationContext, "efeito " + effect.name, Toast.LENGTH_SHORT).show()
        }
    }

    /*
    private fun updateEffectStatusToServer(effect: Effect) {
        val message = MessageProcessor.generateEffectStatusToggled(effect)
        Server.getInstance().send(message)
    }

    // FIXME Voltou da tela anterior
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        Server.getInstance().setListener(this)
        Log.i("MESSAGE", String.valueOf(this.patch!!.getEffects()))
        this.patch = data.extras!!.getSerializable(PatchActivity.PATCH) as Patch
        val settedCurrentPatch = data.getBooleanExtra(PatchActivity.SETTED_CURRENT_PATCH, false)

        if (settedCurrentPatch)
            onSupportNavigateUp()

        super.onActivityResult(requestCode, resultCode, data)

    }
    */

    private fun onMessage(message: ResponseMessage) {
        Log.i("VERB", message.verb.toString())

        if (message.verb == ResponseVerb.ERROR)
            runOnUiThread({
                Toast.makeText(applicationContext, message.content["message"].string, Toast.LENGTH_SHORT).show()
            })

        else

        if (message.verb == ResponseVerb.EVENT) {
            val event = EventMessage(message.content)

            Log.i("TYPE", event.type.toString())
            if (event.type == EventType.CURRENT)
                onSupportNavigateUp()

            else if (event.type == EventType.EFFECT_TOGGLE)
                //val indexEffect = message.getContent().getInt("index")
                //toggleStatusEffectView(this.patch!!.getEffects().get(indexEffect), buttons!![indexEffect])
                return
            else
                //se for de pedalboard ou de param, é bom pegar os dados da aplicação
                return
        }
    }

    /*
    private fun toggleStatusEffectView(effect: Effect, button: Button) {
        runOnUiThread {
            button.setBackgroundColor(if (effect.isActive()) Color.rgb(60, 179, 113) else Color.rgb(178, 34, 34))
            Toast.makeText(applicationContext, "efeito " + effect, Toast.LENGTH_SHORT).show()
        }
    }*/
}
