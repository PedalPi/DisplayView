package io.github.pedalpi.displayview.communication

import android.app.Instrumentation
import android.util.Log
import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import io.github.pedalpi.displayview.communication.message.request.Messages
import io.github.pedalpi.displayview.communication.message.response.EventMessage
import io.github.pedalpi.displayview.communication.message.response.EventType
import io.github.pedalpi.displayview.communication.message.response.ResponseMessage
import io.github.pedalpi.displayview.communication.message.response.ResponseVerb
import io.github.pedalpi.displayview.model.Data

class ResponseMessageProcessor : Client.OnMessageListener {
    var listener : Client.OnMessageListener = Client.OnMessageListener {}

    override fun onMessage(message: ResponseMessage) {
        Log.i("Response", "${message.request.identifier} - ${message.verb}")

        val callback = if (message.verb == ResponseVerb.KEYBOARD_EVENT
         && message.content["code"].string == "DOWN")
            onKeyboardEvent(message)

        else if (message.verb == ResponseVerb.RESPONSE)
            onResponseMessage(message)

        else if (message.verb == ResponseVerb.EVENT)
            onEventMessage(message)

        else
            false

        if (callback)
            listener.onMessage(message)
    }

    private fun onKeyboardEvent(message: ResponseMessage): Boolean {
        Log.i("KEY", message.content["number"].int.toString())

        val inst = Instrumentation()
        inst.sendKeyDownUpSync(message.content["number"].int)

        return true
    }

    private fun onResponseMessage(message: ResponseMessage): Boolean {
        if (message.request isEquivalentTo Messages.CURRENT_PEDALBOARD_DATA) {
            val pedalboardIndex = message.content["pedalboard"].int

            Data.bankIndex = message.content["bank"]["index"].int
            Data.pedalboardIndex = pedalboardIndex
            Data.currentPedalboard = message.content["bank"]["pedalboards"][pedalboardIndex]

        } else if (message.request isEquivalentTo Messages.PLUGINS) {
            val map = HashMap<String, JsonElement>()

            for (plugin in message.content["plugins"].array)
                map[plugin["uri"].string] = plugin

            Data.plugins = map
        }

        return true
    }

    private fun onEventMessage(message: ResponseMessage) : Boolean {
        val event = EventMessage(message.content)

        if (event.type == EventType.CURRENT) {
            Data.bankIndex = event.content["bank"].int
            Data.pedalboardIndex = event.content["pedalboard"].int
            Data.currentPedalboard = event.content["value"]

        } else if (event.type == EventType.BANK) {
            if (!isCurrentBank(event))
                return false

        } else if (event.type == EventType.PEDALBOARD) {
            if (!isCurrentPedalboard(event))
                return false

        } else if (event.type == EventType.EFFECT) {
            if (!isCurrentPedalboard(event))
                return false

        } else if (event.type == EventType.EFFECT_TOGGLE) {
            if (!isCurrentPedalboard(event))
                return false

            val index = event.content["effect"].int

            val effect = Data.currentPedalboard["effects"][index]
            effect["active"] = !effect["active"].bool

        } else if (event.type == EventType.PARAM) {
            if (!isCurrentPedalboard(event))
                return false

            val effectIndex = event.content["effect"].int
            val paramIndex = event.content["param"].int

            val effect = Data.currentPedalboard["effects"][effectIndex]
            effect["params"][paramIndex]["value"] = event.content["value"]

        } else if (event.type == EventType.CONNECTION) {
            return false
        }

        return true
    }

    private fun isCurrentPedalboard(event: EventMessage): Boolean {
        return isCurrentBank(event)
            && event.content["pedalboard"].int == Data.pedalboardIndex
    }

    private fun isCurrentBank(event: EventMessage) =
            event.content["bank"].int == Data.bankIndex
}
