package by.judoka.new_car_parse_service.mapper

import by.judoka.new_car_parse_service.model.dao.Car
import by.judoka.new_car_parse_service.model.proxy.bidcars.BidCarsCar
import java.time.Instant
import java.time.ZoneId

fun BidCarsCar.mapToCar() : Car {
    val currentTimestamp = Instant.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val saleTimestampMs = timeLeft!!.toLong() * 1000L + currentTimestamp

    return Car(
        vin = vin,
        lot = lot,
        tag = tag,
        name = nameLong,

        primaryDamage = primaryDamage,
        secondaryDamage = secondaryDamage,
        startCode = startCode,
        odometerKm = odometerKmSubstr,

        seller = sellerLong,
        saleDocument = saleDocument,
        saleTimestampMs = saleTimestampMs,
        soldBefore = soldBefore > 0,
        location = location,

        priceSold =  finalBid,
        buyNowPrice = buyNowPrice,

        estimatedMin = estimatedMin,
        estimatedMax = estimatedMax,

        specs = specs,
        img = img
    )
}
