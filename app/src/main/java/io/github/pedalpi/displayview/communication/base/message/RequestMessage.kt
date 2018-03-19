package io.github.pedalpi.displayview.communication.base.message

import io.github.pedalpi.displayview.communication.adb.message.SerialRequestMessage

open class RequestMessage(
        val type: RequestVerb,
        val path: String,
        val content: Any = "") : Cloneable {

    companion object {
        @JvmField val NIL = SerialRequestMessage(RequestVerb.NIL, "/")
    }

    /**
     * Similar to equals, but it ignores inherit parameters
     * Identifier needs be unique and it is used in message protocol
     *
     * @param message
     */
    infix fun isEquivalentTo(message: RequestMessage): Boolean {
        return this.type == message.type
            && this.path == message.path
            && this.content == message.content
    }
}
