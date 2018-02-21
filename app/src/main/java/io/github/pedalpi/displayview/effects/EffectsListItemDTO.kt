package io.github.pedalpi.pedalpi_display.effects

import com.github.salomonbrys.kotson.bool
import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement

class EffectsListItemDTO(val index : Int, val data: String, val name: String, val status: Boolean) {
    constructor(index : Int, data: JsonElement) : this(index, data.toString(), "Effect name" + index, data["active"].bool)
}
