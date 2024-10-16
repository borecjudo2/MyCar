package by.judoka.new_car_parse_service.repo

import by.judoka.new_car_parse_service.model.dao.Car
import org.springframework.data.mongodb.repository.MongoRepository

interface CarRepository: MongoRepository<Car, String> {

}
