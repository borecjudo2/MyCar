package by.judoka.import_cost_calculation_service.service.impl

import by.judoka.import_cost_calculation_service.model.CalculateRequest
import by.judoka.import_cost_calculation_service.model.CalculatedData
import by.judoka.import_cost_calculation_service.repo.CalculateRepository
import by.judoka.import_cost_calculation_service.service.CalculateService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CalculateServiceImpl(
    val calculateRepository: CalculateRepository,
) : CalculateService {

    private val log = LoggerFactory.getLogger(this::class.java)

    val customPriceForEngines = mapOf(
        Pair(0,0),
        Pair(1500, 1400),
        Pair(2000, 3000),
        Pair(2500, 4100),
        Pair(3000, 4900),
    )

    override fun calculateImportPrices(calculateRequest: CalculateRequest): CalculatedData {
        val calculatedData = calculateRepository.findAll()[0]

        calculatedData.custom = customPriceForEngines[calculateRequest.engine] ?: 0

        log.info("Calculated import prices for engine size ${calculateRequest.engine}")

        return calculatedData
    }
}
