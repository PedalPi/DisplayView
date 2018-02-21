package io.github.pedalpi.displayview.params

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

/**
 * Based in https://github.com/betranthanh/android-KotlinListView
 */
class ParamsListItemAdapter(private val activity: Activity, private val items: List<ParamsListItemDTO>): BaseAdapter() {

    //var toggleStatusListener : ToggleStatusListener = { }
    //var selectEffectListener : SelectEffectListener = { }

    interface ParamsListItemViewHolder {
        fun update(param : ParamsListItemDTO)
        val layout: Int
        var row: View?
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ParamsListItemViewHolder

        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            viewHolder = buildViewHolder(position)
            view = inflater.inflate(viewHolder.layout, null)

            view.tag = viewHolder
            viewHolder.row = view

        } else {
            view = convertView
            viewHolder = view.tag as ParamsListItemViewHolder
        }

        viewHolder.update(items[position])

        return view
    }

    private fun buildViewHolder(position: Int): ParamsListItemViewHolder {
        return if (position % 2 == 0)
            ParamsListItemViewHolderCombobox(this)
        else
            ParamsListItemViewHolderSlider(this)
    }

    override fun getItem(i: Int): ParamsListItemDTO {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}
