/*
Copyright 2018 SrMouraSilva

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package io.github.pedalpi.displayview.activity.params

import android.content.Context
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import io.github.pedalpi.displayview.R

/**
 * https://github.com/p4x3c0/PedalPi-Display-View/blob/master/app/src/main/java/com/pedalpi/pedalpi/component/ParamSeekbar.java
 */
class ParamListItemViewHolderSlider(private val notifier: ParamValueChangeNotifier) : ParamListItemViewHolder {

    override val layout: Int = R.layout.param_list_item_slider

    private lateinit var name : TextView
    private lateinit var value : TextView

    private lateinit var slider : SeekBar

    override lateinit var dto: ParamListItemDTO

    override var view: View? = null
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
                    dto.param.value = dto.param.calculateValueByPercent(progress)
                    notifier.onParamValueChange(dto.param)
                }
            })
        }

    override fun update(context : Context) {
        this.update(context, dto)
    }

    override fun update(context: Context, dto: ParamListItemDTO) {
        this.dto = dto

        name.text = dto.param.name
        value.text = "${dto.param.valueAsPercent}%"
        slider.progress = dto.param.valueAsPercent
    }
}
