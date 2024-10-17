package by.judoka.sold_cars_parse_service.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("cars")
class Car(

    @Id
    var vin: String? = null,
    var lot: String? = null,
    var tag: String? = null,
    var name: String? = null,

    var primaryDamage: String? = null,
    var secondaryDamage: String? = null,
    var startCode: String? = null,
    var odometerKm: Int = 0,

    var seller: String? = null,
    var saleDocument: String? = null,
    var saleTimestampMs: Long = 0,
    var soldBefore: Boolean = false,
    var location: String? = null,

    var priceSold: String? = null,
    var priceNoInfo: String? = null,
    var priceNotSold: String? = null,
    var buyNowPrice: String? = null,

    var estimatedMin: Int = 0,
    var estimatedMax: Int = 0,

    var specs: Specs? = null,
    var img: Img? = null,
) {

    override fun toString(): String {
        return "Car(vin=$vin, lot=$lot, tag=$tag, name=$name, primaryDamage=$primaryDamage, secondaryDamage=$secondaryDamage, startCode=$startCode, odometerKm=$odometerKm, seller=$seller, saleDocument=$saleDocument, saleTimestampMs=$saleTimestampMs, soldBefore=$soldBefore, location=$location, priceSold=$priceSold, priceNoInfo=$priceNoInfo, priceNotSold=$priceNotSold, buyNowPrice=$buyNowPrice, estimatedMin=$estimatedMin, estimatedMax=$estimatedMax, specs=$specs, img=$img)"
    }
}


