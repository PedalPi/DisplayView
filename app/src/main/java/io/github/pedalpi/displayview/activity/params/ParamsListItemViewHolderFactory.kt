package io.github.pedalpi.displayview.activity.params


class ParamsListItemViewHolderFactory {
    companion object {
        @JvmStatic fun build(adapter : ParamsListItemAdapter, dto : ParamsListItemDTO): ParamsListItemAdapter.ParamsListItemViewHolder {
            return if (dto.type == ParamsListItemDTO.ParamType.COMBOBOX)
                ParamsListItemViewHolderCombobox(adapter)
            else if (dto.type == ParamsListItemDTO.ParamType.TOGGLE)
                ParamsListItemViewHolderToggle(adapter)
            else
                ParamsListItemViewHolderSlider(adapter)
        }
    }
}
