package io.github.pedalpi.displayview.communication.message.request

import io.github.pedalpi.displayview.model.Data
import io.github.pedalpi.displayview.model.Effect
import io.github.pedalpi.displayview.model.Param

class Messages {
    companion object {
        @JvmField val PLUGINS = RequestMessage(RequestVerb.GET, "/v1/plugins")

        @JvmField val CURRENT_PEDALBOARD = RequestMessage(RequestVerb.GET, "/v1/current")
        @JvmField val CURRENT_PEDALBOARD_DATA = RequestMessage(RequestVerb.GET, "/v1/current/data")
        @JvmStatic
        fun CURRENT_PEDALBOARD_TOGGLE_EFFECT(effect: Effect) = RequestMessage(RequestVerb.PUT, "/v1/current/effect/${effect.index}", "{}")

        @JvmStatic
        fun PARAM_VALUE_CHANGE(effectIndex: Int, param: Param) = PARAM_VALUE_CHANGE(0, Data.currentPedalboard.index, effectIndex, param)

        @JvmStatic
        fun PARAM_VALUE_CHANGE(bankIndex: Int, pedalboardIndex: Int, effectIndex: Int, param: Param) = RequestMessage(RequestVerb.PUT, "/v1/bank/$bankIndex/pedalboard/$pedalboardIndex/effect/$effectIndex/param/${param.index}", param.value)
    }
}
