package io.github.pedalpi.displayview.activity.resume

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.int
import com.github.salomonbrys.kotson.string
import io.github.pedalpi.displayview.R
import io.github.pedalpi.displayview.activity.resume.effectsview.EffectsView
import io.github.pedalpi.displayview.activity.resume.effectview.EffectView
import io.github.pedalpi.displayview.communication.message.request.Messages
import io.github.pedalpi.displayview.communication.message.response.EventMessage
import io.github.pedalpi.displayview.communication.message.response.EventType
import io.github.pedalpi.displayview.communication.message.response.ResponseMessage
import io.github.pedalpi.displayview.communication.message.response.ResponseVerb
import io.github.pedalpi.displayview.communication.server.Server
import io.github.pedalpi.displayview.model.Data
import io.github.pedalpi.displayview.model.Effect
import io.github.pedalpi.displayview.model.Param
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

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        supportActionBar?.hide()

        this.title = TitleView(resumePedalboardNumber, resumePedalboardName)
        this.effectsView = EffectsView(this, resumePedalboardEffects)
        this.effectsView.onEffectSelected = { effectView.update(it) }

        this.effectView = EffectView(this, resumeEffectParams, resumeEffectName, resumeEffectStatus)

        this.effectView.onParamValueChange = { requestChangeParamValue(it) }
        this.effectView.onEffectToggleStatus = { onEffectChangeStatus(it) }

        Server.setListener({ onMessage(it) })
        this.update()

        if (!Data.isDataLoaded())
            showLoading()
    }

    private fun update() {
        runOnUiThread({
            this.title.update(Data.currentPedalboard)
            this.effectsView.update(Data.currentPedalboard)
            this.effectView.updateWithPedalboard(Data.currentPedalboard)
        })
    }

    private fun showLoading() {
        progress = ProgressDialog(this)
        progress.setTitle("Connecting")
        progress.setMessage("Reading plugins data")
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
        Server.sendBroadcast(Messages.CURRENT_PEDALBOARD_TOGGLE_EFFECT(effect))
    }

    private fun requestChangeParamValue(param: Param) {
        Server.sendBroadcast(Messages.PARAM_VALUE_CHANGE(param.effect.index, param))
    }

    private fun onMessage(message : ResponseMessage) {
        if (message.verb == ResponseVerb.ERROR) {
            runOnUiThread({
                Toast.makeText(applicationContext, message.content["message"].string, Toast.LENGTH_SHORT).show()
            })

        } else if (message.request isEquivalentTo Messages.PLUGINS) {
            Server.sendBroadcast(Messages.CURRENT_PEDALBOARD_DATA)
            runOnUiThread({
                progress.setMessage("Reading current pedalboard data")
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
                Server.sendBroadcast(Messages.CURRENT_PEDALBOARD_DATA)

            } else if (event.type == EventType.PEDALBOARD) {
                Server.sendBroadcast(Messages.CURRENT_PEDALBOARD_DATA)

            } else if (event.type == EventType.EFFECT) {
                Server.sendBroadcast(Messages.CURRENT_PEDALBOARD_DATA)

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
}
