package by.judoka.import_cost_calculation_service.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("prices")
class CalculatedData(
    @Id
    var auctionFee: Int = 1500,

    var importToGeorgia: Int = 2500,
    var portForwarding: Int = 400,
    var importToMinsk: Int = 3000,

    var custom: Int = 0,
    var customFee: Int = 150,
    var utilizationFee: Int = 250,

    var beneficiary: Int = 500,
    var picker: Int = 500,
)
