package io.github.pedalpi.displayview.activity.params

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.ToggleButton
import io.github.pedalpi.displayview.R

/**
 * https://github.com/p4x3c0/PedalPi-Display-View/blob/master/app/src/main/java/com/pedalpi/pedalpi/component/ParamSeekbar.java
 */
class ParamListItemViewHolderToggle(private val notifier: ParamValueChangeNotifier) : ParamListItemViewHolder {

    override val layout: Int = R.layout.param_list_item_toggle

    private lateinit var name : TextView
    private lateinit var toggle : ToggleButton

    override lateinit var dto: ParamListItemDTO

    override var view: View? = null
        set(row) {
            field = row
            name   = row?.findViewById(R.id.paramsListItemName) as TextView
            toggle = row?.findViewById(R.id.paramsListItemToggle) as ToggleButton

            toggle.setOnClickListener {
                dto.param.value = if (toggle.isChecked) 1 else 0
                notifier.onParamValueChange(dto.param)
            }
        }

    override fun update(context : Context) {
        this.update(context, dto)
    }

    override fun update(context: Context, dto: ParamListItemDTO) {
        this.dto = dto

        name.text = dto.param.name
        toggle.isChecked = dto.param.value.toInt() == 1
    }
}
