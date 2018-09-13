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