package io.github.pedalpi.displayview.activity.resume.effectview

import android.content.Context
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.TextView
import android.widget.ToggleButton
import io.github.pedalpi.displayview.activity.params.ParamValueChangeNotifier
import io.github.pedalpi.displayview.activity.params.ValueChangedListener
import io.github.pedalpi.displayview.model.Effect
import io.github.pedalpi.displayview.model.Pedalboard


typealias EffectToggleStatusListener = (effect: Effect) -> Unit

class EffectViewParamValueChangeNotifier(private val paramsView: EffectView): ParamValueChangeNotifier {
    override var onParamValueChange: ValueChangedListener
        get() = {
            paramsView.updateParamView(it.index)
            paramsView.onParamValueChange(it)
        }
        set(value) {}
}

class EffectView(
        private val context: Context,
        private val gridView: GridView,
        private val effectName: TextView,
        private val effectStatus: ToggleButton
) {

    var effect: Effect? = null
    private lateinit var adapter: ParamsGridItemAdapter

    private val valueChangeNotifier = EffectViewParamValueChangeNotifier(this)

    var onParamValueChange: ValueChangedListener = {}
    var onEffectToggleStatus: EffectToggleStatusListener = {}
    val paramDialog = ParamDialog(context, valueChangeNotifier)

    init {
        clear()

        gridView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val viewHolder = view.tag as ParamGridItemViewHolder
            this.paramDialog.show(viewHolder.dto.param)
        }

        effectStatus.setOnClickListener {
            effect!!.active = !effect!!.active
            onEffectToggleStatus(effect!!)
        }
    }

    /**
     * Change the view for the first effect of the pedalboard
     * or show none if pedalboard haven't effects
     */
    fun updateWithPedalboard(pedalboard: Pedalboard) {
        if (pedalboard.effects.isNotEmpty())
            this.update(pedalboard.effects[0])
        else
            this.clear()
    }

    fun update(effect: Effect?) {
        this.effect = effect

        this.effectName.text = effect?.name ?: ""
        this.effectStatus.visibility = if (effect != null) View.VISIBLE else View.INVISIBLE
        this.effectStatus.isChecked = effect?.active ?: false

        effect?.let {
            populateViews(effect)
        }
    }

    private fun populateViews(effect: Effect) {
        this.adapter = ParamsGridItemAdapter(context, valueChangeNotifier, generateData(effect))

        this.gridView.adapter = adapter
        this.adapter.notifyDataSetChanged()
    }

    private fun generateData(effect: Effect): List<ParamGridItemDTO> {
        return effect.params.map { ParamGridItemDTO(it) }
    }

    fun clear() {
        this.gridView.adapter = null

        this.update(null)
    }

    /**
     * Update the param view
     */
    fun updateParamView(paramIndex: Int) {
        adapter.update(paramIndex)
        paramDialog.update()
    }

    /**
     * Update only the button status
     */
    fun updateEffectStatusView() {
        this.effectStatus.isChecked = effect?.active ?: false
    }
}
