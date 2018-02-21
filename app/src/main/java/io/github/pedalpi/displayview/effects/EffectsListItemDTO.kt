package io.github.pedalpi.displayview.effects

import com.github.salomonbrys.kotson.bool
import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement

class EffectsListItemDTO(val index : Int, val data: JsonElement, val name: String, val status: Boolean) {
    constructor(index : Int, data: JsonElement) : this(index, data, "$index - ${"Effect name"/*data["name"]*/}", data["active"].bool)
}
