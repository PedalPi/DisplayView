package io.github.pedalpi.displayview.resume.effectview

import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonElement

class EffectsGridItemDTO(val index : Int, private val effect: JsonElement, private val plugin: JsonElement) {
    val name = "$index - ${plugin["name"].string}"

    lateinit var viewHolder: EffectsGridItemAdapter.ViewHolder
}
