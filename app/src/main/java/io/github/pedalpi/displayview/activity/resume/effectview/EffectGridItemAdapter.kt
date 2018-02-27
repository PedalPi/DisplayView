package io.github.pedalpi.displayview.activity.resume.effectview

import android.content.Context
import io.github.pedalpi.displayview.resume.effectview.EffectSelectable
import io.github.pedalpi.displayview.util.GenericAdapter


class EffectGridItemAdapter(private val selectable: EffectSelectable, private val context: Context, private val items: List<EffectGridItemDTO>)
    : GenericAdapter<EffectGridItemDTO, EffectGridItemViewHolder>(context, items) {

    override fun generateViewHolder(dto: EffectGridItemDTO): EffectGridItemViewHolder {
        return EffectGridItemViewHolder(selectable, context.resources)
    }

    override fun update(item: EffectGridItemDTO, viewHolder: EffectGridItemViewHolder) {
        item.viewHolder = viewHolder
        viewHolder.update(item)
    }
}
