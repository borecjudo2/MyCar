package by.judoka.sold_cars_parse_service.service

import by.judoka.sold_cars_parse_service.model.CarPrices

interface BidCarsService {

   suspend fun getCarPrices(vin: String): CarPrices
}
