package io.github.pedalpi.displayview.activity.params

import io.github.pedalpi.displayview.model.ParamType


class ParamListItemViewHolderFactory {
    companion object {
        @JvmStatic fun build(changable: ParamValueChangeable, type: ParamType): ParamListItemViewHolder {
            return when (type) {
                ParamType.COMBOBOX -> ParamListItemViewHolderCombobox(changable)
                ParamType.TOGGLE -> ParamListItemViewHolderToggle(changable)
                else -> ParamListItemViewHolderSlider(changable)
            }
        }
    }
}
