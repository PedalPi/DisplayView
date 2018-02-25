package io.github.pedalpi.displayview.resume.paramview

import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonElement

class ParamsGridItemDTO(val index : Int, private val param: JsonElement, private val plugin: JsonElement) {
    val name = "$index - ${plugin["name"].string}"

    lateinit var viewHolder: ParamsGridItemAdapter.ParamsGridItemViewHolder
}
