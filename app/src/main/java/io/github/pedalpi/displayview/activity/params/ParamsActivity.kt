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

package io.github.pedalpi.displayview.activity.params

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.ListView
import android.widget.Toast
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.int
import com.github.salomonbrys.kotson.string
import io.github.pedalpi.displayview.R
import io.github.pedalpi.displayview.activity.effects.EffectsActivity
import io.github.pedalpi.displayview.communication.adb.message.EventMessage
import io.github.pedalpi.displayview.communication.adb.message.EventType
import io.github.pedalpi.displayview.communication.base.Communicator
import io.github.pedalpi.displayview.communication.base.message.Messages
import io.github.pedalpi.displayview.communication.base.message.ResponseMessage
import io.github.pedalpi.displayview.communication.base.message.ResponseVerb
import io.github.pedalpi.displayview.model.Data
import io.github.pedalpi.displayview.model.Effect
import io.github.pedalpi.displayview.model.Param
import io.github.pedalpi.displayview.util.popToRoot


class ParamsActivity : AppCompatActivity() {
    private var index: Int = 0
    private lateinit var effect: Effect

    private lateinit var listView: ListView
    private lateinit var adapter: ParamsListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_params)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.index = intent.getIntExtra(EffectsActivity.EFFECT_INDEX, 0)
        this.effect = Data.currentPedalboard.effects[index]

        title = this.effect.name

        populateViews()

        Communicator.setOnMessageListener({ onMessage(it) })
    }

    private fun populateViews() {
        this.listView = findViewById(R.id.paramsList) as ListView
        this.adapter = ParamsListItemAdapter(this, generateData(this.effect))

        this.adapter.onParamValueChange = { requestChangeParamValue(it) }

        this.listView.adapter = adapter
        this.adapter.notifyDataSetChanged()
    }

    private fun generateData(effect: Effect): List<ParamListItemDTO> {
        return effect.params.map { ParamListItemDTO(it) }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun requestChangeParamValue(param: Param) {
        Communicator.send(Messages.Companion.PARAM_VALUE_CHANGE(this.index, param))
    }

    private fun onMessage(message: ResponseMessage) {
        if (message.verb == ResponseVerb.ERROR)
            runOnUiThread({
                Toast.makeText(applicationContext, message.content["message"].string, Toast.LENGTH_SHORT).show()
            })

        else if (message.verb == ResponseVerb.EVENT) {
            val event = EventMessage(message.content)

            if (event.type == EventType.CURRENT) {
                this.popToRoot()

            } else if (event.type == EventType.PARAM) {
                if (event.content["effect"].int != index)
                    return

                updateParamValue(adapter[event.content["param"].int])
            }
        }
    }

    private fun updateParamValue(dto: ParamListItemDTO) {
        runOnUiThread {
            dto.viewHolder.update(applicationContext)
        }
    }
}
