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

    override var row: View? = null
        set(value) {
            field = row
            name = row?.findViewById(R.id.effectsListItemName) as Button
            status = row?.findViewById(R.id.effectsListItemStatus) as ToggleButton

            name.setOnClickListener { adapter.selectEffectListener(dto) }
            status.setOnClickListener { adapter.toggleStatusListener(dto) }
        }

    fun update(effect : EffectsListItemDTO) {
        dto = effect
        update()
    }

    fun update() {
        name.text = dto.name
        status.isChecked = !dto.status
    }
}
