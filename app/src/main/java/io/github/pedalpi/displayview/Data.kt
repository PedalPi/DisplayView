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

    var currentPedalboardPosition: Int = 0

    lateinit var plugins: Map<String, JsonElement>

    fun plugin(uri: String): JsonElement {
        return plugins[uri] ?: VOID_PLUGIN
    }
}
