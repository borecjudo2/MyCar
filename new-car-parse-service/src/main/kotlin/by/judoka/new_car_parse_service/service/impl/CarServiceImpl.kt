package by.judoka.new_car_parse_service.service.impl

import by.judoka.new_car_parse_service.model.dao.Car
import by.judoka.new_car_parse_service.repo.CarRepository
import by.judoka.new_car_parse_service.service.CarService
import by.judoka.new_car_parse_service.service.ParserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CarServiceImpl(
    private val carRepository: CarRepository,
) : CarService {

    private val log: Logger = LoggerFactory.getLogger(ParserService::class.java)

    override fun saveCars(cars: List<Car>): List<Car> {
        val savedCars = carRepository.saveAll(cars)
        log.info("CarService saved cars to db")
        return savedCars
    }

    override fun saveCar(car: Car): Car {
        val savedCar = carRepository.save(car)
        log.info("CarService saved car to db")
        return savedCar
    }
}
