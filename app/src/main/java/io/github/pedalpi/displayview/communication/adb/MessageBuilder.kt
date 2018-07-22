package io.github.pedalpi.displayview.communication.adb

import com.google.gson.JsonParser
import io.github.pedalpi.displayview.communication.adb.message.Identifier
import io.github.pedalpi.displayview.communication.adb.message.SerialRequestMessage
import io.github.pedalpi.displayview.communication.adb.message.SerialResponseMessage
import io.github.pedalpi.displayview.communication.base.message.ResponseMessage
import io.github.pedalpi.displayview.communication.base.message.ResponseVerb
import org.json.JSONException


object MessageBuilder {
    fun generate(message: String): ResponseMessage {
        val strings = message.split(" ".toRegex(), 3).toTypedArray()

        val request = getRequestMessage(strings[0].toInt())
        val type = searchType(strings[1])
        val data = strings[2]

        return generateMessage(request, type, data)
    }

    private fun getRequestMessage(identifier: Int) : SerialRequestMessage {
        return Identifier.instance.remove(identifier)
    }

    private fun searchType(word: String): ResponseVerb {
        return ResponseVerb.valueOf(word)
    }

    private fun generateMessage(request: SerialRequestMessage, type: ResponseVerb, data: String): ResponseMessage {
        return try {
            SerialResponseMessage(type, JsonParser().parse(data), request)
        } catch (e: JSONException) {
            ResponseMessage.error("Invalid received message", request)
        }
    }
}
