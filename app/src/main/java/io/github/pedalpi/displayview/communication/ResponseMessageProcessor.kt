package io.github.pedalpi.displayview.communication

import android.app.Instrumentation
import android.util.Log
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.int
import com.github.salomonbrys.kotson.string
import io.github.pedalpi.displayview.Data
import io.github.pedalpi.displayview.communication.message.request.Messages
import io.github.pedalpi.displayview.communication.message.response.EventMessage
import io.github.pedalpi.displayview.communication.message.response.EventType
import io.github.pedalpi.displayview.communication.message.response.ResponseMessage
import io.github.pedalpi.displayview.communication.message.response.ResponseVerb

class ResponseMessageProcessor : Client.OnMessageListener {
    var listener : Client.OnMessageListener = Client.OnMessageListener {}

    override fun onMessage(message: ResponseMessage) {
        Log.i("OnMSG", message.toString())

        if (message.verb == ResponseVerb.KEYBOARD_EVENT
         && message.content["code"].string == "DOWN")
            onKeyboardEvent(message)

        else if (message.verb == ResponseVerb.RESPONSE)
            onResponseMessage(message)

        else if (message.verb == ResponseVerb.EVENT)
            onEventMessage(message)

        listener.onMessage(message)
    }

    private fun onKeyboardEvent(message: ResponseMessage) {
        Log.i("KEY", message.content["number"].int.toString())

        val inst = Instrumentation()
        inst.sendKeyDownUpSync(message.content["number"].int)
    }

    private fun onResponseMessage(message: ResponseMessage) {
        if (message.request isEquivalentTo Messages.CURRENT_PEDALBOARD_DATA) {
            val index = message.content["pedalboard"].int

            Data.getInstance().currentPedalboardPosition = index
            Data.getInstance().currentPedalboard = message.content["bank"]["pedalboards"][index]

        } else if (message.request isEquivalentTo Messages.PLUGINS) {
            Data.getInstance().plugins = message.content
        }
    }

    private fun onEventMessage(message: ResponseMessage) {
        val event = EventMessage(message.content)

        if (event.type == EventType.CURRENT) {
            val id = event.content["pedalboard"].int

            Data.getInstance().currentPedalboardPosition = id
            Data.getInstance().currentPedalboard = event.content["value"]
        }
    }
}
