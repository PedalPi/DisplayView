package io.github.pedalpi.displayview.activity.resume.effectsview

import io.github.pedalpi.displayview.model.Effect


class EffectGridItemDTO(val effect: Effect) {

    @Deprecated("Use effect.active instead", ReplaceWith("effect.active"))
    val active
        get() = effect.active

    @Deprecated("Use effect.name instead", ReplaceWith("effect.name"))
    val name
        get() = effect.name

    lateinit var viewHolder: EffectGridItemViewHolder
}
