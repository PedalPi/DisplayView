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
