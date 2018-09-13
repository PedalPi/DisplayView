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

package io.github.pedalpi.displayview.communication.base.message

import io.github.pedalpi.displayview.model.Data
import io.github.pedalpi.displayview.model.Effect
import io.github.pedalpi.displayview.model.Param

class Messages {
    companion object {
        @JvmStatic
        fun AUTH(username: String, password: String) = RequestMessage(
            RequestVerb.PUT,
            "/v1/auth",
            "{'username': '$username', 'password': '$password'}"
        )

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
