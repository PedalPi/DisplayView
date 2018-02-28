package io.github.pedalpi.displayview.activity.effects

import io.github.pedalpi.displayview.model.Effect

class EffectsListItemDTO(val effect: Effect) {

    @Deprecated("Use effect.active instead", ReplaceWith("effect.active"))
    val status: Boolean
        get() = effect.active

    val name = "${effect.index} - ${effect.name}"

    lateinit var viewHolder: EffectListItemViewHolder
}
