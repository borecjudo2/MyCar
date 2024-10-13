package by.judoka.car_request_service.repo

import by.judoka.car_request_service.model.Car
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID

interface CarRepository: MongoRepository<Car, UUID> {
}
