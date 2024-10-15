package by.judoka.new_car_parse_service.model.proxy.bidcars

import com.fasterxml.jackson.annotation.JsonProperty

data class Specs(
    var transmission: Int = 0,
    @JsonProperty("drive_type")
    var driveType: Int = 0,
    @JsonProperty("engine_rendered")
    var engineRendered: String? = null
)
