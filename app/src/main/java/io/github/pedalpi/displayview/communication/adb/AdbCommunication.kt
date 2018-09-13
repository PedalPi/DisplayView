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

import android.util.Log
import io.github.pedalpi.displayview.communication.adb.server.Server
import io.github.pedalpi.displayview.communication.base.Communication
import io.github.pedalpi.displayview.communication.base.message.RequestMessage
import java.io.IOException


class AdbCommunication: Communication() {

    private val server = Server()
    private var running = false

    override fun initialize() {
        server.onConnectedListener = { onConnectedListener() }
        server.onDisconnectedListener = { onDisconnectedListener() }
        server.onMessageListener = { onMessageListener(it) }

        val runnable = Runnable { this.runServer() }
        Thread(runnable).start()
    }

    private fun runServer() {
        server.start(8888)
        running = true

        try {
            while (running)
                server.waitClient()

        } catch (e: IOException) {
            Log.e("ERROR", e.message)
            throw RuntimeException(e)
        }
    }

    override fun close() {
        running = false
    }

    override fun send(message: RequestMessage) {
        server.sendBroadcast(message)
    }
}
