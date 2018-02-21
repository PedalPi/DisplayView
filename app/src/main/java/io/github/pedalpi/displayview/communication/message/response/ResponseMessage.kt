package io.github.pedalpi.pedalpi_display.communication.message.response

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import io.github.pedalpi.pedalpi_display.communication.message.request.RequestMessage
import java.io.Serializable

class ResponseMessage(val verb: ResponseVerb, val content: JsonElement) : Serializable {

    var request : RequestMessage? = null

    constructor(type: ResponseVerb, content: String) : this(type, JsonParser().parse(content))

    override fun toString(): String {
        return verb.toString() + " " + content.toString()
    }
}