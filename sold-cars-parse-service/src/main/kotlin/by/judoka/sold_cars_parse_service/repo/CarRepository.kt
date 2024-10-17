package by.judoka.sold_cars_parse_service.repo

import by.judoka.sold_cars_parse_service.model.Car
import org.springframework.data.mongodb.repository.MongoRepository

interface CarRepository : MongoRepository<Car, String> {

    fun findAllBySaleTimestampMsLessThan(saleTimestampMs: Long): List<Car>
}
