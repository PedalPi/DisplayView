package io.github.pedalpi.displayview.activity.resume.effectview

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import io.github.pedalpi.displayview.R
import io.github.pedalpi.displayview.model.ParamType


class ParamGridItemViewHolderProgress : ParamGridItemViewHolder {

    override val layout: Int = R.layout.resume_param_grid_item

    private lateinit var name: TextView
    private lateinit var value: TextView
    private lateinit var progress : ProgressBar

    override lateinit var dto: ParamGridItemDTO

    override var view: View? = null
        set(row) {
            field = row
            name   = row?.findViewById(R.id.paramsGridItemName) as TextView
            value  = row?.findViewById(R.id.paramsGridItemValue) as TextView

            progress = row?.findViewById(R.id.paramsGridItemProgress) as ProgressBar
        }

    override fun update(paramDTO: ParamGridItemDTO) {
        this.dto = paramDTO

        name.text = paramDTO.param.name
        value.text = this.valueText
        progress.progress = paramDTO.param.valueAsPercent
    }

    private val valueText: String
        get() {
            return when {
                dto.param.type == ParamType.COMBOBOX -> dto.param.optionValue
                else -> "${dto.param.valueAsPercent}%"
            }
        }
}