package io.github.pedalpi.displayview.params


class ParamsListItemViewHolderFactory {
    companion object {
        @JvmStatic fun build(adapter : ParamsListItemAdapter, dto : ParamsListItemDTO): ParamsListItemAdapter.ParamsListItemViewHolder {
            return if (dto.type == ParamsListItemDTO.ParamType.COMBOBOX)
                ParamsListItemViewHolderCombobox(adapter)
            else
                ParamsListItemViewHolderSlider(adapter)
        }
    }
}
