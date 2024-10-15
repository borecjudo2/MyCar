package by.judoka.new_car_parse_service.model.proxy.bidcars

import com.fasterxml.jackson.annotation.JsonProperty

data class BidCarsDetailsForCar(
    @JsonProperty("secondary_damage")
    var secondaryDamage: String? = null,
    var price: String? = null,
    var priceNotSold: Double? = null,
    var priceNoInfo: String? = null,
)
