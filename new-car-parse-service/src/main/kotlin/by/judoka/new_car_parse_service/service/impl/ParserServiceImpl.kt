package by.judoka.new_car_parse_service.service.impl

import by.judoka.new_car_parse_service.client.CarsRequestServiceClient
import by.judoka.new_car_parse_service.service.BidCarsService
import by.judoka.new_car_parse_service.service.ParserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ParserServiceImpl(
    private val carsRequestServiceClient: CarsRequestServiceClient,
    private val bidCarsService: BidCarsService,
) : ParserService {

    private val log: Logger = LoggerFactory.getLogger(ParserService::class.java)


    override suspend fun parseNewCars() {
        log.info("Started to get new cars")

        carsRequestServiceClient.getAllFollowedCarsRequest().forEach { request ->
            try {
                bidCarsService.getNewCarsFromBidCars(request).forEach { car ->
                    bidCarsService.saveCarToDb(car)
                }
            } catch (exception: Exception) {
                log.error(exception.localizedMessage, exception.printStackTrace())
            }
        }

        log.info("Finished to get new cars")
    }
}
