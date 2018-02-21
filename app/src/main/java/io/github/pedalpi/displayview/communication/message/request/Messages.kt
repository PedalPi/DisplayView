package io.github.pedalpi.displayview.communication.message.request

import org.json.JSONObject

class Messages {
    companion object {
        @JvmField val PLUGINS = RequestMessage(RequestVerb.GET, "/v1/plugins", JSONObject("{}"))

        @JvmField val CURRENT_PEDALBOARD = RequestMessage(RequestVerb.GET, "/v1/current", JSONObject("{}"))
        @JvmField val CURRENT_PEDALBOARD_DATA = RequestMessage(RequestVerb.GET, "/v1/current/data", JSONObject("{}"))
    }
}