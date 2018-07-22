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

package io.github.pedalpi.displayview.model

import android.util.Log
import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement


/**
 * @param index: Effect position
 * @param effect: Original effect Json data
 */
class Effect(val index: Int, val effect: JsonElement) {

    private val data: JsonElement = Data.plugin(effect["plugin"].string)

    val name
        get() = data["name"].string

    var active: Boolean
        get() = effect["active"].bool
        set(value) {
            effect["active"] = value
        }

    val params : List<Param>

    init {
        val params = effect["params"].array
        val pluginControls = data["ports"]["control"]["input"].array

        try {
            this.params = (0 until params.array.size())
                    .map { Param(it, this, params[it], pluginControls[it]) }

        } catch (e: IndexOutOfBoundsException) {
            Log.wtf("ERROR", "Params: ${params.array.size()}")
            Log.wtf("ERROR", "PluginParams: ${pluginControls.array.size()}")

            throw e
        }
    }
}
