package io.github.pedalpi.displayview.activity.resume.paramview

import android.content.Context
import android.view.View
import io.github.pedalpi.displayview.util.GenericAdapter
import io.github.pedalpi.displayview.util.GenericViewHolder


interface ParamGridItemViewHolder: GenericViewHolder<ParamGridItemDTO> {
    override var dto: ParamGridItemDTO
    override val layout: Int
    override var row: View?

    fun update(paramDTO: ParamGridItemDTO)
}

class ParamsGridItemAdapter(context: Context, items: List<ParamGridItemDTO>)
    : GenericAdapter<ParamGridItemDTO, ParamGridItemViewHolder>(context, items) {

    override fun generateViewHolder(dto: ParamGridItemDTO): ParamGridItemViewHolder {
        return ParamGridItemViewHolderProgress()
    }

    override fun update(item: ParamGridItemDTO, viewHolder: ParamGridItemViewHolder) {
        item.viewHolder = viewHolder
        viewHolder.update(item)
    }
}
