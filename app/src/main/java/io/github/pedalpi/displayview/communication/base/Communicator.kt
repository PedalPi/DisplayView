/*
Copyright 2018 SrMouraSilva

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

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
