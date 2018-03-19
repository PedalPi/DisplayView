package io.github.pedalpi.displayview.communication.rest


import android.util.Log
import okhttp3.*
import okio.ByteString


internal class WebSocketEventsObserver(client: OkHttpClient, url: String) : WebSocketListener() {

    private val ws: WebSocket
    var onMessageListener: OnMessageListener = {}

    init {
        val request = Request.Builder().url(url).build()
        this.ws = client.newWebSocket(request, this)
    }

    override fun onOpen(webSocket: WebSocket, response: Response?) {
        /*
        webSocket.send("Hello, it's Saurel !")
        webSocket.send("What's up ?")
        webSocket.send(ByteString.decodeHex("deadbeef"))
        webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !")
        */
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        this.onMessageListener(text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        Log.i("WEBSOCKET-MESSAGE", "bytes received???")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String?) {
        val NORMAL_CLOSURE_STATUS = 1000
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
    }

    override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) { }
}
