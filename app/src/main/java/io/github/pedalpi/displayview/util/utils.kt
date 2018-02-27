package io.github.pedalpi.displayview

import android.content.Context
import android.content.Intent

fun Context.popToRoot() {
    val intent = Intent(this, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    startActivity(intent)
}