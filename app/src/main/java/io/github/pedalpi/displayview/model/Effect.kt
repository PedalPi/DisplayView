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
            Log.wtf("ERRO", "Params: ${params.array.size()}")
            Log.wtf("ERRO", "PluginParams: ${pluginControls.array.size()}")

            throw e
        }
    }
}
