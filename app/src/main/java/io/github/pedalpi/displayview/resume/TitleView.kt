package io.github.pedalpi.displayview.resume

import android.app.Activity
import android.widget.TextView
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonElement
import io.github.pedalpi.displayview.R


class TitleView(activity: Activity) {
    private var number: TextView = activity.findViewById(R.id.resumePedalboardNumber) as TextView
    private var name: TextView = activity.findViewById(R.id.resumePedalboardName) as TextView

    fun update(index: Int, pedalboard: JsonElement) {
        number.text = if (index < 10) "0$index" else "$index"
        name.text = pedalboard["name"].string
    }
}
