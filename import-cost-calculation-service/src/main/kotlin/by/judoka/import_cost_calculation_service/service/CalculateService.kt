package by.judoka.import_cost_calculation_service.service

import by.judoka.import_cost_calculation_service.model.CalculateRequest
import by.judoka.import_cost_calculation_service.model.CalculatedData

interface CalculateService {

    fun calculateImportPrices(calculateRequest: CalculateRequest): CalculatedData
}
