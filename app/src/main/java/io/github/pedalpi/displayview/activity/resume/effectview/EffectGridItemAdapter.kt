package io.github.pedalpi.displayview.resume.effectview

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import io.github.pedalpi.displayview.R


class EffectGridItemAdapter(private val selectable: EffectSelectable, private val context: Context, private val items: List<EffectGridItemDTO>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: EffectGridItemViewHolder

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.resume_effects_grid_item, null)
            viewHolder = EffectGridItemViewHolder(view, selectable, context.resources)
            view.tag = viewHolder

        } else {
            view = convertView
            viewHolder = view.tag as EffectGridItemViewHolder
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
