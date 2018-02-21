package io.github.pedalpi.displayview.communication.message.response

enum class ResponseVerb(private val type: String) {
    RESPONSE("RESPONSE"),
    EVENT("EVENT"),
    KEYBOARD_EVENT("KEYBOARD_EVENT"),

    ERROR("ERROR");

    override fun toString(): String {
        return type
    }
}
