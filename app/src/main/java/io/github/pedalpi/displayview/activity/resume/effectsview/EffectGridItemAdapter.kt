package io.github.pedalpi.displayview.activity.resume.effectsview

import android.content.Context
import io.github.pedalpi.displayview.util.GenericAdapter


class EffectGridItemAdapter(private val notifier: EffectSelectNotifier, private val context: Context, private val items: List<EffectGridItemDTO>)
    : GenericAdapter<EffectGridItemDTO, EffectGridItemViewHolder>(context, items) {

    private var selected: EffectGridItemViewHolder? = null

    private val selectedNotifier = object: EffectSelectNotifier {
        override var onEffectSelected: SelectEffectListener = {
            select(it.index)
            notifier.onEffectSelected(it)
        }
    }

    override fun generateViewHolder(dto: EffectGridItemDTO): EffectGridItemViewHolder {
        return EffectGridItemViewHolder(selectedNotifier)
    }

    override fun update(item: EffectGridItemDTO, viewHolder: EffectGridItemViewHolder) {
        item.viewHolder = viewHolder
        viewHolder.update(item)
    }

    fun select(index: Int) {
        this.unselect()

        if (validIndex(index)) {
            this.selected = this[index].viewHolder
            this.selected?.isSelected = true
        }
    }

    fun unselect() {
        this.selected?.isSelected = false
        this.selected = null
    }

    private fun validIndex(index: Int): Boolean {
        return 0 <= index && index < count
    }
}
