package io.github.pedalpi.displayview.util

import android.content.Context
import android.content.Intent
import io.github.pedalpi.displayview.activity.MainActivity

fun Context.popToRoot() {
    val intent = Intent(this, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    startActivity(intent)
}