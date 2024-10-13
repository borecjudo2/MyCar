package by.judoka.car_request_service.service

import by.judoka.car_request_service.model.Car
import by.judoka.car_request_service.model.FollowCarData
import java.util.UUID

interface CarsService {

    fun createCar(car: Car): Car

    fun getCarById(id: UUID): Car

    fun getAllCars(): List<Car>

    fun followCar(id: UUID, followCarData: FollowCarData)
}
