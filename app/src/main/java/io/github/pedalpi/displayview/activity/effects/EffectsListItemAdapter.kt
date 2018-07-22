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

package io.github.pedalpi.displayview.activity.effects

import android.app.Activity
import io.github.pedalpi.displayview.util.GenericAdapter

typealias ToggleStatusListener = (effect: EffectsListItemDTO) -> Unit
typealias SelectEffectListener = (effect: EffectsListItemDTO) -> Unit


class EffectsListItemAdapter(context: Activity, items: List<EffectsListItemDTO>)
    : GenericAdapter<EffectsListItemDTO, EffectListItemViewHolder>(context, items) {

    var toggleStatusListener : ToggleStatusListener = { }
    var selectEffectListener : SelectEffectListener = { }

    override fun generateViewHolder(dto: EffectsListItemDTO): EffectListItemViewHolder {
        return EffectListItemViewHolder(this)
    }

    override fun update(item: EffectsListItemDTO, viewHolder: EffectListItemViewHolder) {
        item.viewHolder = viewHolder
        viewHolder.update(item)
    }
}
