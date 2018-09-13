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

package io.github.pedalpi.displayview.activity.effects

import android.view.View
import android.widget.Button
import android.widget.ToggleButton
import io.github.pedalpi.displayview.R
import io.github.pedalpi.displayview.util.GenericViewHolder


class EffectListItemViewHolder(private val adapter : EffectsListItemAdapter): GenericViewHolder<EffectsListItemDTO> {

    override val layout = R.layout.effects_list_item

    private lateinit var name: Button
    private lateinit var status: ToggleButton

    override lateinit var dto: EffectsListItemDTO

    override var view: View? = null
        set(value) {
            field = view
            name = view?.findViewById(R.id.effectsListItemName) as Button
            status = view?.findViewById(R.id.effectsListItemStatus) as ToggleButton

            name.setOnClickListener { adapter.selectEffectListener(dto) }
            status.setOnClickListener { adapter.toggleStatusListener(dto) }
        }

    fun update(effect : EffectsListItemDTO) {
        dto = effect
        update()
    }

    fun update() {
        name.text = dto.name
        status.isChecked = !dto.effect.active
    }
}
