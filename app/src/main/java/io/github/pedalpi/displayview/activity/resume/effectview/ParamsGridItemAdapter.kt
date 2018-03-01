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
