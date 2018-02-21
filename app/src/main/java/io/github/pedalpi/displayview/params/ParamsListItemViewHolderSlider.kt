package io.github.pedalpi.displayview.params

import android.view.View
import io.github.pedalpi.displayview.R

class ViewHolderCombobox(private val row: View, private val adapter : ParamsListItemAdapter) {

    lateinit var dto: ParamsListItemDTO

    init {
        //name.setOnClickListener { adapter.selectEffectListener(dto) }
        //status.setOnClickListener { adapter.toggleStatusListener(dto) }
    }

    fun update(effect : ParamsListItemDTO) {
        dto = effect

        //name.text = effect.name
        //status.isChecked = effect.status
    }

    fun view(): Int {
        return R.layout.param_list_item_combobox
    }
}