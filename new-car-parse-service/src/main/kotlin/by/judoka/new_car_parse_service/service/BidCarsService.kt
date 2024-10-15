package by.judoka.new_car_parse_service.service

import by.judoka.new_car_parse_service.model.proxy.bidcars.BidCarsCar
import by.judoka.new_car_parse_service.model.request.CarRequestServiceResponse

interface BidCarsService {

    suspend fun getNewCarsFromBidCars(carRequest: CarRequestServiceResponse) : List<BidCarsCar>
}
