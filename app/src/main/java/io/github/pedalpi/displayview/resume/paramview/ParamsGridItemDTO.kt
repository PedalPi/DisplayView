package io.github.pedalpi.displayview.resume.paramview

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement


class ParamsGridItemDTO(val index : Int, val param: JsonElement, val plugin: JsonElement) {

    enum class ParamType {
        COMBOBOX,
        KNOB,
        TOGGLE
    }

    lateinit var viewHolder: ParamsGridItemAdapter.ParamsGridItemViewHolder

    val name: String = plugin["name"].string
    var value: Number
        get() = param["value"].number
        set(value) {
            param["value"] = value
        }

    val minimum: Double = param["minimum"].double
    val maximum: Double = param["maximum"].double

    private val properties = plugin["properties"].array.map { value -> value.string }

    val type : ParamType = when {
        properties.contains("enumeration") -> ParamType.COMBOBOX
        properties.contains("toggled") -> ParamType.TOGGLE
        else -> ParamType.KNOB
    }

    val percent: Int
        get() = calculatePercent(value.toDouble())

    private fun calculatePercent(current: Double): Int {
        return ((current - minimum) * 100 / (maximum - minimum)).toInt()
    }

    fun calculateValue(progress: Int): Double {
        return (progress * maximum + (100 - progress) * minimum) / 100.0
    }

    val options: List<String> = plugin["scalePoints"].array.map { data -> data["label"].string }
}
