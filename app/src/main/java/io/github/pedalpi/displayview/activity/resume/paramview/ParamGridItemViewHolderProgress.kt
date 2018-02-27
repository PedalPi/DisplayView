package io.github.pedalpi.displayview.activity.resume.paramview

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import io.github.pedalpi.displayview.R


class ParamGridItemViewHolderProgress : ParamGridItemViewHolder {

    override val layout: Int = R.layout.resume_params_grid_item

    private lateinit var name: TextView
    private lateinit var value: TextView
    private lateinit var progress : ProgressBar

    override lateinit var dto: ParamGridItemDTO

    override var row: View? = null
        set(row) {
            field = row
            name   = row?.findViewById(R.id.paramsGridItemName) as TextView
            value  = row?.findViewById(R.id.paramsGridItemValue) as TextView

            progress = row?.findViewById(R.id.paramsGridItemProgress) as ProgressBar
        }

    override fun update(paramDTO: ParamGridItemDTO) {
        this.dto = paramDTO

        name.text = paramDTO.param.name
        value.text = "${paramDTO.param.valueAsPercent}%"
        progress.progress = paramDTO.param.valueAsPercent
    }
}