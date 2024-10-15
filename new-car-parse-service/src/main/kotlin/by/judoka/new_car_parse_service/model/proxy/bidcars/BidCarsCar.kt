package by.judoka.new_car_parse_service.model.proxy.bidcars

import com.fasterxml.jackson.annotation.JsonProperty

data class BidCarsCar(
    var vin: String? = null,
    var name: String? = null,
    @JsonProperty("name_long")
    var nameLong: String? = null,
    var tag: String? = null,
    @JsonProperty("primary_damage")
    var primaryDamage: String? = null,
    @JsonProperty("secondary_damage")
    var secondaryDamage: String? = null,
    @JsonProperty("start_code")
    var startCode: String? = null,
    @JsonProperty("prebid_close_time")
    var prebidCloseTime: String? = null,
    var seller: String? = null,
    @JsonProperty("seller_long")
    var sellerLong: String? = null,
    @JsonProperty("seller_trusted")
    var sellerTrusted: Int = 0,
    var lot: String? = null,
    @JsonProperty("sold_before")
    var soldBefore: Int = 0,
    @JsonProperty("odometer_km_substr")
    var odometerKmSubstr: Int = 0,
    var location: String? = null,
    @JsonProperty("sale_document")
    var saleDocument: String? = null,
    @JsonProperty("prebid_price")
    var prebidPrice: String? = null,
    @JsonProperty("final_bid")
    var finalBid: Any? = null,
    @JsonProperty("buy_now_price")
    var buyNowPrice: Any? = null,
    @JsonProperty("estimated_min")
    var estimatedMin: Int = 0,
    @JsonProperty("estimated_max")
    var estimatedMax: Int = 0,
    var specs: Specs? = null,
    var img: Img? = null,
    @JsonProperty("time_left")
    var timeLeft: String? = null,
)
