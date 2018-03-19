package io.github.pedalpi.displayview.activity.effects

import io.github.pedalpi.displayview.model.Effect

class EffectsListItemDTO(val effect: Effect) {

    val name = "${effect.index} - ${effect.name}"

    lateinit var viewHolder: EffectListItemViewHolder
}
