package io.github.pedalpi.displayview.communication.adb.message

import com.google.gson.JsonElement
import io.github.pedalpi.displayview.communication.base.message.ResponseMessage
import io.github.pedalpi.displayview.communication.base.message.ResponseVerb


class SerialResponseMessage(verb: ResponseVerb, content: JsonElement, request: SerialRequestMessage)
    : ResponseMessage(verb, content, request) {

    val serialRequest: SerialRequestMessage = request

    override fun toString(): String {
        return "${serialRequest.identifier} $verb $content\n"
    }
}
