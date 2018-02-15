package io.github.pedalpi.pedalpi_display.communication.message.response

import org.json.JSONObject
import java.io.Serializable

class ResponseMessage(val type: ResponseVerb, val content: JSONObject) : Serializable {

    constructor(type: ResponseVerb, content: String) : this(type, JSONObject(content))

    override fun toString(): String {
        return type.toString() + " " + content.toString()
    }
}