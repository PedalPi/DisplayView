package io.github.pedalpi.displayview.communication

import android.util.Log
import io.github.pedalpi.displayview.communication.message.Identifier
import io.github.pedalpi.displayview.communication.message.request.RequestMessage
import io.github.pedalpi.displayview.communication.message.request.RequestVerb
import io.github.pedalpi.displayview.communication.message.response.ResponseMessage
import io.github.pedalpi.displayview.communication.server.Server
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.Socket


class Client(private val connection: Socket) {

    private var open = false

    private val ioOutput = PrintStream(connection.getOutputStream())
    private val ioInput = BufferedReader(InputStreamReader(connection.getInputStream()))

    var onMessageListener: Server.OnMessageListener = Server.OnMessageListener { }
    var onDisconnectedListener: Server.OnDisconnectedListener = Server.OnDisconnectedListener { }

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
                    onMessageListener.onMessage(message)
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

        onDisconnectedListener.onDisconnected()
    }

    fun send(message: RequestMessage) {
        if (message.type !== RequestVerb.SYSTEM && message.type !== RequestVerb.NIL)
            Identifier.instance.register(message)

        Log.i("Request", message.toString())
        ioOutput.print(message.toString())
    }
}
