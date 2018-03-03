package io.github.pedalpi.displayview.activity.resume.effectview

import android.view.View
import android.widget.TextView
import android.widget.ToggleButton
import io.github.pedalpi.displayview.R
import io.github.pedalpi.displayview.activity.params.ParamValueChangeNotifier


class ParamGridItemViewHolderToggle(private val notifier: ParamValueChangeNotifier) : ParamGridItemViewHolder {

    override val layout: Int = R.layout.resume_param_grid_item_toggle

    private lateinit var name: TextView
    private lateinit var toggle: ToggleButton

    override lateinit var dto: ParamGridItemDTO

    override var view: View? = null
        set(row) {
            field = row
            name   = row?.findViewById(R.id.paramsGridItemName) as TextView
            toggle = row?.findViewById(R.id.paramGridItemToggle) as ToggleButton

            toggle.setOnClickListener {
                this.dto.param.value = if (this.dto.param.value.toInt() == 1) 0 else 1

                notifier.onParamValueChange(this.dto.param)
            }
        }

    override fun update(paramDTO: ParamGridItemDTO) {
        this.dto = paramDTO

        name.text = paramDTO.param.name
        toggle.isChecked = paramDTO.param.value.toInt() == 1
    }
}