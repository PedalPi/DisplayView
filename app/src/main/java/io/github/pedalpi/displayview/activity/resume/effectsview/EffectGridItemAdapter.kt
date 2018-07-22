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

package io.github.pedalpi.displayview.activity.resume.effectsview

import android.content.Context
import io.github.pedalpi.displayview.util.GenericAdapter


class EffectGridItemAdapter(private val notifier: EffectSelectNotifier, private val context: Context, private val items: List<EffectGridItemDTO>)
    : GenericAdapter<EffectGridItemDTO, EffectGridItemViewHolder>(context, items) {

    private var selected: EffectGridItemViewHolder? = null

    private val selectedNotifier = object: EffectSelectNotifier {
        override var onEffectSelected: SelectEffectListener = {
            select(it.index)
            notifyDataSetChanged()
            notifier.onEffectSelected(it)
        }
    }

    override fun generateViewHolder(dto: EffectGridItemDTO): EffectGridItemViewHolder {
        return EffectGridItemViewHolder(selectedNotifier)
    }

    override fun update(item: EffectGridItemDTO, viewHolder: EffectGridItemViewHolder) {
        item.viewHolder = viewHolder
        viewHolder.update(item)
    }

    fun select(index: Int) {
        this.unselect()

        if (validIndex(index)) {
            this.selected = this[index].viewHolder
            this.selected?.isSelected = true
        }
    }

    fun unselect() {
        this.selected?.isSelected = false
        this.selected = null
    }

    private fun validIndex(index: Int): Boolean {
        return 0 <= index && index < count
    }
}
