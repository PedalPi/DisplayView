package io.github.pedalpi.displayview.util

import io.github.pedalpi.displayview.R


object EffectColorUtil {
    val selectableColor: HashMap<String, Int> = HashMap<String, Int>()

    init {
        selectableColor[true.toString()] = R.color.buttonGreen
        selectableColor[false.toString()] = R.color.buttonRed
    }
}
