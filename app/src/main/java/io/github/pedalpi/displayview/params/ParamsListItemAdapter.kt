package io.github.pedalpi.displayview.params

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

typealias ValueChangedListener = (param: ParamsListItemDTO) -> Unit

/**
 * Based in https://github.com/betranthanh/android-KotlinListView
 */
class ParamsListItemAdapter(private val activity: Activity, private val items: List<ParamsListItemDTO>): BaseAdapter() {

    var valueChangeListener : ValueChangedListener = { }

    interface ParamsListItemViewHolder {
        fun update(context: Context, param: ParamsListItemDTO)
        val layout: Int
        var row: View?
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ParamsListItemViewHolder

        val originalViewHolder = ParamsListItemViewHolderFactory.build(this, items[position])

        if (convertView == null || !isSameView(convertView, originalViewHolder)) {
            viewHolder = originalViewHolder

            view = generateView(viewHolder)

            view.tag = viewHolder
            viewHolder.row = view

        } else {
            view = convertView
            viewHolder = view.tag as ParamsListItemViewHolder
        }

        viewHolder.update(activity.applicationContext, items[position])

        return view
    }

    private fun isSameView(convertView: View, viewHolder: ParamsListItemViewHolder): Boolean {
        return viewHolder.layout == (convertView.tag as ParamsListItemViewHolder).layout
    }

    private fun generateView(viewHolder: ParamsListItemViewHolder): View {
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return inflater.inflate(viewHolder.layout, null)
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
