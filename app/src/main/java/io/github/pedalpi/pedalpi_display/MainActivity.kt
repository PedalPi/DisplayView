package io.github.pedalpi.pedalpi_display

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import io.github.pedalpi.pedalpi_display.communication.message.request.Messages
import io.github.pedalpi.pedalpi_display.communication.message.response.ResponseMessage
import io.github.pedalpi.pedalpi_display.communication.message.response.ResponseVerb
import io.github.pedalpi.pedalpi_display.communication.server.Server
import io.github.pedalpi.pedalpi_display.effects.EffectsActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class MainActivity : AppCompatActivity() {

    private lateinit var number: TextView
    private lateinit var name: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        supportActionBar?.hide()

        this.number = findViewById(R.id.pedalboard_number) as TextView
        this.name = findViewById(R.id.pedalboard_name) as TextView

        number.text = "--"
        name.text = "CONNECTING"

        Server.getInstance().setListener({ onMessage(it) })
        Server.getInstance().sendBroadcast(Messages.CURRENT_PEDALBOARD_DATA)

        val button = findViewById(R.id.button) as Button
        button.setOnClickListener({goToEffectsList()})
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    private fun onMessage(message : ResponseMessage) {
        if (message.type == ResponseVerb.RESPONSE && message.request == Messages.CURRENT_PEDALBOARD_DATA
                || message.type == ResponseVerb.EVENT && message.content["type"].string == "CURRENT") {
            runOnUiThread({
                val id = Data.getInstance().currentPedalboardPosition
                val pedalboard = Data.getInstance().currentPedalboard

                number.text = if (id < 10) "0"+id else id.toString()
                name.text = pedalboard["name"].string
            })
        }
    }

    private fun goToEffectsList() {
        val intent = Intent(baseContext, EffectsActivity::class.java)
        startActivity(intent)
    }

    public override fun onResume() {
        super.onResume()

        Server.getInstance().setListener({ onMessage(it) })
    }
}
