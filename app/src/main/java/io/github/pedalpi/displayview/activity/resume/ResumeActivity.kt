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

package io.github.pedalpi.displayview.activity.resume

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.int
import com.github.salomonbrys.kotson.string
import io.github.pedalpi.displayview.R
import io.github.pedalpi.displayview.activity.ConfigureInformationActivity
import io.github.pedalpi.displayview.activity.resume.effectsview.EffectsView
import io.github.pedalpi.displayview.activity.resume.effectview.EffectView
import io.github.pedalpi.displayview.communication.adb.message.EventMessage
import io.github.pedalpi.displayview.communication.adb.message.EventType
import io.github.pedalpi.displayview.communication.adb.message.toSerial
import io.github.pedalpi.displayview.communication.base.Communicator
import io.github.pedalpi.displayview.communication.base.message.Messages
import io.github.pedalpi.displayview.communication.base.message.ResponseMessage
import io.github.pedalpi.displayview.communication.base.message.ResponseVerb
import io.github.pedalpi.displayview.model.Data
import io.github.pedalpi.displayview.model.Effect
import io.github.pedalpi.displayview.model.Param
import io.github.pedalpi.displayview.util.isDebugActive
import io.github.pedalpi.displayview.util.strictModePermitAll
import kotlinx.android.synthetic.main.activity_resume.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class ResumeActivity : AppCompatActivity() {

    private lateinit var title: TitleView
    private lateinit var effectsView: EffectsView
    private lateinit var effectView: EffectView

    private lateinit var progress: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume)

        strictModePermitAll()

        if (!this.isDebugActive) {
            this.goToConfigureInformation()
            return
        }

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        supportActionBar?.hide()

        this.title = TitleView(resumePedalboardNumber, resumePedalboardName)
        this.effectsView = EffectsView(this, resumePedalboardEffects)
        this.effectsView.onEffectSelected = { effectView.update(it) }

        this.effectView = EffectView(this, resumeEffectParams, resumeEffectName, resumeEffectStatus)

        this.effectView.onParamValueChange = { requestChangeParamValue(it) }
        this.effectView.onEffectToggleStatus = { onEffectChangeStatus(it) }

        this.progress = ProgressDialog(this)

        Communicator.setOnMessageListener { onMessage(it) }
        Communicator.setOnConnectedListener {
            if (!Data.isDataLoaded()) {
                Communicator.send(Messages.PLUGINS.toSerial())
                runOnUiThread { progress.setMessage(getString(R.string.reading_plugins_data)) }
            } else
                runOnUiThread { progress.dismiss() }
        }
        Communicator.setOnDisconnectedListener { runOnUiThread { showLoading(getString(R.string.trying_reconnect)) } }
        this.update()

        if (!Data.isDataLoaded())
            showLoading(getString(R.string.waiting_connection))
    }

    private fun update() {
        runOnUiThread({
            this.title.update(Data.currentPedalboard)
            this.effectsView.update(Data.currentPedalboard)
            this.effectView.clear()
            //this.effectView.updateWithPedalboard(Data.currentPedalboard)
        })
    }

    private fun showLoading(message: String) {
        progress.setTitle(getString(R.string.connecting))
        progress.setMessage(message)
        progress.setCancelable(false)
        progress.show()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    private fun onEffectChangeStatus(effect: Effect) {
        runOnUiThread { this.effectsView.updateEffectView(effect) }
        requestToggleEffectStatus(effect)
    }

    private fun requestToggleEffectStatus(effect: Effect) {
        Communicator.send(Messages.CURRENT_PEDALBOARD_TOGGLE_EFFECT(effect))
    }

    private fun requestChangeParamValue(param: Param) {
        Communicator.send(Messages.PARAM_VALUE_CHANGE(param.effect.index, param))
    }

    private fun onMessage(message : ResponseMessage) {
        if (message.verb == ResponseVerb.ERROR) {
            runOnUiThread({
                Toast.makeText(applicationContext, message.content["message"].string, Toast.LENGTH_SHORT).show()
            })

        } else if (message.request isEquivalentTo Messages.PLUGINS) {
            Communicator.send(Messages.CURRENT_PEDALBOARD_DATA)
            runOnUiThread({
                progress.setMessage(getString(R.string.reading_current_pedalboard_data))
            })

        } else if (message.request isEquivalentTo Messages.CURRENT_PEDALBOARD_DATA) {
            if (progress.isShowing)
                progress.dismiss()

            update()

        } else if (message.verb == ResponseVerb.EVENT) {
            val event = EventMessage(message.content)

            if (event.type == EventType.CURRENT) {
                update()

            } else if (event.type == EventType.BANK) {
                Communicator.send(Messages.CURRENT_PEDALBOARD_DATA)

            } else if (event.type == EventType.PEDALBOARD) {
                Communicator.send(Messages.CURRENT_PEDALBOARD_DATA)

            } else if (event.type == EventType.EFFECT) {
                Communicator.send(Messages.CURRENT_PEDALBOARD_DATA)

            } else if (event.type == EventType.EFFECT_TOGGLE) {
                this.updateEffectStatus(event.content["effect"].int)

            } else if (event.type == EventType.PARAM) {
                val index = event.content["param"].int
                runOnUiThread { this.effectView.updateParamView(index) }
            }
        }
    }

    private fun updateEffectStatus(index: Int) {
        val effect = Data.currentPedalboard.effects[index]

        runOnUiThread { this.effectsView.updateEffectView(effect) }
        if (this.effectView.effect == effect)
            runOnUiThread { this.effectView.updateEffectStatusView() }
    }

    private fun goToConfigureInformation() {
        val intent = Intent(baseContext, ConfigureInformationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

        startActivity(intent)
    }
}
