package by.judoka.sold_cars_parse_service.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Img(
    @JsonProperty("img_1")
    var img1: String? = null,
    @JsonProperty("img_2")
    var img2: String? = null,
    @JsonProperty("img_3")
    var img3: String? = null,
    @JsonProperty("img_4")
    var img4: String? = null,
    @JsonProperty("img_5")
    var img5: String? = null
)
