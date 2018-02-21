package io.github.pedalpi.displayview.communication.message.request

import io.github.pedalpi.displayview.communication.message.Identifier
import org.json.JSONObject

class RequestMessage(
        val type: RequestVerb,
        private val path: String,
        private val content: JSONObject) : Cloneable {

    companion object {
        @JvmField val NIL = RequestMessage(RequestVerb.NIL, "/", JSONObject("{}"))
    }

    var identifier: Int = Identifier.instance.next()

    override fun toString(): String {
        return "$identifier $type $path\n$content\nEOF\n"
    }

    public override fun clone(): RequestMessage {
        return super.clone() as RequestMessage
    }

    /**
     * Similar to equals, but it ignores the identifier
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
