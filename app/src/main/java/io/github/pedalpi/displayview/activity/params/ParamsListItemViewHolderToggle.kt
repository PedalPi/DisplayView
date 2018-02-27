package io.github.pedalpi.displayview.activity.params

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.ToggleButton
import io.github.pedalpi.displayview.R

/**
 * https://github.com/p4x3c0/PedalPi-Display-View/blob/master/app/src/main/java/com/pedalpi/pedalpi/component/ParamSeekbar.java
 */
class ParamsListItemViewHolderToggle(private val changeable: ParamValueChangeable) : ParamsListItemAdapter.ParamsListItemViewHolder {

    private lateinit var name : TextView
    private lateinit var toggle : ToggleButton

    lateinit var dto: ParamsListItemDTO

    override var row: View? = null
        set(row) {
            field = row
            name   = row?.findViewById(R.id.paramsListItemName) as TextView
            toggle = row?.findViewById(R.id.paramsListItemToggle) as ToggleButton

            toggle.setOnClickListener {
                dto.value = if (toggle.isChecked) 1 else 0
                changeable.onParamValueChange(dto)
            }
        }

    override fun update(context : Context) {
        this.update(context, dto)
    }

    override fun update(context: Context, param: ParamsListItemDTO) {
        dto = param

        name.text = param.name
        toggle.isChecked = param.value.toInt() == 1
    }

    override val layout: Int
        get() = R.layout.param_list_item_toggle
}
