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

package io.github.pedalpi.displayview.communication.base.message

import io.github.pedalpi.displayview.communication.adb.message.SerialRequestMessage

open class RequestMessage(
        val type: RequestVerb,
        val path: String,
        val content: Any = "") : Cloneable {

    companion object {
        @JvmField val NIL = SerialRequestMessage(RequestVerb.NIL, "/")
    }

    /**
     * Similar to equals, but it ignores inherit parameters
     * Identifier needs be unique and it is used in message protocol
     *
     * @param message
     */
    infix fun isEquivalentTo(message: RequestMessage): Boolean {
        return this.type == message.type
            && this.path == message.path
            && this.content == message.content
    }
}
