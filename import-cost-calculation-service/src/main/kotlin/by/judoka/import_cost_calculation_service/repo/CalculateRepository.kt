package by.judoka.import_cost_calculation_service.repo

import by.judoka.import_cost_calculation_service.model.CalculatedData
import org.springframework.data.mongodb.repository.MongoRepository

interface CalculateRepository: MongoRepository<CalculatedData, Int> {
}
