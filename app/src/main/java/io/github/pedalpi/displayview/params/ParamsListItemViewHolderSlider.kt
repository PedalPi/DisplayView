package io.github.pedalpi.displayview.params

import android.content.Context
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import io.github.pedalpi.displayview.R

/**
 * https://github.com/p4x3c0/PedalPi-Display-View/blob/master/app/src/main/java/com/pedalpi/pedalpi/component/ParamSeekbar.java
 */
class ParamsListItemViewHolderSlider(private val adapter : ParamsListItemAdapter) : ParamsListItemAdapter.ParamsListItemViewHolder {

    private lateinit var name : TextView
    private lateinit var value : TextView

    private lateinit var slider : SeekBar

    lateinit var dto: ParamsListItemDTO

    override var row: View? = null
        set(row) {
            field = row
            name   = row?.findViewById(R.id.paramsListItemName) as TextView
            value  = row?.findViewById(R.id.paramsListItemValue) as TextView

            slider = row?.findViewById(R.id.paramsListItemSlider) as SeekBar
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
        }

    override fun update(context : Context) {
        this.update(context, dto)
    }

    override fun update(context: Context, param: ParamsListItemDTO) {
        dto = param

        name.text = param.name
        value.text = "${param.percent}%"
        slider.progress = param.percent
    }

    override val layout: Int
        get() = R.layout.param_list_item_slider
}