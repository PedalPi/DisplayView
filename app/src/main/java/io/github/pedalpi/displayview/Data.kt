package io.github.pedalpi.displayview

import com.google.gson.JsonElement
import com.google.gson.JsonParser

object Data {
    private val VOID_PLUGIN: JsonElement = JsonParser().parse("""
        {
            "label": "Plugin not found",
            "ports": {
                "audio": [],
                "midi": [],
                "control": []
            }
        }
    """)

    lateinit var currentPedalboard: JsonElement

    var pedalboardIndex: Int = 0
    var bankIndex: Int = 0

    var plugins: Map<String, JsonElement>? = null

    fun plugin(uri: String): JsonElement {
        return plugins?.get(uri) ?: VOID_PLUGIN
    }

    fun isDataLoaded(): Boolean {
        return plugins != null
    }
}
