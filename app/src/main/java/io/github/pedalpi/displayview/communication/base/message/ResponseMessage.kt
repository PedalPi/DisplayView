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

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import java.io.Serializable

open class ResponseMessage(val verb: ResponseVerb, val content: JsonElement, val request: RequestMessage) : Serializable {

    companion object {
        @JvmStatic
        fun error(message: String) = error(message, RequestMessage.NIL)

        @JvmStatic
        fun error(message: String, request: RequestMessage)
                = ResponseMessage(ResponseVerb.ERROR, "{'message': 'error: $message'}", request)
    }

    constructor(type: ResponseVerb, content: String, request: RequestMessage) : this(type, JsonParser().parse(content), request)
}
