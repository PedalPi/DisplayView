package io.github.pedalpi.displayview.resume.paramview

import android.content.Context
import android.widget.GridView
import android.widget.TextView
import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonElement
import io.github.pedalpi.displayview.Data
import java.util.*


class ParamsView(private val context: Context, private val gridView: GridView, private val effectName: TextView) {

    private lateinit var effect: JsonElement
    private lateinit var plugin: JsonElement
    private lateinit var adapter: ParamsGridItemAdapter

    fun updateWithPedalboard(pedalboard: JsonElement) {
        if (pedalboard.array.size() > 0)
            this.update(pedalboard["effects"][0])
        else
            this.clear()
    }

    fun update(effect: JsonElement) {
        this.effect = effect
        this.plugin = Data.plugin(effect["plugin"].string)
        this.effectName.text = plugin["name"].string

        populateViews()
    }

    private fun populateViews() {
        this.adapter = ParamsGridItemAdapter(context, generateData(effect, plugin))

        this.gridView.adapter = adapter
        this.adapter.notifyDataSetChanged()
    }

    private fun generateData(effect: JsonElement, plugin: JsonElement): List<ParamsGridItemDTO> {
        val data = ArrayList<ParamsGridItemDTO>()

        val params = effect["params"].array
        val pluginControls = plugin["ports"]["control"]["input"].array

        for (i in (0 until params.array.size()))
            data.add(ParamsGridItemDTO(i, params[i], pluginControls[i]))

        return data
    }

    private fun clear() {
        this.gridView.adapter = null
        this.effectName.text = ""
    }
}
