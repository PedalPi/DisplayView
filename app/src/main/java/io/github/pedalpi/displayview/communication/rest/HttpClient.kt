package io.github.pedalpi.displayview.communication.rest

import io.github.pedalpi.displayview.communication.base.message.RequestMessage
import io.github.pedalpi.displayview.communication.base.message.RequestVerb
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody


class HttpClient(private val client: OkHttpClient, private var url: String) {
    private val JSON = MediaType.parse("application/json; charset=utf-8")

    var token: String? = null
    var onMessageListener: OnMessageListener = {}

    fun request(message: RequestMessage) {
        var builder = Request.Builder()
            .url("$url/${message.path}")

        builder = when (message.type) {
            RequestVerb.POST -> builder.post(RequestBody.create(JSON, message.content.toString()))
            RequestVerb.PUT -> builder.put(RequestBody.create(JSON, message.content.toString()))
            RequestVerb.DELETE -> builder.delete()
            else -> builder.get()
        }

        token?.let {
            builder = builder.addHeader("Authorization", "bearer $it")
        }

        val request = builder.build()

        request.method()
        val response = client.newCall(request).execute()

        response.body()?.let {
            this.onMessageListener(it.string())
        }
    }
}
