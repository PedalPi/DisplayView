package io.github.pedalpi.displayview.activity.resume.paramview

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import io.github.pedalpi.displayview.R
import io.github.pedalpi.displayview.activity.params.ParamsListItemAdapter
import io.github.pedalpi.displayview.activity.params.ParamsListItemDTO

/**
 * https://github.com/p4x3c0/PedalPi-Display-View/blob/master/app/src/main/java/com/pedalpi/pedalpi/component/ParamSeekbar.java
 */
class ParamsListItemViewHolderProgress: ParamsListItemAdapter.ParamsListItemViewHolder {

    private lateinit var name : TextView
    private lateinit var value : TextView

    private lateinit var progress : ProgressBar

    lateinit var dto: ParamsListItemDTO

    override var row: View? = null
        set(row) {
            field = row
            name   = row?.findViewById(R.id.paramsGridItemName) as TextView
            value  = row?.findViewById(R.id.paramsGridItemValue) as TextView

            progress = row?.findViewById(R.id.paramsGridItemProgress) as ProgressBar
        }

    override fun update(context : Context) {
        this.update(context, dto)
    }

    override fun update(context: Context, param: ParamsListItemDTO) {
        dto = param

        name.text = param.name
        value.text = "${param.percent}%"
        progress.progress = param.percent
    }

    override val layout: Int
        get() = R.layout.resume_params_grid_item
}