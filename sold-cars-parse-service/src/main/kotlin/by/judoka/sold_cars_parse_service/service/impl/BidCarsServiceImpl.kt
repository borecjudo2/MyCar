package by.judoka.sold_cars_parse_service.service.impl

import by.judoka.sold_cars_parse_service.client.ProxyServiceClient
import by.judoka.sold_cars_parse_service.model.CarPrices
import by.judoka.sold_cars_parse_service.service.BidCarsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BidCarsServiceImpl(
    private val proxyServiceClient: ProxyServiceClient,
) : BidCarsService {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    override suspend fun getCarPrices(vin: String): CarPrices {
        val result = proxyServiceClient.getBidCarsDetailsForCar(vin)
        val carPrices = result.data!!

        log.info("Got car prices for vin $vin")

        return carPrices
    }
}
