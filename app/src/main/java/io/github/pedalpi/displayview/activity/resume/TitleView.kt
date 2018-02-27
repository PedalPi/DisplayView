package io.github.pedalpi.displayview.activity.resume

import android.widget.TextView
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonElement


class TitleView(private var number: TextView, private var name: TextView) {
    fun update(index: Int, pedalboard: JsonElement) {
        number.text = if (index < 10) "0$index" else "$index"
        name.text = pedalboard["name"].string
    }
}
