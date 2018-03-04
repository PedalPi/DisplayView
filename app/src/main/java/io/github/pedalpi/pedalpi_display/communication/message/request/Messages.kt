package io.github.pedalpi.pedalpi_display.communication.message.request

import org.json.JSONObject

class Messages {
    companion object {
        @JvmField val CURRENT_PEDALBOARD = RequestMessage(RequestVerb.GET, "/v1/current", JSONObject("{}"))
        @JvmField val CURRENT_PEDALBOARD_DATA = RequestMessage(RequestVerb.GET, "/v1/current/data", JSONObject("{}"))
    }
}