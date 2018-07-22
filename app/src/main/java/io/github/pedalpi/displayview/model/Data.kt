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

package io.github.pedalpi.displayview.model

import com.google.gson.JsonElement
import com.google.gson.JsonParser

object Data {
    private val VOID_PLUGIN = JsonParser().parse("""
        {
            "name": "Plugin not found",
            "ports": {
                "audio": [],
                "midi": [],
                "control": {"input": []}
            }
        }
    """)

    var currentPedalboard = Pedalboard(0, JsonParser().parse("""
        {
            "name": "Connecting",
            "connections": [],
            "data": {},
            "effects": []
        }
    """))

    var bankIndex: Int = 0

    var plugins: Map<String, JsonElement>? = null

    fun plugin(uri: String): JsonElement {
        return plugins?.get(uri) ?: VOID_PLUGIN
    }

    fun isDataLoaded(): Boolean {
        return plugins != null
    }
}
