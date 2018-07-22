/*
Copyright 2018 SrMouraSilva

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package io.github.pedalpi.displayview.util

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.StrictMode
import android.provider.Settings
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
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

fun Context.generateSpinnerDropdownAdapter(options: List<String>): ArrayAdapter<String> {
    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    return adapter
}

fun Context.generateCustomDialog(view: View): Dialog {
    val builder = AlertDialog.Builder(this)
    builder.setView(view)
    builder.setCancelable(true)

    val dialog = builder.create()
    dialog.setCanceledOnTouchOutside(true)

    return dialog
}

fun Context.getColorCompat(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

fun View.setBackgroundColorCompat(@ColorInt color: Int) {
    this.setBackgroundColor(this.context.getColorCompat(color))
}

/**
 * Set the background of a view based in the color defined
 *
 */
fun View.setBackgroundTintListCompat(@ColorRes color: Int) {
    val colorInt = this.context.getColorCompat(color)
    val backgroundTintList = BackgroundTints.forColoredButton(this.context, colorInt)

    ViewCompat.setBackgroundTintList(this, backgroundTintList)
}

fun View.setBackgroundCompat(@DrawableRes drawable: Int) {
    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN)
        this.setBackgroundDrawable(this.getDrawable(drawable))
    else
        this.background = this.getDrawable(drawable)
}

private fun View.getDrawable(@DrawableRes drawable: Int): Drawable? {
    return ResourcesCompat.getDrawable(this.resources, drawable, null)
}

fun TextView.setTextColorCompat(@ColorInt color: Int) {
    this.setTextColor(this.context.getColorCompat(color))
}

val Activity.isDebugActive: Boolean
    get() {
        val ADB_ENABLED = if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT)
            android.provider.Settings.Secure.ADB_ENABLED
        else
            android.provider.Settings.Global.ADB_ENABLED

        return 1 == Settings.Secure.getInt(this.contentResolver, ADB_ENABLED, 0)
    }


fun strictModePermitAll() {
    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)

    if (Build.VERSION.SDK_INT >= 16) {
        //restore strict mode after onCreate() returns. https://issuetracker.google.com/issues/36951662
        Handler().postAtFrontOfQueue({
            StrictMode.setThreadPolicy(policy)
        })
    }
}
