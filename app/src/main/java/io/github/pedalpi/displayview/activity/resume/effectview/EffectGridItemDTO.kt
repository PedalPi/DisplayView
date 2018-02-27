package io.github.pedalpi.displayview.activity.resume.effectview

import com.github.salomonbrys.kotson.bool
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonElement


class EffectGridItemDTO(val index : Int, val effect: JsonElement, private val plugin: JsonElement) {

    val active: Boolean
        get() = effect["active"].bool

    val name = plugin["name"].string

    lateinit var viewHolder: EffectGridItemViewHolder
}
