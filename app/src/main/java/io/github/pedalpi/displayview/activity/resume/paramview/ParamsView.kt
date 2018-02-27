package io.github.pedalpi.displayview.activity.resume.paramview

import android.content.Context
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.TextView
import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonElement
import io.github.pedalpi.displayview.activity.params.ParamListItemDTO
import io.github.pedalpi.displayview.activity.params.ParamListItemViewHolderFactory
import io.github.pedalpi.displayview.activity.params.ParamValueChangeable
import io.github.pedalpi.displayview.activity.params.ValueChangedListener
import io.github.pedalpi.displayview.model.Data
import io.github.pedalpi.displayview.model.Param
import io.github.pedalpi.displayview.util.generateCustomDialog
import io.github.pedalpi.displayview.util.inflate


class ParamsView(private val context: Context, private val gridView: GridView, private val effectName: TextView): ParamValueChangeable {

    override var onParamValueChange: ValueChangedListener = {
        Log.i("O LOCO MEU", "${it.name} - ${it.value}")
    }

    private lateinit var effect: JsonElement
    private lateinit var plugin: JsonElement
    private lateinit var adapter: ParamsGridItemAdapter

    init {
        gridView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val viewHolder = view.tag as ParamGridItemViewHolder
            showDialog(ParamListItemDTO(viewHolder.dto.param))
        }
    }

    fun updateWithPedalboard(pedalboard: JsonElement) {
        if (pedalboard["effects"].array.size() > 0)
            this.update(pedalboard["effects"][0])
        else
            this.clear()
    }

    fun update(effect: JsonElement) {
        this.effect = effect
        this.plugin = Data.plugin(effect["plugin"].string)
        this.effectName.text = plugin["name"].string

        populateViews()
    }

    private fun populateViews() {
        this.adapter = ParamsGridItemAdapter(context, generateData(effect, plugin))

        this.gridView.adapter = adapter
        this.adapter.notifyDataSetChanged()
    }

    private fun generateData(effect: JsonElement, plugin: JsonElement): List<ParamGridItemDTO> {
        // TODO Create effect model and moves it
        val params = effect["params"].array
        val pluginControls = plugin["ports"]["control"]["input"].array

        return (0 until params.array.size())
                .map { Param(it, params[it], pluginControls[it]) }
                .map { ParamGridItemDTO(it) }
    }

    private fun clear() {
        this.gridView.adapter = null
        this.effectName.text = ""
    }

    private fun showDialog(item: ParamListItemDTO) {
        Log.i("PARAM", item.param.name)

        val viewHolder = ParamListItemViewHolderFactory.build(this, item.param.type)

        val view = context.inflate(viewHolder.layout)

        viewHolder.row = view
        item.viewHolder = viewHolder
        viewHolder.update(context, item)

        context.generateCustomDialog(view).show()
    }
}
