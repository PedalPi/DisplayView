package io.github.pedalpi.displayview.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter


interface GenericViewHolder<DTO> {
    var dto: DTO
    val layout: Int
    var row: View?
}

/**
 * Based in https://github.com/betranthanh/android-KotlinListView
 */
abstract class GenericAdapter<DTO, ViewHolder: GenericViewHolder<DTO>>(private val context: Context, private val items: List<DTO>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        val originalViewHolder = generateViewHolder(items[position])

        if (convertView == null || !isSameView(convertView, originalViewHolder)) {
            viewHolder = originalViewHolder

            view = generateView(viewHolder)

            view.tag = viewHolder
            viewHolder.row = view

        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        update(items[position], viewHolder)

        return view
    }

    abstract fun generateViewHolder(dto: DTO): ViewHolder

    abstract fun update(item: DTO, viewHolder: ViewHolder)

    private fun isSameView(convertView: View, viewHolder: ViewHolder): Boolean {
        return viewHolder.layout == (convertView.tag as ViewHolder).layout
    }

    private fun generateView(viewHolder: ViewHolder): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return inflater.inflate(viewHolder.layout, null)
    }

    override fun getItem(i: Int): DTO {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

    operator fun get(index: Int): DTO {
        return getItem(index)
    }
}
