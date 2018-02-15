package io.github.pedalpi.pedalpi_display

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import io.github.pedalpi.pedalpi_display.communication.message.response.ResponseMessage
import io.github.pedalpi.pedalpi_display.communication.message.response.ResponseVerb
import io.github.pedalpi.pedalpi_display.communication.server.Server

class MainActivity : AppCompatActivity() {

    private lateinit var number: TextView
    private lateinit var name: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Server.getInstance().setListener({ onMessage(it) })

        //this.number = findViewById<TextView>(R.id.pedalboard_number)
        //this.name = findViewById(R.id.pedalboard_name)

        this.number = findViewById(R.id.pedalboard_number) as TextView
        this.name = findViewById(R.id.pedalboard_name) as TextView

        number.text = "CONNECTING"
        name.text = "Waiting Pedal Pi"
    }

    private fun onMessage(message : ResponseMessage) {
        Log.i("OnMSG", message.toString())

        if (message.type == ResponseVerb.RESPONSE) {
            //number.text = message.content.getInt("index").toString()
            //name.text = message.content.getJSONObject("pedalboard").getString("name")
        }
    }
}
