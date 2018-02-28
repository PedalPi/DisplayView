package io.github.pedalpi.displayview.activity.resume.effectsview

import android.content.Context
import android.widget.GridView
import io.github.pedalpi.displayview.model.Effect
import io.github.pedalpi.displayview.model.Pedalboard


interface EffectSelectable {
    var onEffectSelected: SelectEffectListener
}

typealias SelectEffectListener = (effect: Effect) -> Unit

class EffectsView(private val context: Context, private val gridView: GridView) : EffectSelectable {

    private lateinit var pedalboard: Pedalboard
    private lateinit var adapter: EffectGridItemAdapter

    override var onEffectSelected: SelectEffectListener = { }

    fun update(pedalboard: Pedalboard) {
        this.pedalboard = pedalboard

        populateViews()
    }

    private fun populateViews() {
        this.adapter = EffectGridItemAdapter(this, context, generateData())

        this.gridView.adapter = adapter
        this.adapter.notifyDataSetChanged()
    }

    private fun generateData(): List<EffectGridItemDTO> {
        return pedalboard.effects.map(::EffectGridItemDTO)
    }

    fun updateEffectView(effect: Effect) {
        this.adapter[effect.index].viewHolder.update()
    }
}
