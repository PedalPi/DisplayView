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
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import io.github.pedalpi.displayview.R
import io.github.pedalpi.displayview.util.generateSpinnerDropdownAdapter


class ParamListItemViewHolderCombobox(private val notifier: ParamValueChangeNotifier): ParamListItemViewHolder {

    override val layout: Int = R.layout.param_list_item_combobox

    private lateinit var name: TextView
    private lateinit var combobox: Spinner
    private lateinit var next: Button
    private lateinit var previous: Button

    override lateinit var dto: ParamListItemDTO

    override var view: View? = null
        set(row) {
            field = row
            name = row?.findViewById(R.id.paramsListItemName) as TextView
            combobox = row?.findViewById(R.id.paramsListItemSpinner) as Spinner

            next = row?.findViewById(R.id.paramsListItemNext) as Button
            next.setOnClickListener { nextValue() }

            previous = row?.findViewById(R.id.paramsListItemPrevious) as Button
            previous.setOnClickListener { previousValue() }
        }

    override fun update(context : Context, dto: ParamListItemDTO) {
        this.dto = dto
        this.update(context)
    }

    override fun update(context : Context) {
        name.text = dto.param.name

        combobox.adapter = generateAdapter(context)
        combobox.setSelection(dto.param.value.toInt())

        combobox.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            /** Android call in the first time if any interaction */
            private var firstTime = true

            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (!firstTime)
                    selected(position)
                else
                    firstTime = !firstTime
            }
        }
    }

    private fun generateAdapter(context: Context)
            = context.generateSpinnerDropdownAdapter(dto.param.options)

    private fun selected(position: Int) {
        dto.param.value = position
        notifier.onParamValueChange(dto.param)
    }

    private fun nextValue() {
        var position = combobox.selectedItemPosition + 1
        if (position == combobox.adapter.count)
            position = 0

        combobox.setSelection(position)
    }

    private fun previousValue() {
        var position = combobox.selectedItemPosition - 1
        if (position == -1)
            position = combobox.adapter.count-1

        combobox.setSelection(position)
    }
}
