package io.github.pedalpi.displayview.communication.base.message


enum class RequestVerb(private val type: String) {
    SYSTEM("SYSTEM"),

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),

    NIL("NIL");

    override fun toString(): String {
        return type
    }
}
