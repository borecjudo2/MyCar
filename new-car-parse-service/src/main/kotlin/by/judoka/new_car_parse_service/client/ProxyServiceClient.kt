package by.judoka.new_car_parse_service.client

import by.judoka.new_car_parse_service.model.proxy.bidcars.BidCarsDetailsForCarResponse
import by.judoka.new_car_parse_service.model.proxy.bidcars.BidCarsNewCarsResponse
import by.judoka.new_car_parse_service.model.proxy.bidcars.ProxyPostRequest

interface ProxyServiceClient {

    suspend fun getNewCarsFromBidCars(request: ProxyPostRequest): BidCarsNewCarsResponse

    suspend fun getBidCarsDetailsForCar(vin: String): BidCarsDetailsForCarResponse
}
