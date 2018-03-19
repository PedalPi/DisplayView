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
