package io.github.pedalpi.displayview.communication.message.response

import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonElement


enum class EventType(private val type : String) {
    CURRENT("CURRENT"),
    BANK("BANK"),
    PEDALBOARD("PEDALBOARD"),
    EFFECT("EFFECT"),
    EFFECT_TOGGLE("EFFECT-TOGGLE"),
    PARAM("PARAM"),
    CONNECTION("CONNECTION");

    companion object {
        @JvmStatic fun valueOfGeneral(type: String) = EventType.valueOf(type.replace('-', '_'))
    }
}

enum class UpdateType(private val type : String) {
    CREATED("CREATED"),
    UPDATED("UPDATED"),
    DELETED("DELETED"),

    NULL("NULL")
}

class EventMessage(val type : EventType, val content : JsonElement) {

    val updateType : UpdateType?
        get() = UpdateType.valueOf(content["updateType"].string )

    constructor(content: JsonElement) : this(EventType.valueOfGeneral(content["type"].string), content)
}
