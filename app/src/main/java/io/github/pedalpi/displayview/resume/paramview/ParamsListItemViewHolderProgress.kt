package io.github.pedalpi.displayview.resume.paramview

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import io.github.pedalpi.displayview.R

/**
 * https://github.com/p4x3c0/PedalPi-Display-View/blob/master/app/src/main/java/com/pedalpi/pedalpi/component/ParamSeekbar.java
 */
class ParamsListItemViewHolderProgress: ParamsGridItemAdapter.ParamsGridItemViewHolder {

    private lateinit var name : TextView
    private lateinit var value : TextView

    private lateinit var progress : ProgressBar

    lateinit var dto: ParamsGridItemDTO

    override var row: View? = null
        set(row) {
            /*
            field = row
            name   = row?.findViewById(R.id.paramsGridItemName) as TextView
            value  = row?.findViewById(R.id.paramsGridItemValue) as TextView

            slider = row?.findViewById(R.id.paramsGridItemProgress) as SeekBar
            slider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (!fromUser)
                        return

                    value.text = "$progress%"
                    dto.value = dto.calculateValue(progress)
                    adapter.valueChangeListener(dto)
                }
            })
            */
        }

    override fun update(context : Context) {
        this.update(context, dto)
    }

    override fun update(context: Context, param: ParamsGridItemDTO) {
        /*
        dto = param

        name.text = param.name
        value.text = "${param.percent}%"
        slider.progress = param.percent
        */
    }

    override val layout: Int
        get() = R.layout.resume_params_grid_item
}