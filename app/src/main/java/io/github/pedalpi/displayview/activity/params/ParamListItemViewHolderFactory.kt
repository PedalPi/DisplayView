package io.github.pedalpi.displayview.activity.params

import io.github.pedalpi.displayview.model.ParamType


class ParamListItemViewHolderFactory {
    companion object {
        @JvmStatic fun build(notifier: ParamValueChangeNotifier, type: ParamType): ParamListItemViewHolder {
            return when (type) {
                ParamType.COMBOBOX -> ParamListItemViewHolderCombobox(notifier)
                ParamType.TOGGLE -> ParamListItemViewHolderToggle(notifier)
                else -> ParamListItemViewHolderSlider(notifier)
            }
        }
    }
}
