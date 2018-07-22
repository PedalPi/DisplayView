package io.github.pedalpi.displayview.communication.base

import io.github.pedalpi.displayview.communication.base.message.RequestMessage
import io.github.pedalpi.displayview.communication.base.message.ResponseMessage


typealias OnConnectedListener = () -> Unit
typealias OnDisconnectedListener = () -> Unit
typealias OnMessageListener = (ResponseMessage) -> Unit


abstract class Communication {
    var onConnectedListener: OnConnectedListener = { }
    var onDisconnectedListener: OnDisconnectedListener = { }
    var onMessageListener: OnMessageListener = { }

    abstract fun initialize()
    abstract fun close()

    abstract fun send(message: RequestMessage)
}
