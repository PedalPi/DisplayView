package io.github.pedalpi.displayview.communication.message

import io.github.pedalpi.displayview.communication.message.request.RequestMessage

class Identifier(private var id: Int) {

    val messages: MutableMap<Int, RequestMessage> = HashMap()

    companion object {
        @JvmField val instance : Identifier = Identifier(0)
    }

    fun next(): Int {
        synchronized(this) {
            val id = this.id
            this.id += 1
            return id
        }
    }

    fun register(message: RequestMessage) {
        this.messages[message.identifier] = message
    }

    fun remove(identifier: Int) : RequestMessage {
        return this.messages.remove(identifier) ?: RequestMessage.NIL
    }
}
