package io.github.pedalpi.displayview.activity.resume

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import io.github.pedalpi.displayview.R
import io.github.pedalpi.displayview.activity.resume.paramview.ParamsView
import io.github.pedalpi.displayview.communication.message.request.Messages
import io.github.pedalpi.displayview.communication.message.response.EventMessage
import io.github.pedalpi.displayview.communication.message.response.EventType
import io.github.pedalpi.displayview.communication.message.response.ResponseMessage
import io.github.pedalpi.displayview.communication.message.response.ResponseVerb
import io.github.pedalpi.displayview.communication.server.Server
import io.github.pedalpi.displayview.model.Data
import io.github.pedalpi.displayview.resume.effectview.EffectsView
import kotlinx.android.synthetic.main.activity_resume.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class ResumeActivity : AppCompatActivity() {

    private lateinit var title: TitleView
    private lateinit var effectsView: EffectsView
    private lateinit var paramsView: ParamsView

    private lateinit var progress: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        supportActionBar?.hide()

        this.title = TitleView(resumePedalboardNumber, resumePedalboardName)
        this.title.update(Data.pedalboardIndex, Data.currentPedalboard)

        this.effectsView = EffectsView(this.applicationContext, resumePedalboardEffects)
        this.effectsView.update(Data.currentPedalboard)

        this.effectsView.onEffectSelected = { paramsView.update(it) }

        this.paramsView = ParamsView(this.applicationContext, resumeEffectParams, resumeEffectName)
        this.paramsView.updateWithPedalboard(Data.currentPedalboard)

        //this.paramsView.onParamSelect { updateParamValue(it) }
        //this.paramsView.onEffectStatusToggle { requestChangeParamValue(it) }

        Server.setListener({ onMessage(it) })

        if (!Data.isDataLoaded())
            showLoading()
    }

    private fun showLoading() {
        this.progress = ProgressDialog(this)
        progress.setTitle("Connecting")
        progress.setMessage("Reading plugins data")
        progress.setCancelable(false)
        progress.show()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    private fun onMessage(message : ResponseMessage) {
        if (message.request isEquivalentTo Messages.PLUGINS) {
            progress.dismiss()

            runOnUiThread({
                this.title.update(Data.pedalboardIndex, Data.currentPedalboard)
                this.effectsView.update(Data.currentPedalboard)
                this.paramsView.updateWithPedalboard(Data.currentPedalboard)
            })

        } else if (message.request isEquivalentTo Messages.CURRENT_PEDALBOARD_DATA
                || message.verb == ResponseVerb.EVENT && EventMessage(message.content).type == EventType.CURRENT) {

            if (progress.isShowing)
                return

            runOnUiThread({
                this.title.update(Data.pedalboardIndex, Data.currentPedalboard)
                this.effectsView.update(Data.currentPedalboard)
                this.paramsView.updateWithPedalboard(Data.currentPedalboard)
            })
        }
    }
}