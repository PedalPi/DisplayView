package io.github.pedalpi.displayview.resume

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.GridView
import android.widget.TextView
import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.get
import io.github.pedalpi.displayview.Data
import io.github.pedalpi.displayview.R
import io.github.pedalpi.displayview.resume.effectview.EffectsView
import io.github.pedalpi.displayview.resume.paramview.ParamsView
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class ResumeActivity : AppCompatActivity() {
    private lateinit var number: TextView
    private lateinit var name: TextView

    private lateinit var effectsView: EffectsView
    private lateinit var paramsView: ParamsView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        supportActionBar?.hide()

        this.number = findViewById(R.id.resumePedalboardNumber) as TextView
        this.name = findViewById(R.id.resumePedalboardName) as TextView

        this.effectsView = EffectsView(this.applicationContext, findViewById(R.id.resumePedalboardEffects) as GridView)
        effectsView.update(Data.currentPedalboard)

        this.paramsView = ParamsView(this.applicationContext, findViewById(R.id.resumeEffectParams) as GridView)

        if (Data.currentPedalboard["effects"].array.size() > 0)
            paramsView.update(Data.currentPedalboard["effects"][0])
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}