package by.judoka.sold_cars_parse_service.service.impl

import by.judoka.sold_cars_parse_service.model.Car
import by.judoka.sold_cars_parse_service.repo.CarRepository
import by.judoka.sold_cars_parse_service.service.CarService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CarServiceImpl(
    private val carRepository: CarRepository,
) : CarService {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun getAllSoldCarsLessThan(timestamp: Long): List<Car> {
        val cars = carRepository.findAllBySaleTimestampMsLessThan(timestamp)
        log.info("Got all sold cars less that time in ms $timestamp")
        return cars
    }

    override fun updateCar(car: Car) {
        carRepository.save(car)
        log.info("Updated car with vin ${car.vin}")
    }
}
