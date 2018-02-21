package io.github.pedalpi.displayview.communication.message.response

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import io.github.pedalpi.displayview.communication.message.request.RequestMessage
import java.io.Serializable

class ResponseMessage(val verb: ResponseVerb, val content: JsonElement, val request: RequestMessage) : Serializable {

    companion object {
        @JvmStatic fun error(message: String) = error(message, RequestMessage.NIL)
        @JvmStatic fun error(message: String, request: RequestMessage) = ResponseMessage(ResponseVerb.ERROR, "{'message': 'error: $message'}", request)
    }
    constructor(type: ResponseVerb, content: String, request: RequestMessage) : this(type, JsonParser().parse(content), request)

    override fun toString(): String {
        return "${request.identifier} $verb $content\n"
    }
}
