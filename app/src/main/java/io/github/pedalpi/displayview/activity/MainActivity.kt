package io.github.pedalpi.displayview.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import io.github.pedalpi.displayview.R
import io.github.pedalpi.displayview.activity.effects.EffectsActivity
import io.github.pedalpi.displayview.communication.adb.message.EventMessage
import io.github.pedalpi.displayview.communication.adb.message.EventType
import io.github.pedalpi.displayview.communication.base.Communicator
import io.github.pedalpi.displayview.communication.base.message.Messages
import io.github.pedalpi.displayview.communication.base.message.ResponseMessage
import io.github.pedalpi.displayview.communication.base.message.ResponseVerb
import io.github.pedalpi.displayview.model.Data
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class MainActivity : AppCompatActivity() {

    private lateinit var progress: ProgressDialog

    private lateinit var number: TextView
    private lateinit var name: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        supportActionBar?.hide()

        this.number = findViewById(R.id.pedalboard_number) as TextView
        this.name = findViewById(R.id.pedalboard_name) as TextView

        number.text = "--"
        name.text = "CONNECTING"

        Communicator.setOnMessageListener({ onMessage(it) })

        val button = findViewById(R.id.button) as Button
        button.setOnClickListener({ goToEffectsList() })

        if (!Data.isDataLoaded())
            showLoading()
    }

    private fun showLoading() {
        this.progress = ProgressDialog(this)
        progress.setTitle("Connecting")
        progress.setMessage("Wait plugins data")
        progress.setCancelable(false)
        progress.show()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    private fun onMessage(message : ResponseMessage) {
        if (message.request isEquivalentTo Messages.PLUGINS) {
            progress.dismiss()

        } else if (message.request isEquivalentTo Messages.CURRENT_PEDALBOARD_DATA
         || message.verb == ResponseVerb.EVENT && EventMessage(message.content).type == EventType.CURRENT) {
            val id = Data.currentPedalboard.index
            val pedalboard = Data.currentPedalboard

            runOnUiThread({
                number.text = if (id < 10) "0$id" else id.toString()
                name.text = pedalboard.name
            })
        }
    }

    private fun goToEffectsList() {
        val intent = Intent(baseContext, EffectsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

        startActivity(intent)
    }

    public override fun onResume() {
        super.onResume()

        Communicator.setOnMessageListener({ onMessage(it) })
        Communicator.send(Messages.CURRENT_PEDALBOARD_DATA)
    }
}
