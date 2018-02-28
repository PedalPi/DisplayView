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
