package io.github.pedalpi.displayview.resume.effectview

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.widget.Button
import io.github.pedalpi.displayview.R


class EffectGridItemViewHolder(row: View, private val selectable: EffectSelectable, val resources: Resources) {

    val name = row.findViewById(R.id.effectsGridItemName) as Button

    private lateinit var dto: EffectGridItemDTO

    init {
        name.setOnClickListener { selectable.onEffectSelected(dto.effect) }
    }

    fun update(effect: EffectGridItemDTO) {
        dto = effect
        name.text = dto.name

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN)
            name.setBackgroundDrawable(drawable(effect.active))
        else
            name.background = drawable(effect.active)
    }

    private fun drawable(active: Boolean): Drawable? {
        val drawable = if (active) R.drawable.button_effect_active else R.drawable.button_effect_not_active
        return ResourcesCompat.getDrawable(resources, drawable, null)
    }
}
