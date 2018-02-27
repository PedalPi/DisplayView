package io.github.pedalpi.displayview.activity.params

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

typealias ValueChangedListener = (param: ParamsListItemDTO) -> Unit

interface ParamValueChangeable {
    var onParamValueChange: ValueChangedListener
}


class ParamsListItemAdapter(private val activity: Activity, private val items: List<ParamsListItemDTO>): BaseAdapter(), ParamValueChangeable {

    override var onParamValueChange: ValueChangedListener = { }

    interface ParamsListItemViewHolder {
        val layout: Int
        var row: View?

        fun update(context: Context)
        fun update(context: Context, param: ParamsListItemDTO)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ParamsListItemViewHolder

        val originalViewHolder = ParamsListItemViewHolderFactory.build(this, items[position].type)

        if (convertView == null || !isSameView(convertView, originalViewHolder)) {
            viewHolder = originalViewHolder

            view = generateView(viewHolder)

            view.tag = viewHolder
            viewHolder.row = view

        } else {
            view = convertView
            viewHolder = view.tag as ParamsListItemViewHolder
        }

        items[position].viewHolder = viewHolder
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

    operator fun get(i: Int): ParamsListItemDTO {
        return getItem(i)
    }
}
