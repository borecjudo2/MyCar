package by.judoka.sold_cars_parse_service.service.impl

import by.judoka.sold_cars_parse_service.mapper.addPrices
import by.judoka.sold_cars_parse_service.service.BidCarsService
import by.judoka.sold_cars_parse_service.service.CarService
import by.judoka.sold_cars_parse_service.service.ParserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneId

@Service
class ParserServiceImpl(
    private val carService: CarService,
    private val bidCarsService: BidCarsService
) : ParserService {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    override suspend fun startToGetFinalPriceForSoldCars() {
        log.info("Start to get final price for all sold cars less than 6h from now")

        val sixHoursAgoTimestamp = Instant.now().atZone(ZoneId.systemDefault())
            .minusHours(6)
            .toInstant()
            .toEpochMilli()

        carService.getAllSoldCarsLessThan(sixHoursAgoTimestamp).forEach { car ->
            try {
                val carPrices = bidCarsService.getCarPrices(car.vin!!)

                log.info("Prices for vin ${car.vin} $carPrices")

                car.addPrices(carPrices)
                carService.updateCar(car)
            } catch (e: Exception) {
                log.error(e.localizedMessage, e)
            }
        }

        log.info("Finish to get final price for all sold cars less than 6h from now")
    }
}
