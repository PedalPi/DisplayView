package io.github.pedalpi.displayview.communication.server

import android.app.Application
import android.util.Log

import java.io.IOException

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val runnable = Runnable { this.runServer() }

        Thread(runnable).start()
    }

    private fun runServer() {
        val server = Server.getInstance()
        server.start(8888)

        try {
            while (true)
                server.waitClient()

        } catch (e: IOException) {
            Log.e("ERROR", e.message)
            throw RuntimeException(e)
        }
    }
}