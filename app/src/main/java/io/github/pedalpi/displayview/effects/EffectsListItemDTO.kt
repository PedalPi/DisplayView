package io.github.pedalpi.displayview.effects

import com.github.salomonbrys.kotson.bool
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonElement

class EffectsListItemDTO(val index : Int, private val effect: JsonElement, private val plugin: JsonElement) {

    val status: Boolean
        get() = effect["active"].bool

    val name = "$index - ${plugin["name"].string}"

    lateinit var viewHolder: EffectsListItemAdapter.ViewHolder
}
