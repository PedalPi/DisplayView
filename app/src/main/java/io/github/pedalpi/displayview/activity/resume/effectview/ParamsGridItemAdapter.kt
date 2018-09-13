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

package io.github.pedalpi.displayview.activity.resume.effectview

import android.content.Context
import android.view.View
import io.github.pedalpi.displayview.activity.params.ParamValueChangeNotifier
import io.github.pedalpi.displayview.model.ParamType
import io.github.pedalpi.displayview.util.GenericAdapter
import io.github.pedalpi.displayview.util.GenericViewHolder


interface ParamGridItemViewHolder: GenericViewHolder<ParamGridItemDTO> {
    override var dto: ParamGridItemDTO
    override val layout: Int
    override var view: View?

    fun update(paramDTO: ParamGridItemDTO)
}

class ParamsGridItemAdapter(context: Context, private val notifier: ParamValueChangeNotifier, items: List<ParamGridItemDTO>)
    : GenericAdapter<ParamGridItemDTO, ParamGridItemViewHolder>(context, items) {

    override fun generateViewHolder(dto: ParamGridItemDTO): ParamGridItemViewHolder {
        return when {
            dto.param.type == ParamType.TOGGLE -> ParamGridItemViewHolderToggle(notifier)
            else -> ParamGridItemViewHolderProgress()
        }
    }

    override fun update(item: ParamGridItemDTO, viewHolder: ParamGridItemViewHolder) {
        item.viewHolder = viewHolder
        viewHolder.update(item)
    }

    fun update(paramIndex: Int) {
        this.notifyDataSetChanged()
    }
}
