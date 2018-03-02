package io.github.pedalpi.displayview.activity.resume.effectsview

import android.view.View
import android.widget.Button
import io.github.pedalpi.displayview.R
import io.github.pedalpi.displayview.util.EffectColorUtil
import io.github.pedalpi.displayview.util.GenericViewHolder
import io.github.pedalpi.displayview.util.setBackgroundTintListCompat
import io.github.pedalpi.displayview.util.setTextColorCompat


class EffectGridItemViewHolder(private val notifier: EffectSelectNotifier): GenericViewHolder<EffectGridItemDTO> {

    override val layout: Int = R.layout.resume_effect_grid_item

    private lateinit var name: Button

    override lateinit var dto: EffectGridItemDTO

    override var view: View? = null
        set(row) {
            field = row
            name = row?.findViewById(R.id.effectsGridItemName) as Button
            name.setOnClickListener {
                notifier.onEffectSelected(dto.effect)
            }
        }

    fun update() {
        this.update(this.dto)
    }

    fun update(dto: EffectGridItemDTO) {
        this.dto = dto
        name.text = dto.effect.name

        updateColor()
    }

    var isSelected: Boolean = false
        set(value) {
            field = value
            updateColor()
        }

    private fun updateColor() {
        val color = dto.effect.active.toString()
        if (isSelected) {
            name.setTextColorCompat(R.color.yellow)
            //name.setBackgroundColorCompat(EffectColorUtil.unselectableColor[color]!!)
            name.setBackgroundTintListCompat(EffectColorUtil.unselectableColor[color]!!)
        } else {
            name.setTextColorCompat(R.color.textColorDark)
            name.setBackgroundTintListCompat(EffectColorUtil.selectableColor[color]!!)
        }
    }
}
