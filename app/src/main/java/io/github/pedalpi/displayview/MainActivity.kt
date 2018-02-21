package io.github.pedalpi.displayview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import io.github.pedalpi.displayview.communication.message.request.Messages
import io.github.pedalpi.displayview.communication.message.response.EventMessage
import io.github.pedalpi.displayview.communication.message.response.EventType
import io.github.pedalpi.displayview.communication.message.response.ResponseMessage
import io.github.pedalpi.displayview.communication.message.response.ResponseVerb
import io.github.pedalpi.displayview.communication.server.Server
import io.github.pedalpi.displayview.effects.EffectsActivity
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
        //Server.getInstance().sendBroadcast(Messages.CURRENT_PEDALBOARD_DATA)
        //Server.getInstance().sendBroadcast(Messages.PLUGINS)

        val button = findViewById(R.id.button) as Button
        button.setOnClickListener({goToEffectsList()})
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    private fun onMessage(message : ResponseMessage) {
        if (message.request isEquivalentTo Messages.CURRENT_PEDALBOARD_DATA
         || message.verb == ResponseVerb.EVENT && EventMessage(message.content).type == EventType.CURRENT) {
            val id = Data.getInstance().currentPedalboardPosition
            val pedalboard = Data.getInstance().currentPedalboard

            runOnUiThread({
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
        Server.getInstance().sendBroadcast(Messages.CURRENT_PEDALBOARD_DATA)
    }
}
