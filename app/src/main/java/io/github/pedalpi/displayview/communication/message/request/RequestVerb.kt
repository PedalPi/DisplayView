package io.github.pedalpi.pedalpi_display.communication.message.request


enum class RequestVerb(private val type: String) {
    SYSTEM("SYSTEM"),

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    override fun toString(): String {
        return type
    }
}
