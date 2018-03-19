package io.github.pedalpi.displayview.communication.adb.message

import io.github.pedalpi.displayview.communication.base.message.RequestMessage

class Identifier(private var id: Int) {

    val messages: MutableMap<Int, SerialRequestMessage> = HashMap()

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

    fun register(message: SerialRequestMessage) {
        this.messages[message.identifier] = message
    }

    fun remove(identifier: Int) : SerialRequestMessage {
        return this.messages.remove(identifier) ?: RequestMessage.NIL
    }
}
