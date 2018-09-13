/*
Copyright 2018 SrMouraSilva

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

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
        val colorIdentifier = dto.effect.active.toString()

        name.setTextColorCompat(if (isSelected) R.color.yellow else R.color.textColorDark)
        name.isEnabled = !isSelected
        name.setBackgroundTintListCompat(EffectColorUtil.selectableColor[colorIdentifier]!!)
    }
}
