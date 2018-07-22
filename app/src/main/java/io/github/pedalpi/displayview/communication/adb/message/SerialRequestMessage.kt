package io.github.pedalpi.displayview.communication.adb.message

import io.github.pedalpi.displayview.communication.base.message.RequestMessage
import io.github.pedalpi.displayview.communication.base.message.RequestVerb

class SerialRequestMessage(
        type: RequestVerb,
        path: String,
        content: Any = "") : RequestMessage(type, path, content) {

    var identifier: Int = Identifier.instance.next()

    override fun toString(): String {
        return "$identifier $type $path\n$content\nEOF\n"
    }
}


fun RequestMessage.toSerial(): SerialRequestMessage {
    return SerialRequestMessage(this.type, this.path, this.content)
}
