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
