package by.judoka.import_cost_calculation_service.controller

import by.judoka.import_cost_calculation_service.model.CalculateRequest
import by.judoka.import_cost_calculation_service.model.CalculatedData
import by.judoka.import_cost_calculation_service.service.CalculateService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/import")
class CalculateController(
    val calculateService: CalculateService,
) {

    @PostMapping("/calculate")
    fun calculateImportPrices(@RequestBody calculateRequest: CalculateRequest): CalculatedData {
        return calculateService.calculateImportPrices(calculateRequest)
    }
}
