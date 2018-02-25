package io.github.pedalpi.displayview.resume.paramview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import io.github.pedalpi.displayview.params.ParamsListItemDTO

typealias ValueChangedListener = (param: ParamsListItemDTO) -> Unit

/**
 * Based in https://github.com/betranthanh/android-KotlinListView
 */
class ParamsGridItemAdapter(private val context: Context, private val items: List<ParamsGridItemDTO>): BaseAdapter() {

    interface ParamsGridItemViewHolder {
        val layout: Int
        var row: View?

        fun update(context: Context)
        fun update(context: Context, param: ParamsGridItemDTO)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ParamsGridItemViewHolder

        val originalViewHolder = ParamsListItemViewHolderProgress()
        //val originalViewHolder = ParamsListItemViewHolderFactory.build(this, items[position])

        if (convertView == null || !isSameView(convertView, originalViewHolder)) {
            viewHolder = originalViewHolder

            view = generateView(viewHolder)

            view.tag = viewHolder
            viewHolder.row = view

        } else {
            view = convertView
            viewHolder = view.tag as ParamsGridItemViewHolder
        }

        items[position].viewHolder = viewHolder
        viewHolder.update(context.applicationContext, items[position])

        return view
    }

    private fun isSameView(convertView: View, viewHolder: ParamsGridItemViewHolder): Boolean {
        return viewHolder.layout == (convertView.tag as ParamsGridItemViewHolder).layout
    }

    private fun generateView(viewHolder: ParamsGridItemViewHolder): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return inflater.inflate(viewHolder.layout, null)
    }

    override fun getItem(i: Int): ParamsGridItemDTO {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

    operator fun get(i: Int): ParamsGridItemDTO {
        return getItem(i)
    }
}
