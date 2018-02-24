package io.github.pedalpi.displayview.params

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.ListView
import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonElement
import io.github.pedalpi.displayview.Data
import io.github.pedalpi.displayview.R
import io.github.pedalpi.displayview.communication.message.response.ResponseMessage
import io.github.pedalpi.displayview.communication.server.Server
import io.github.pedalpi.displayview.effects.EffectsActivity


class ParamsActivity : AppCompatActivity() {

    private var messageReceived = false
    private lateinit var effect: JsonElement
    private lateinit var plugin: JsonElement

    private lateinit var listView: ListView
    private lateinit var adapter: ParamsListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_params)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val index = intent.getIntExtra(EffectsActivity.EFFECT_INDEX, 0)
        this.effect = Data.currentPedalboard["effects"][index]
        this.plugin = Data.plugin(effect["plugin"].string)

        title = title(this.plugin)

        populateViews()

        Server.getInstance().setListener({ onMessage(it) })
    }

    private fun title(plugin: JsonElement): String {
        return plugin["name"].string
    }

    private fun populateViews() {
        this.listView = findViewById(R.id.paramsList) as ListView
        this.adapter = ParamsListItemAdapter(this, generateData(this.effect, this.plugin))

        this.listView.adapter = adapter
        this.adapter.notifyDataSetChanged()
    }

    private fun generateData(effect: JsonElement, plugin: JsonElement): List<ParamsListItemDTO> {
        val data = ArrayList<ParamsListItemDTO>()

        val params = effect["params"].array
        val pluginControls = plugin["ports"]["control"]["input"].array

        for (i in (0 until params.array.size()))
            data.add(ParamsListItemDTO(params[i], pluginControls[i]))

        return data
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    /*
    private fun generateParameter(container: LinearLayout, parameter: Parameter): Any {
        val linearLayout = LinearLayout(ContextThemeWrapper(container.context, LinearLayout.HORIZONTAL))

        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM.toFloat())
        params.setMargins(0, 20, 0, 0)

        linearLayout.layoutParams = params
        linearLayout.orientation = LinearLayout.VERTICAL

        val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val element: Any

        //Log.i("CMBOBOX", String.valueOf(parameter.isCombobox()));
        //Log.i("TOGGLE", String.valueOf(parameter.isToggle()));
        //Log.i("KNOB", String.valueOf(parameter.isKnob()));

        if (parameter.isCombobox()) {

            element = createSpinner(linearLayout, parameter, layoutParams)
        } else if (parameter.isToggle()) {

            element = createButton(linearLayout, parameter, layoutParams) //createToggle();
        } else {

            element = createSeekbar(linearLayout, parameter, layoutParams)
        }
        container.addView(linearLayout)
        return element
    }

    private fun createButton(container: LinearLayout, parameter: Parameter, layoutParams: LinearLayout.LayoutParams): View {
        val context = container.context

        val toggleButton = ToggleButton(context)
        toggleButton.setText(parameter.getName())
        toggleButton.gravity = Gravity.CENTER
        toggleButton.setTextAppearance(context, R.style.ButtonParamToggle)

        val isChecked = parameter.getValue() === 1
        toggleButton.setBackgroundColor(if (isChecked) Color.rgb(60, 179, 113) else Color.rgb(178, 34, 34))

        toggleButton.setOnClickListener {
            val isChecked = parameter.getValue() === 1
            val newChecked = !isChecked
            val newValue = if (newChecked) 1 else 0

            parameter.setValue(newValue)

            toggleButton.setText(parameter.getName())
            toggleButton.setBackgroundColor(if (newChecked) Color.rgb(60, 179, 113) else Color.rgb(178, 34, 34))

            updateToServer(parameter)
        }

        container.addView(toggleButton, layoutParams)
        return toggleButton
    }

    private fun createSpinner(container: LinearLayout, parameter: Parameter, layoutParams: LinearLayout.LayoutParams): View {
        val context = container.context


        parametro1 = findViewById(R.id.spinner) as Spinner
        val adapter = ArrayAdapter.createFromResource(this, R.array.Parâmetro_1, android.R.layout.simple_spinner_dropdown_item)
        parametro1.adapter = adapter

        return parametro1
    }

    fun createSeekbar(container: LinearLayout, parameter: Parameter, layoutParams: LinearLayout.LayoutParams): ParamSeekbar {
        val seekbar = ParamSeekbar(container, parameter, layoutParams)
        seekbar.setListener(this)

        return seekbar
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(PatchActivity.PATCH, this.patch)
        intent.putExtra(PatchActivity.SETTED_CURRENT_PATCH, this.messageReceived)

        messageReceived = false

        setResult(0, intent)
        finish()
    }*/

    private fun onMessage(message: ResponseMessage) {
        /*
        this.patch = MessageProcessor.process(message, this.patch)

        if (message.getType() === ProtocolType.PARAM) {
            val data = message.getContent()
            val effectIndex = message.getContent().getInt("effect")
            val paramIndex = data.getInt("param")

            val parameter = this.patch.getEffects().get(effectIndex).getParameters().get(paramIndex)
            updateParamView(parameter, this.views!![parameter.getIndex()])
        } else if (message.getType() === ProtocolType.PATCH) {
            messageReceived = true
            onBackPressed()
        }*/
    }

    /*
    private fun updateParamView(parameter: Parameter, `object`: Any) {
        Log.i("adas", parameter.toString())
        runOnUiThread {
            /*if (parameter.isCombobox())
                    updatesValue
                else if (parameter.isToggle())
                    parameter.setValue();
                else {*/
            val seekbar = `object` as ParamSeekbar
            seekbar.refreshView()
            //}
        }
    }*/

    /*
    private fun onChange(parameter: Parameter) {
        updateToServer(parameter)
    }

    private fun updateToServer(parameter: Parameter) {
        val message = MessageProcessor.generateUpdateParamValue(this.effect, parameter)
        Server.getInstance().send(message)
    }*/
}
