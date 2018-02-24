package io.github.pedalpi.displayview.communication.message.request

import io.github.pedalpi.displayview.Data

class Messages {
    companion object {
        @JvmField val PLUGINS = RequestMessage(RequestVerb.GET, "/v1/plugins")

        @JvmField val CURRENT_PEDALBOARD = RequestMessage(RequestVerb.GET, "/v1/current")
        @JvmField val CURRENT_PEDALBOARD_DATA = RequestMessage(RequestVerb.GET, "/v1/current/data")
        @JvmStatic
        fun CURRENT_PEDALBOARD_TOGGLE_EFFECT(indexEffect : Int) = RequestMessage(RequestVerb.PUT, "/v1/current/effect/$indexEffect")

        @JvmStatic
        fun PARAM_VALUE_CHANGE(effectIndex: Int, paramIndex: Int, value: Number) = PARAM_VALUE_CHANGE(0, Data.currentPedalboardPosition, effectIndex, paramIndex, value)

        @JvmStatic
        fun PARAM_VALUE_CHANGE(bankIndex: Int, pedalboardIndex: Int, effectIndex: Int, paramIndex: Int, value: Number) = RequestMessage(RequestVerb.PUT, "/v1/bank/$bankIndex/pedalboard/$pedalboardIndex/effect/$effectIndex/param/$paramIndex", value)
    }
}
