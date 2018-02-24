package io.github.pedalpi.displayview.params

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement

class ParamsListItemDTO(val index : Int, val param: JsonElement, val plugin: JsonElement) {

    enum class ParamType {
        COMBOBOX,
        KNOB,
        TOGGLE
    }

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
        properties.contains("toggle") -> ParamType.TOGGLE
        else -> ParamType.KNOB
    }

    val percent: Int
        get() = calculatePercent(value.toDouble())

    fun calculatePercent(current : Double): Int {
        return ((current - minimum) * 100 / (maximum - minimum)).toInt()
    }

    val options: List<String> = plugin["scalePoints"].array.map { data -> data["label"].string }
}
