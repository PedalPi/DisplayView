package io.github.pedalpi.displayview.communication.base

import io.github.pedalpi.displayview.communication.base.message.RequestMessage


object Communicator {
    var communication: Communication? = null

    fun initialize() {
        communication?.initialize()
    }

    fun close() {
        communication?.close()
    }

    fun setOnMessageListener(listener: OnMessageListener) {
        communication?.onMessageListener = listener
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
