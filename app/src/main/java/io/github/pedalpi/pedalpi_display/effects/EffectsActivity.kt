package io.github.pedalpi.pedalpi_display.effects

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ListView
import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import io.github.pedalpi.pedalpi_display.Data
import io.github.pedalpi.pedalpi_display.R
import io.github.pedalpi.pedalpi_display.communication.server.Server
import java.util.*


class EffectsActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: EffectsListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_effects)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (Data.getInstance().currentPedalboard != null) {
            title = title()
        }

        populateViews()

        Server.getInstance().setListener({  }) //onMessage(it)
    }

    private fun title(): String {
        return "%02d - %s".format(Data.getInstance().currentPedalboardPosition, Data.getInstance().currentPedalboard["name"].string)
    }

    private fun populateViews() {
        this.listView = findViewById(R.id.effectsList) as ListView
        this.adapter = EffectsListItemAdapter(this, generateData())

        this.adapter.toggleStatusListener = { Log.i("TOGGLE", it.index.toString()) }
        this.adapter.selectEffectListener = { Log.i("SELECT", it.name) }

        this.listView.adapter = adapter
        this.adapter.notifyDataSetChanged()
    }

    private fun generateData(): List<EffectsListItemDTO> {
        val elements = ArrayList<EffectsListItemDTO>()

        val pedalboard = Data.getInstance().currentPedalboard
        if (pedalboard != null) { //Fixme REMOVE
            pedalboard["effects"].array.mapIndexedTo(elements) { index, value -> EffectsListItemDTO(index, value) }
        }

        for (i in 0..9)
            elements.add(EffectsListItemDTO(i, "{}", "Bett"+i, false))

        return elements
    }

    public override fun onResume() {
        super.onResume()

        //val container = findViewById(R.id.effectsList) as ListView
        //container.removeAllViews()

        //populateViews()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    /*
    private fun createEffectButton(effect: Effect, container: LinearLayout, layoutParams: LinearLayout.LayoutParams): Button {
        val button = Button(this)
        button.setTextAppearance(applicationContext, R.style.Effect)

        button.setBackgroundColor(if (effect.isActive()) Color.rgb(60, 179, 113) else Color.rgb(178, 34, 34))
        button.setText(effect.getName())
        button.setOnClickListener {
            toggleStatusEffect(effect, button)
            updateEffectStatusToServer(effect)
        }

        button.setOnLongClickListener {
            openScreenParamsList(effect)
            true
        }

        container.addView(button, layoutParams)
        return button
    }

    private fun toggleStatusEffect(effect: Effect, button: Button) {
        Log.i("BOTAO", "Effect selected: " + effect)
        effect.toggleStatus()
        runOnUiThread {
            button.setBackgroundColor(if (effect.isActive()) Color.rgb(60, 179, 113) else Color.rgb(178, 34, 34))
            Toast.makeText(applicationContext, "efeito " + effect, Toast.LENGTH_SHORT).show()
        }
    }


    private fun updateEffectStatusToServer(effect: Effect) {
        val message = MessageProcessor.generateEffectStatusToggled(effect)
        Server.getInstance().send(message)
    }

    private fun openScreenParamsList(effect: Effect) {
        val intent = Intent(baseContext, ParamsActivity::class.java)
        intent.putExtra(PatchActivity.PATCH, this.patch)
        intent.putExtra(EffectsActivity.EFFECT_INDEX, effect.getIndex())
        startActivityForResult(intent, 0)
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(PatchActivity.PATCH, this.patch)

        setResult(0, intent) // onActivityResult da tela anterior
        finish()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        Server.getInstance().setListener(this)
        Log.i("MESSAGE", String.valueOf(this.patch!!.getEffects()))
        this.patch = data.extras!!.getSerializable(PatchActivity.PATCH) as Patch
        val settedCurrentPatch = data.getBooleanExtra(PatchActivity.SETTED_CURRENT_PATCH, false)

        if (settedCurrentPatch)
            onBackPressed()

        super.onActivityResult(requestCode, resultCode, data)

    }

    @Throws(JSONException::class)
    fun onMessage(message: Message) {
        Log.i("MESSAGE", message.getType().toString())
        this.patch = MessageProcessor.process(message, this.patch)

        if (message.getType() === ProtocolType.EFFECT) {
            val indexEffect = message.getContent().getInt("index")
            toggleStatusEffectView(this.patch!!.getEffects().get(indexEffect), buttons!![indexEffect])
        } else if (message.getType() === ProtocolType.PATCH) {
            onBackPressed()
        }
    }

    private fun toggleStatusEffectView(effect: Effect, button: Button) {
        runOnUiThread {
            button.setBackgroundColor(if (effect.isActive()) Color.rgb(60, 179, 113) else Color.rgb(178, 34, 34))
            Toast.makeText(applicationContext, "efeito " + effect, Toast.LENGTH_SHORT).show()
        }
    }
    */
}