package io.github.pedalpi.displayview

import com.google.gson.JsonElement
import com.google.gson.JsonParser

object Data {
    private val VOID_PLUGIN = JsonParser().parse("""
        {
            "name": "Plugin not found",
            "ports": {
                "audio": [],
                "midi": [],
                "control": {"input": [
                    {"name": "param1"},
                    {"name": "param2"},
                    {"name": "param3"},
                    {"name": "param4"},
                    {"name": "param5"},
                    {"name": "param6"},
                    {"name": "param7"},
                    {"name": "param8"},
                    {"name": "param9"},
                    {"name": "param10"},
                    {"name": "param11"},
                    {"name": "param12"},
                ]}
            }
        }
    """)

    var currentPedalboard = JsonParser().parse("""
        {
            "name": "Connecting",
            "connections": [],
            "data": {},
            "effects": [
                {"plugin": "???", "params": [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}]},
                {"plugin": "!!!"}, {"plugin": "*****"},
                {"plugin": "???"}, {"plugin": "!!!"}, {"plugin": "*****"},
                {"plugin": "???"}, {"plugin": "!!!"}, {"plugin": "*****"},
                {"plugin": "???"}, {"plugin": "!!!"}, {"plugin": "*****"},
                {"plugin": "???"}, {"plugin": "!!!"}, {"plugin": "*****"},
                {"plugin": "???"}, {"plugin": "!!!"}, {"plugin": "*****"}
            ]
        }
    """)

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
