package io.github.pedalpi.displayview.resume.effectview

import android.content.Context
import android.widget.GridView
import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonElement
import io.github.pedalpi.displayview.activity.resume.effectview.EffectGridItemAdapter
import io.github.pedalpi.displayview.activity.resume.effectview.EffectGridItemDTO
import io.github.pedalpi.displayview.model.Data
import java.util.*


interface EffectSelectable {
    var onEffectSelected: SelectEffectListener
}

typealias SelectEffectListener = (effect: JsonElement) -> Unit

class EffectsView(private val activity: Context, private val gridView: GridView) : EffectSelectable {

    private lateinit var pedalboard: JsonElement
    private lateinit var adapter: EffectGridItemAdapter

    override var onEffectSelected: SelectEffectListener = { }

    fun update(pedalboard: JsonElement) {
        this.pedalboard = pedalboard

        populateViews()
    }

    private fun populateViews() {
        this.adapter = EffectGridItemAdapter(this, activity, generateData())

        this.gridView.adapter = adapter
        this.adapter.notifyDataSetChanged()
    }

    private fun generateData(): List<EffectGridItemDTO> {
        val elements = ArrayList<EffectGridItemDTO>()

        pedalboard["effects"].array.mapIndexedTo(elements) {
            index, effectData -> EffectGridItemDTO(index, effectData, Data.plugin(effectData["plugin"].string))
        }

        return elements
    }
}
