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
