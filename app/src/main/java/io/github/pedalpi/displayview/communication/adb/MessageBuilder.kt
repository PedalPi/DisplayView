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

package io.github.pedalpi.displayview.communication.adb

import com.google.gson.JsonParser
import io.github.pedalpi.displayview.communication.adb.message.Identifier
import io.github.pedalpi.displayview.communication.adb.message.SerialRequestMessage
import io.github.pedalpi.displayview.communication.adb.message.SerialResponseMessage
import io.github.pedalpi.displayview.communication.base.message.ResponseMessage
import io.github.pedalpi.displayview.communication.base.message.ResponseVerb
import org.json.JSONException


object MessageBuilder {
    fun generate(message: String): ResponseMessage {
        val strings = message.split(" ".toRegex(), 3).toTypedArray()

        val request = getRequestMessage(strings[0].toInt())
        val type = searchType(strings[1])
        val data = strings[2]

        return generateMessage(request, type, data)
    }

    private fun getRequestMessage(identifier: Int) : SerialRequestMessage {
        return Identifier.instance.remove(identifier)
    }

    private fun searchType(word: String): ResponseVerb {
        return ResponseVerb.valueOf(word)
    }

    private fun generateMessage(request: SerialRequestMessage, type: ResponseVerb, data: String): ResponseMessage {
        return try {
            SerialResponseMessage(type, JsonParser().parse(data), request)
        } catch (e: JSONException) {
            ResponseMessage.error("Invalid received message", request)
        }
    }
}
