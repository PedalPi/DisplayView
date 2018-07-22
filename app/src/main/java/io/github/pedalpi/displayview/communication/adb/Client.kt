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
import io.github.pedalpi.displayview.communication.adb.message.Identifier
import io.github.pedalpi.displayview.communication.adb.message.SerialRequestMessage
import io.github.pedalpi.displayview.communication.base.OnDisconnectedListener
import io.github.pedalpi.displayview.communication.base.OnMessageListener
import io.github.pedalpi.displayview.communication.base.message.RequestVerb
import io.github.pedalpi.displayview.communication.base.message.ResponseMessage
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.Socket


class Client(private val connection: Socket) {

    private var open = false

    private val ioOutput = PrintStream(connection.getOutputStream())
    private val ioInput = BufferedReader(InputStreamReader(connection.getInputStream()))

    var onMessageListener: OnMessageListener = { }
    var onDisconnectedListener: OnDisconnectedListener = { }

    private val message: ResponseMessage?
        get() {
            val data: String?
            try {
                data = ioInput.readLine()
            } catch (e: IOException) {
                Log.e("ERROR", e.message)
                return ResponseMessage.error(e.message!!)
            }

            if (data == null)
                return null
            return MessageBuilder.generate(data)
        }

    fun listen(): Runnable {
        open = true

        return Runnable {
            while (open) {
                val message = message
                if (message != null)
                    onMessageListener(message)
                else {
                    disconnect()
                }
            }
        }
    }

    @Throws(IOException::class)
    fun disconnect() {
        open = false

        connection.close()
        ioOutput.close()
        ioInput.close()

        onDisconnectedListener()
    }

    fun send(message: SerialRequestMessage) {
        if (message.type !== RequestVerb.SYSTEM && message.type !== RequestVerb.NIL)
            Identifier.instance.register(message)

        Log.i("Request", message.toString())
        ioOutput.print(message.toString())
    }
}
