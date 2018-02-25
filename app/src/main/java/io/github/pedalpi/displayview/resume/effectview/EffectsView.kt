package io.github.pedalpi.displayview.resume.effectview

import android.content.Context
import android.widget.GridView
import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonElement
import io.github.pedalpi.displayview.Data
import java.util.*


class EffectsView(private val activity: Context, private val gridView: GridView) {
    private lateinit var pedalboard: JsonElement

    private lateinit var adapter: EffectsGridItemAdapter

    fun update(pedalboard: JsonElement) {
        this.pedalboard = pedalboard

        populateViews()
    }

    private fun populateViews() {
        this.adapter = EffectsGridItemAdapter(activity, generateData())

        this.gridView.adapter = adapter
        this.adapter.notifyDataSetChanged()
    }

    private fun generateData(): List<EffectsGridItemDTO> {
        val elements = ArrayList<EffectsGridItemDTO>()

        pedalboard["effects"].array.mapIndexedTo(elements) {
            index, effectData -> EffectsGridItemDTO(index, effectData, Data.plugin(effectData["plugin"].string))
        }

        return elements
    }
}
