package io.github.pedalpi.displayview.communication

import com.google.gson.JsonParser
import io.github.pedalpi.displayview.communication.message.Identifier
import io.github.pedalpi.displayview.communication.message.request.RequestMessage
import io.github.pedalpi.displayview.communication.message.response.ResponseMessage
import io.github.pedalpi.displayview.communication.message.response.ResponseVerb
import org.json.JSONException


object MessageBuilder {
    fun generate(message: String): ResponseMessage {
        val strings = message.split(" ".toRegex(), 3).toTypedArray()

        val request = getRequestMessage(strings[0].toInt())
        val type = searchType(strings[1])
        val data = strings[2]

        return generateMessage(request, type, data)
    }

    private fun getRequestMessage(identifier: Int) : RequestMessage {
        return Identifier.instance.remove(identifier)
    }

    private fun searchType(word: String): ResponseVerb {
        return ResponseVerb.valueOf(word)
    }

    private fun generateMessage(request: RequestMessage, type: ResponseVerb, data: String): ResponseMessage {
        return try {
            ResponseMessage(type, JsonParser().parse(data), request)
        } catch (e: JSONException) {
            ResponseMessage.error("Invalid received message", request)
        }
    }
}
