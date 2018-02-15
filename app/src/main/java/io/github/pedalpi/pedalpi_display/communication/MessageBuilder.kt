package io.github.pedalpi.pedalpi_display.communication

import android.util.Log
import io.github.pedalpi.pedalpi_display.communication.message.response.ResponseMessage
import io.github.pedalpi.pedalpi_display.communication.message.response.ResponseVerb
import org.json.JSONException
import org.json.JSONObject

object MessageBuilder {
    fun generate(message: String): ResponseMessage {
        val strings = message.split(" ".toRegex(), 2).toTypedArray()

        Log.i(strings[0], strings[1])

        val type = searchType(strings[0])
        return generateMessage(type, strings[1])
    }

    private fun searchType(word: String): ResponseVerb {
        return ResponseVerb.values().firstOrNull { it.toString() == word }
                ?: ResponseVerb.ERROR
    }

    private fun generateMessage(type: ResponseVerb, data: String): ResponseMessage {
        return try {
            ResponseMessage(type, JSONObject(data))
        } catch (e: JSONException) {
            ResponseMessage(ResponseVerb.ERROR, "{'message': 'Invalid received message'}")
        }
    }
}
