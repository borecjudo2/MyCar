package by.judoka.car_request_service.service.impl

import by.judoka.car_request_service.repo.CarRepository
import by.judoka.car_request_service.model.Car
import by.judoka.car_request_service.model.FollowCarData
import by.judoka.car_request_service.repo.UserRepository
import by.judoka.car_request_service.service.CarsService
import by.judoka.car_request_service.service.UserService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CarsServiceImpl(
    val carRepository: CarRepository,
    var userService: UserService,
) : CarsService {

    override fun createCar(car: Car): Car {
        car.id
        return carRepository.save(car)
    }

    override fun getCarById(id: UUID): Car {
        return carRepository.findById(id)
            .orElseThrow {
                RuntimeException("Car with id=$id not found")
            }
    }

    override fun getAllCars(): List<Car> {
        return carRepository.findAll()
    }

    override fun followCar(id: UUID, followCarData: FollowCarData) {
        userService.getUserById(followCarData.userId)
        val existingCar = getCarById(id)

        existingCar.followData.plus(followCarData)

        carRepository.save(existingCar)
    }

    override fun getAllFollowedCarsRequest(): List<Car> {
        return carRepository.findCarsWithFollowData()
    }
}
