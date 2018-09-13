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

package io.github.pedalpi.displayview.util

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter


interface GenericViewHolder<DTO> {
    var dto: DTO
    val layout: Int
    var view: View?
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
            viewHolder.view = view

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
        return context.inflate(viewHolder.layout)
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
