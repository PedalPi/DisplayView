/*
Copyright 2018 SrMouraSilva

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

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
