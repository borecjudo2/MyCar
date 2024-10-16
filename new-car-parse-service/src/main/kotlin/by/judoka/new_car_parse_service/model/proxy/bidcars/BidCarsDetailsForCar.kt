package by.judoka.new_car_parse_service.model.proxy.bidcars

import com.fasterxml.jackson.annotation.JsonProperty

data class BidCarsDetailsForCar(
    @JsonProperty("secondary_damage")
    var secondaryDamage: String? = null,
    var priceSold: String? = null,
    var priceNoInfo: String? = null,
    var priceNotSold: String? = null,
)
