package by.judoka.sold_cars_parse_service.mapper

import by.judoka.sold_cars_parse_service.model.Car
import by.judoka.sold_cars_parse_service.model.CarPrices

fun Car.addPrices(prices: CarPrices) {
    priceSold = prices.priceSold
    priceNoInfo = prices.priceNoInfo
    priceNotSold = prices.priceNotSold
}
