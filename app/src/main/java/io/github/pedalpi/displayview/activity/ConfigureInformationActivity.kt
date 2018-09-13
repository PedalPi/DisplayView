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
