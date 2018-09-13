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