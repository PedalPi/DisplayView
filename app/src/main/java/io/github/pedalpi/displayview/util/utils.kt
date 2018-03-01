package io.github.pedalpi.displayview.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
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