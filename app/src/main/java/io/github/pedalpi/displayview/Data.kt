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
                    {"name": "Parameter", "properties":[], "scalePoints":[]},
                    {"name": "Parameter", "properties":[], "scalePoints":[]},
                    {"name": "Parameter", "properties":[], "scalePoints":[]},
                    {"name": "Parameter", "properties":[], "scalePoints":[]},
                    {"name": "Parameter", "properties":[], "scalePoints":[]},
                    {"name": "Parameter", "properties":[], "scalePoints":[]},
                    {"name": "Parameter", "properties":[], "scalePoints":[]},
                    {"name": "Parameter", "properties":[], "scalePoints":[]}
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
                {"active": true, "plugin": "???", "params": [
                    {"minimum": 0, "maximum": 10, "value":0},
                    {"minimum": 0, "maximum": 10, "value":0},
                    {"minimum": 0, "maximum": 10, "value":5},
                    {"minimum": 0, "maximum": 10, "value":0},
                    {"minimum": 0, "maximum": 10, "value":0},
                    {"minimum": 0, "maximum": 10, "value":0},
                    {"minimum": 0, "maximum": 10, "value":0},
                    {"minimum": 0, "maximum": 10, "value":0}
                ]},
                {"active": true, "plugin": ""}, {"active": true, "plugin": ""},
                {"active": true, "plugin": ""}, {"active": true, "plugin": ""},
                {"active": true, "plugin": ""}, {"active": true, "plugin": ""},
                {"active": true, "plugin": ""}, {"active": true, "plugin": ""},
                {"active": true, "plugin": ""}, {"active": true, "plugin": ""},
                {"active": true, "plugin": ""}, {"active": true, "plugin": ""}
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
