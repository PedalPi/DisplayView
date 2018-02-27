package io.github.pedalpi.displayview.util

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import io.github.pedalpi.displayview.activity.MainActivity

fun Context.popToRoot() {
    val intent = Intent(this, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    startActivity(intent)
}

fun Context.inflate(layout: Int): View {
    val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    return inflater.inflate(layout, null)
}