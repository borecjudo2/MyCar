package by.judoka.sold_cars_parse_service.service

import by.judoka.sold_cars_parse_service.model.Car

interface CarService {

    fun getAllSoldCarsLessThan(timestamp: Long) : List<Car>

    fun updateCar(car: Car)

}
