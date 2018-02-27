package io.github.pedalpi.displayview.activity.params

import io.github.pedalpi.displayview.model.ParamType


class ParamsListItemViewHolderFactory {
    companion object {
        @JvmStatic fun build(changable: ParamValueChangeable, type: ParamType): ParamsListItemAdapter.ParamsListItemViewHolder {
            return when (type) {
                ParamType.COMBOBOX -> ParamsListItemViewHolderCombobox(changable)
                ParamType.TOGGLE -> ParamsListItemViewHolderToggle(changable)
                else -> ParamsListItemViewHolderSlider(changable)
            }
        }
    }
}
