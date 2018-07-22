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
import android.widget.GridView
import io.github.pedalpi.displayview.model.Effect
import io.github.pedalpi.displayview.model.Pedalboard


interface EffectSelectNotifier {
    var onEffectSelected: SelectEffectListener
}

typealias SelectEffectListener = (effect: Effect) -> Unit

class EffectsView(private val context: Context, private val gridView: GridView) : EffectSelectNotifier {

    private lateinit var pedalboard: Pedalboard
    private lateinit var adapter: EffectGridItemAdapter

    override var onEffectSelected: SelectEffectListener = { }

    fun update(pedalboard: Pedalboard) {
        this.pedalboard = pedalboard
        populateViews()
    }

    private fun populateViews() {
        this.adapter = EffectGridItemAdapter(this, context, generateData())

        this.gridView.adapter = adapter
        this.adapter.notifyDataSetChanged()
    }

    private fun generateData(): List<EffectGridItemDTO> {
        return pedalboard.effects.map(::EffectGridItemDTO)
    }

    fun updateEffectView(effect: Effect) {
        this.adapter[effect.index].viewHolder.update()
    }
}
