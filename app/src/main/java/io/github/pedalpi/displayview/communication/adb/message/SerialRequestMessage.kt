package io.github.pedalpi.displayview.communication.adb.message

import io.github.pedalpi.displayview.communication.base.message.RequestMessage
import io.github.pedalpi.displayview.communication.base.message.RequestVerb

class SerialRequestMessage(
        type: RequestVerb,
        path: String,
        content: Any = "") : RequestMessage(type, path, content) {

    companion object {
        @JvmStatic
        fun from(requestMessage: RequestMessage): SerialRequestMessage {
            return SerialRequestMessage(requestMessage.type, requestMessage.path, requestMessage.content)
        }
    }

    var identifier: Int = Identifier.instance.next()

    override fun toString(): String {
        return "$identifier $type $path\n$content\nEOF\n"
    }
}
