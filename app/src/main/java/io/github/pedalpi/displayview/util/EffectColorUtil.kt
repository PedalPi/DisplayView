package io.github.pedalpi.displayview.util

import io.github.pedalpi.displayview.R


object EffectColorUtil {
    val selectableColor: HashMap<String, Int> = HashMap<String, Int>()
    val unselectableColor: HashMap<String, Int> = HashMap<String, Int>()

    init {
        selectableColor[true.toString()] = R.color.buttonGreen
        unselectableColor[true.toString()] = R.color.buttonGreenDark

        selectableColor[false.toString()] = R.color.buttonRed
        unselectableColor[false.toString()] = R.color.buttonRedDark
    }
}
