package io.github.pedalpi.displayview.params

import android.content.Context
import android.view.View
import android.widget.*
import io.github.pedalpi.displayview.R


class ParamsListItemViewHolderCombobox(private val adapter : ParamsListItemAdapter) : ParamsListItemAdapter.ParamsListItemViewHolder {

    private lateinit var name: TextView
    private lateinit var combobox: Spinner
    private lateinit var next: Button
    private lateinit var previous: Button

    lateinit var dto: ParamsListItemDTO

    override var row: View? = null
        set(row) {
            field = row
            name = row?.findViewById(R.id.paramsListItemName) as TextView
            combobox = row?.findViewById(R.id.paramsListItemSpinner) as Spinner

            next = row?.findViewById(R.id.paramsListItemNext) as Button
            next.setOnClickListener { nextValue() }

            previous = row?.findViewById(R.id.paramsListItemPrevious) as Button
            previous.setOnClickListener { previousValue() }
        }

    override fun update(context : Context, param: ParamsListItemDTO) {
        dto = param
        this.update(context)
    }

    override fun update(context : Context) {
        name.text = dto.name

        combobox.adapter = generateAdapter(context)
        combobox.setSelection(dto.value.toInt())

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

    private fun generateAdapter(context: Context): ArrayAdapter<String> {
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, dto.options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        return adapter
    }

    override val layout: Int
        get() = R.layout.param_list_item_combobox

    private fun selected(position: Int) {
        dto.value = position
        adapter.valueChangeListener(dto)
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
