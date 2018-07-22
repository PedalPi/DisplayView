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
