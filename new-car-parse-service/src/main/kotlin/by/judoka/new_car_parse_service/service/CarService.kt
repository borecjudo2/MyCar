package by.judoka.new_car_parse_service.service

import by.judoka.new_car_parse_service.model.dao.Car

interface CarService {

    fun saveCars(cars: List<Car>) : List<Car>

    fun saveCar(car: Car) : Car
}
