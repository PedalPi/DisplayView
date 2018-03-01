package io.github.pedalpi.displayview.activity.resume.effectview

import android.content.Context
import android.view.ViewGroup
import io.github.pedalpi.displayview.activity.params.ParamListItemDTO
import io.github.pedalpi.displayview.activity.params.ParamListItemViewHolder
import io.github.pedalpi.displayview.activity.params.ParamListItemViewHolderFactory
import io.github.pedalpi.displayview.activity.params.ParamValueChangeNotifier
import io.github.pedalpi.displayview.model.Param
import io.github.pedalpi.displayview.util.generateCustomDialog
import io.github.pedalpi.displayview.util.inflate


class ParamDialog(private val context: Context, private val notifier: ParamValueChangeNotifier) {

    private var viewHolder: ParamListItemViewHolder? = null

    fun show(param: Param) {
        val dto = ParamListItemDTO(param)

        val viewHolder = ParamListItemViewHolderFactory.build(notifier, param.type)
        this.viewHolder = viewHolder

        val view = context.inflate(viewHolder.layout) as ViewGroup

        viewHolder.view = view
        dto.viewHolder = viewHolder
        viewHolder.update(context, dto)

        val dialog = context.generateCustomDialog(view)
        dialog.setOnCancelListener { this.viewHolder = null }
        dialog.show()
    }

    fun update() {
        this.viewHolder?.update(context)
    }
}
