package by.judoka.new_car_parse_service.client

import by.judoka.new_car_parse_service.model.request.CarRequestServiceResponse

interface CarsRequestServiceClient {

    suspend fun getAllFollowedCarsRequest(): List<CarRequestServiceResponse>
}
