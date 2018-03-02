package io.github.pedalpi.displayview.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
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

fun View.setBackgroundTintListCompat(@ColorRes color: Int) {
    val colorInt = ContextCompat.getColor(this.context, color)
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
