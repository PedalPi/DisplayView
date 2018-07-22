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

package io.github.pedalpi.displayview.communication.adb.message

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
