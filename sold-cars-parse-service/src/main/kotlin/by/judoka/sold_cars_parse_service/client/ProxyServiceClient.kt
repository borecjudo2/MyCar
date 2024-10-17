package by.judoka.sold_cars_parse_service.client

import by.judoka.sold_cars_parse_service.model.CarPricesResponse

interface ProxyServiceClient {

    suspend fun getBidCarsDetailsForCar(vin: String): CarPricesResponse
}
