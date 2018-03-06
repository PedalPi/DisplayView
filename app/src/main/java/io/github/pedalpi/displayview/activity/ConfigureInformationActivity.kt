package io.github.pedalpi.displayview.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import io.github.pedalpi.displayview.R
import kotlinx.android.synthetic.main.activity_configure_helper.*


class ConfigureInformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configure_helper)

        configureHelperButtonSettings.setOnClickListener {
            startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
        }

        configureHelperButtonDocumentation.setOnClickListener {
            val uri = "https://developer.android.com/studio/debug/dev-options.html#enable"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
        }
    }
}
