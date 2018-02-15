package io.github.pedalpi.pedalpi_display.communication.message.response

enum class ResponseVerb(private val type: String) {
    RESPONSE("RESPONSE"),
    EVENT("EVENT"),

    ERROR("ERROR");

    override fun toString(): String {
        return type
    }
}
