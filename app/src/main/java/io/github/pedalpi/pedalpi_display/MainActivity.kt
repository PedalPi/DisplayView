package io.github.pedalpi.pedalpi_display

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.int
import com.github.salomonbrys.kotson.string
import io.github.pedalpi.pedalpi_display.communication.message.request.Messages
import io.github.pedalpi.pedalpi_display.communication.message.response.ResponseMessage
import io.github.pedalpi.pedalpi_display.communication.message.response.ResponseVerb
import io.github.pedalpi.pedalpi_display.communication.server.Server
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class MainActivity : AppCompatActivity() {

    private lateinit var number: TextView
    private lateinit var name: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/BlackOpsOne-Regular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build()
        )
        */

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        supportActionBar?.hide()

        //this.number = findViewById<TextView>(R.id.pedalboard_number)
        //this.name = findViewById(R.id.pedalboard_name)

        this.number = findViewById(R.id.pedalboard_number) as TextView
        this.name = findViewById(R.id.pedalboard_name) as TextView

        number.text = "--"
        name.text = "CONNECTING"

        Server.getInstance().setListener({ onMessage(it) })
        Server.getInstance().sendBroadcast(Messages.CURRENT_PEDALBOARD_DATA)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    private fun onMessage(message : ResponseMessage) {
        Log.i("OnMSG", message.toString())

        if (message.type == ResponseVerb.RESPONSE && message.request == Messages.CURRENT_PEDALBOARD_DATA) {
            runOnUiThread({
                val id = message.content["pedalboard"].int

                number.text = if (id < 10) "0"+id else id.toString()
                name.text = message.content["bank"]["pedalboards"][id]["name"].string
            })
        } else if (message.type == ResponseVerb.EVENT && message.content["type"].string == "CURRENT") {
            runOnUiThread({
                val id = message.content["pedalboard"].int

                number.text = if (id < 10) "0"+id else id.toString()
                name.text = message.content["value"]["name"].string
            })
        }
    }
}
