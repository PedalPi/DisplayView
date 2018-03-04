package io.github.pedalpi.displayview.activity.effects

import android.app.Activity
import io.github.pedalpi.displayview.util.GenericAdapter

typealias ToggleStatusListener = (effect: EffectsListItemDTO) -> Unit
typealias SelectEffectListener = (effect: EffectsListItemDTO) -> Unit


class EffectsListItemAdapter(context: Activity, items: List<EffectsListItemDTO>)
    : GenericAdapter<EffectsListItemDTO, EffectListItemViewHolder>(context, items) {

    var toggleStatusListener : ToggleStatusListener = { }
    var selectEffectListener : SelectEffectListener = { }

    override fun generateViewHolder(dto: EffectsListItemDTO): EffectListItemViewHolder {
        return EffectListItemViewHolder(this)
    }

    override fun update(item: EffectsListItemDTO, viewHolder: EffectListItemViewHolder) {
        item.viewHolder = viewHolder
        viewHolder.update(item)
    }
}
