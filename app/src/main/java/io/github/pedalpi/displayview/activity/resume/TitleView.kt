package io.github.pedalpi.displayview.activity.resume

import android.widget.TextView
import io.github.pedalpi.displayview.model.Pedalboard


class TitleView(private var number: TextView, private var name: TextView) {
    fun update(pedalboard: Pedalboard) {
        number.text = if (pedalboard.index < 10) "0${pedalboard.index}" else "${pedalboard.index}"
        name.text = pedalboard.name
    }
}
