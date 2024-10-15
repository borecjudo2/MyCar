package by.judoka.car_request_service.repo

import by.judoka.car_request_service.model.Car
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.UUID

interface CarRepository: MongoRepository<Car, UUID> {

    @Query("{ 'followData': { \$exists: true, \$ne: [] } }")
    fun findCarsWithFollowData(): List<Car>
}
