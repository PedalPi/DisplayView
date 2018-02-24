package io.github.pedalpi.displayview.params

import android.view.View
import android.widget.TextView
import io.github.pedalpi.displayview.R


class ParamsListItemViewHolderCombobox(private val adapter : ParamsListItemAdapter) : ParamsListItemAdapter.ParamsListItemViewHolder {

    private lateinit var name : TextView

    override var row: View? = null
        set(row) {
            field = row
            name = row?.findViewById(R.id.paramsListItemName) as TextView
            //name.setOnClickListener { adapter.selectEffectListener(dto) }
            //status.setOnClickListener { adapter.toggleStatusListener(dto) }
        }

    lateinit var dto: ParamsListItemDTO

    override fun update(param: ParamsListItemDTO) {
        dto = param

        name.text = param.name
        //status?.isChecked = param.status
    }

    override val layout: Int
        get() = R.layout.param_list_item_combobox
}
