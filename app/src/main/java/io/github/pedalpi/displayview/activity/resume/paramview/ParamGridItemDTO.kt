package io.github.pedalpi.displayview.resume.paramview

import io.github.pedalpi.displayview.model.Param


class ParamGridItemDTO(val index : Int, val param: Param) {
    lateinit var viewHolder: ParamsGridItemAdapter.ParamGridItemViewHolder
}
