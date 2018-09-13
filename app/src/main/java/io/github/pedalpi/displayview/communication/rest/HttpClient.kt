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
