package io.github.pedalpi.displayview.model

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement


enum class ParamType {
    COMBOBOX,
    KNOB,
    TOGGLE
}


class Param(val index: Int, val effect: Effect, val param: JsonElement, val data: JsonElement) {

    val name: String = data["name"].string
    var value: Number
        get() = param["value"].number
        set(value) {
            param["value"] = value
        }

    val minimum: Double = param["minimum"].double
    val maximum: Double = param["maximum"].double

    val options: List<String> = data["scalePoints"].array.map { data -> data["label"].string }
    private val properties = data["properties"].array.map { value -> value.string }

    val type : ParamType = when {
        properties.contains("enumeration") -> ParamType.COMBOBOX
        properties.contains("toggled") -> ParamType.TOGGLE
        else -> ParamType.KNOB
    }

    val valueAsPercent: Int
        get() = ((value.toDouble() - minimum) * 100 / (maximum - minimum)).toInt()


    fun calculateValueByPercent(progress: Int): Double {
        return (progress * maximum + (100 - progress) * minimum) / 100.0
    }
}
