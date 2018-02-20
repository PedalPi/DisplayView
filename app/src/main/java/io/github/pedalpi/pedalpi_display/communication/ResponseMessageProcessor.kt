package io.github.pedalpi.pedalpi_display.communication

import android.app.Instrumentation
import android.util.Log
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.int
import com.github.salomonbrys.kotson.string
import io.github.pedalpi.pedalpi_display.Data
import io.github.pedalpi.pedalpi_display.communication.message.request.Messages
import io.github.pedalpi.pedalpi_display.communication.message.response.EventMessage
import io.github.pedalpi.pedalpi_display.communication.message.response.EventType
import io.github.pedalpi.pedalpi_display.communication.message.response.ResponseMessage
import io.github.pedalpi.pedalpi_display.communication.message.response.ResponseVerb

class ResponseMessageProcessor : Client.OnMessageListener {
    var listener : Client.OnMessageListener = Client.OnMessageListener {}

    override fun onMessage(message: ResponseMessage) {
        Log.i("OnMSG", message.toString())

        if (message.type == ResponseVerb.KEYBOARD_EVENT && message.content["code"].string == "DOWN") {
            Log.i("KEY", message.content["number"].int.toString())

            val inst = Instrumentation()
            inst.sendKeyDownUpSync(message.content["number"].int)

        } else if (message.type == ResponseVerb.RESPONSE && message.request == Messages.CURRENT_PEDALBOARD_DATA) {
            val index = message.content["pedalboard"].int

            Data.getInstance().currentPedalboardPosition = index
            Data.getInstance().currentPedalboard = message.content["bank"]["pedalboards"][index]

        } else if (message.type == ResponseVerb.EVENT) {
            val event = EventMessage(message.content)

            if (event.type == EventType.CURRENT) {
                val id = event.content["pedalboard"].int

                Data.getInstance().currentPedalboardPosition = id
                Data.getInstance().currentPedalboard = event.content["value"]
            }
        }

        listener.onMessage(message)
    }
}
