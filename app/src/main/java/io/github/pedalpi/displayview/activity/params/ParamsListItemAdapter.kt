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

package io.github.pedalpi.displayview.activity.params

import android.content.Context
import io.github.pedalpi.displayview.model.Param
import io.github.pedalpi.displayview.util.GenericAdapter
import io.github.pedalpi.displayview.util.GenericViewHolder

typealias ValueChangedListener = (param: Param) -> Unit

interface ParamValueChangeNotifier {
    var onParamValueChange: ValueChangedListener
}

interface ParamListItemViewHolder : GenericViewHolder<ParamListItemDTO> {
    fun update(context: Context)
    fun update(context: Context, dto: ParamListItemDTO)
}


class ParamsListItemAdapter(private val context: Context, items: List<ParamListItemDTO>)
    : GenericAdapter<ParamListItemDTO, ParamListItemViewHolder>(context, items),
        ParamValueChangeNotifier {

    override var onParamValueChange: ValueChangedListener = { }

    override fun generateViewHolder(dto: ParamListItemDTO): ParamListItemViewHolder {
        return ParamListItemViewHolderFactory.build(this, dto.param.type)
    }

    override fun update(item: ParamListItemDTO, viewHolder: ParamListItemViewHolder) {
        item.viewHolder = viewHolder
        viewHolder.update(context, item)
    }
}
