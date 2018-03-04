package io.github.pedalpi.displayview.communication.message.request

import org.json.JSONObject

class SystemMessages {
    companion object {
        @JvmField val CONNECTED = RequestMessage(RequestVerb.SYSTEM, "/", JSONObject("{'message': 'Connected'}"))
    }
}
