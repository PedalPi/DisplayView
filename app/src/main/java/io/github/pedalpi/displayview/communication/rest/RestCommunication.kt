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

import android.util.Log
import io.github.pedalpi.displayview.communication.base.Communication
import io.github.pedalpi.displayview.communication.base.message.Messages
import io.github.pedalpi.displayview.communication.base.message.RequestMessage
import okhttp3.OkHttpClient

typealias OnMessageListener = (String) -> Unit


class RestCommunication: Communication() {

    private lateinit var httpClient: HttpClient
    private lateinit var eventsObserver: WebSocketEventsObserver

    private val client = OkHttpClient()

    override fun initialize() {
        this.httpClient = HttpClient(client, "http://localhost:3000")
        this.eventsObserver = WebSocketEventsObserver(client, "ws://localhost:3000/ws")

        this.httpClient.onMessageListener = { onMessage(it) }
        this.eventsObserver.onMessageListener = { onMessage(it) }

        this.httpClient.request(Messages.AUTH("pedal pi", "pedal pi"))
    }

    override fun close() {
        // TODO CLOSE
        //TODO("not implemented")
        client.dispatcher().executorService().shutdown()
    }

    override fun send(message: RequestMessage) {
        this.httpClient.request(message)
    }

    private fun onMessage(message: String) {
        Log.i("ON MESSAGE", message)
        // FIXME
        //this.onMessageListener()
    }
}
