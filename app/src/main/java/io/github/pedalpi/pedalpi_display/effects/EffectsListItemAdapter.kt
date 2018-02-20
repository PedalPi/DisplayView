package io.github.pedalpi.pedalpi_display.effects

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ToggleButton
import io.github.pedalpi.pedalpi_display.R

typealias ToggleStatusListener = (effect: EffectsListItemDTO) -> Unit
typealias SelectEffectListener = (effect: EffectsListItemDTO) -> Unit

/**
 * Based in https://github.com/betranthanh/android-KotlinListView
 */
class EffectsListItemAdapter(private val activity: Activity, private val items: List<EffectsListItemDTO>): BaseAdapter() {

    var toggleStatusListener : ToggleStatusListener = { }
    var selectEffectListener : SelectEffectListener = { }

    private class ViewHolder(private val row: View, private val adapter : EffectsListItemAdapter) {
        val name = row.findViewById(R.id.effectsListItemName) as Button
        val status = row.findViewById(R.id.effectsListItemStatus) as ToggleButton

        lateinit var dto: EffectsListItemDTO

        init {
            name.setOnClickListener { adapter.selectEffectListener(dto) }
            status.setOnClickListener { adapter.toggleStatusListener(dto) }
        }

        fun update(effect : EffectsListItemDTO) {
            dto = effect

            name.text = effect.name
            status.isChecked = effect.status
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.effects_list_item, null)
            viewHolder = ViewHolder(view, this)
            view.tag = viewHolder

        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.update(items[position])

        return view
    }

    override fun getItem(i: Int): EffectsListItemDTO {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}
