package by.judoka.new_car_parse_service.service.impl

import by.judoka.new_car_parse_service.client.CarsRequestServiceClient
import by.judoka.new_car_parse_service.service.BidCarsService
import by.judoka.new_car_parse_service.service.ParserService
import org.springframework.stereotype.Service

@Service
class ParserServiceImpl(
    private val carsRequestServiceClient: CarsRequestServiceClient,
    private val bidCarsService: BidCarsService,
) : ParserService {

    override suspend fun parseNewCars() {
        carsRequestServiceClient.getAllFollowedCarsRequest().forEach {
            try {
                val newCarsFromBidCars = bidCarsService.getNewCarsFromBidCars(it)
                // TODO: impl

            }catch (exception: Exception) {
               // TODO: impl
            }

        }
    }
}
