package io.github.pedalpi.displayview.params

import com.github.salomonbrys.kotson.double
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonElement

class ParamsListItemDTO(val param: JsonElement, val plugin: JsonElement) {
    val name: String = plugin["name"].string
    val value: Double = param["value"].double
    val minimum: Double = param["minimum"].double
    val maximum: Double = param["maximum"].double

    val percent: Int
        get() = calculatePercent(value)

    fun calculatePercent(current : Double): Int {
        return ((current - minimum) * 100 / (maximum - minimum)).toInt()
    }
}
