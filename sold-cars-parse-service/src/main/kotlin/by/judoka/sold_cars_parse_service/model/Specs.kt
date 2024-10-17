package by.judoka.sold_cars_parse_service.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Specs(
    var transmission: Int = 0,
    @JsonProperty("drive_type")
    var driveType: Int = 0,
    @JsonProperty("engine_rendered")
    var engineRendered: String? = null
)
