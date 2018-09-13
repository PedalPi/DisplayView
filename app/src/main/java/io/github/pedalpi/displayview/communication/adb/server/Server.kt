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

package io.github.pedalpi.displayview.communication.adb.server

import android.util.Log
import io.github.pedalpi.displayview.communication.adb.Client
import io.github.pedalpi.displayview.communication.adb.message.toSerial
import io.github.pedalpi.displayview.communication.base.OnConnectedListener
import io.github.pedalpi.displayview.communication.base.OnDisconnectedListener
import io.github.pedalpi.displayview.communication.base.OnMessageListener
import io.github.pedalpi.displayview.communication.base.message.RequestMessage
import java.io.IOException
import java.net.ServerSocket
import java.util.*


class Server {

    private lateinit var connection: ServerSocket
    private val clients = LinkedList<Client>()

    var onMessageListener: OnMessageListener = { }
    var onConnectedListener: OnConnectedListener = { }
    var onDisconnectedListener: OnDisconnectedListener = { }

    fun start(port: Int) {
        try {
            connection = ServerSocket(port)

        } catch (e: IOException) {
            Log.e("ERROR", e.message)
            throw RuntimeException(e)//e.printStackTrace();
        }

    }

    fun close() {
        try {
            for (client in clients) {
                client.disconnect()
                clients.remove(client)
            }

            connection.close()
        } catch (e: IOException) {
            Log.e("ERROR", e.message)
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class)
    fun waitClient() {
        val socket = connection.accept()

        val client = Client(socket)
        client.onMessageListener = { onMessageListener(it) }

        client.onDisconnectedListener = {
            clients.remove(client)
            onDisconnectedListener()
        }

        Thread(client.listen()).start()

        this.clients.add(client)
        Log.i("CLIENTS", clients.size.toString() + "")

        onConnectedListener()
    }

    fun sendBroadcast(message: RequestMessage) {
        for (clients in clients)
            clients.send(message.toSerial())
    }
}
