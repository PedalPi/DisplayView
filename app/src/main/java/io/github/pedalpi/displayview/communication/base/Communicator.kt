package io.github.pedalpi.displayview.communication.base

import io.github.pedalpi.displayview.communication.base.message.RequestMessage
import io.github.pedalpi.displayview.communication.base.message.ResponseMessageProcessor


object Communicator {
    var communication: Communication? = null

    fun initialize() {
        communication?.initialize()
    }

    fun close() {
        communication?.close()
    }

    fun setOnMessageListener(listener: OnMessageListener) {
        communication?.onMessageListener = {
            val notifyNext = ResponseMessageProcessor.process(it)

            if (notifyNext)
                listener(it)
        }
    }

    fun setOnConnectedListener(listener: OnConnectedListener) {
        communication?.onConnectedListener = listener
    }

    fun setOnDisconnectedListener(listener: OnDisconnectedListener) {
        communication?.onDisconnectedListener = listener
    }

    fun send(message: RequestMessage) {
        communication?.send(message)
    }
}
