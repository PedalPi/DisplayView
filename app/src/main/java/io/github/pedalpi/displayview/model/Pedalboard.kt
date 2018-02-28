package io.github.pedalpi.displayview.model

import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonElement


class Pedalboard(val index: Int, val pedalboard: JsonElement) {
    val effects: List<Effect> = pedalboard["effects"].array.mapIndexed(::Effect)

    val name
        get() = pedalboard["name"].string
}
