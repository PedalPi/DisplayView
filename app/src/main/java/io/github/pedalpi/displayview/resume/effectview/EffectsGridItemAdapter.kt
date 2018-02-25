package io.github.pedalpi.displayview.resume.effectview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import io.github.pedalpi.displayview.R


typealias SelectEffectListener = (effect: EffectsGridItemDTO) -> Unit

class EffectsGridItemAdapter(private val context: Context, private val items: List<EffectsGridItemDTO>) : BaseAdapter() {

    var selectEffectListener: SelectEffectListener = { }

    class ViewHolder(row: View, private val adapter : EffectsGridItemAdapter) {
        val name = row.findViewById(R.id.effectsGridItemName) as Button
        var selected : Boolean = false

        private lateinit var dto: EffectsGridItemDTO

        init {
            name.setOnClickListener { adapter.selectEffectListener(dto) }
        }

        fun update(effect: EffectsGridItemDTO) {
            dto = effect
            name.text = dto.name
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.resume_effects_grid_item, null)
            viewHolder = ViewHolder(view, this)
            view.tag = viewHolder

        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        items[position].viewHolder = viewHolder
        viewHolder.update(items[position])

        return view
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }
}
