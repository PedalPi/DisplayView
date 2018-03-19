package io.github.pedalpi.displayview.communication.base.message

import android.app.Instrumentation
import android.util.Log
import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import io.github.pedalpi.displayview.communication.adb.message.EventMessage
import io.github.pedalpi.displayview.communication.adb.message.EventType
import io.github.pedalpi.displayview.model.Data
import io.github.pedalpi.displayview.model.Pedalboard


object ResponseMessageProcessor {

    fun process(message: ResponseMessage): Boolean {

        return if (message.verb == ResponseVerb.KEYBOARD_EVENT
         && message.content["code"].string == "DOWN")
            onKeyboardEvent(message)

        else if (message.verb == ResponseVerb.RESPONSE)
            onResponseMessage(message)

        else if (message.verb == ResponseVerb.EVENT)
            onEventMessage(message)

        else if (message.verb == ResponseVerb.ERROR)
            onEventMessage(message)

        else
            false
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
            Data.currentPedalboard = Pedalboard(pedalboardIndex, message.content["bank"]["pedalboards"][pedalboardIndex])

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
            val pedalboardIndex = event.content["pedalboard"].int
            Data.currentPedalboard = Pedalboard(pedalboardIndex, event.content["value"])

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

            val effect = Data.currentPedalboard.effects[index]
            effect.active = !effect.active

        } else if (event.type == EventType.PARAM) {
            if (!isCurrentPedalboard(event))
                return false

            val effectIndex = event.content["effect"].int
            val paramIndex = event.content["param"].int

            val effect = Data.currentPedalboard.effects[effectIndex]
            effect.params[paramIndex].value = event.content["value"].number

        } else if (event.type == EventType.CONNECTION) {
            return false
        }

        return true
    }

    private fun isCurrentPedalboard(event: EventMessage): Boolean {
        return isCurrentBank(event)
            && event.content["pedalboard"].int == Data.currentPedalboard.index
    }

    private fun isCurrentBank(event: EventMessage) =
            event.content["bank"].int == Data.bankIndex
}
