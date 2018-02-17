package io.github.pedalpi.pedalpi_display.communication.message.request

import org.json.JSONObject
import java.io.Serializable

class RequestMessage(
        val type: RequestVerb,
        private val path: String,
        private val content: JSONObject) : Serializable {

    override fun toString(): String {
        return "$type $path\n$content\nEOF\n"
    }
}
