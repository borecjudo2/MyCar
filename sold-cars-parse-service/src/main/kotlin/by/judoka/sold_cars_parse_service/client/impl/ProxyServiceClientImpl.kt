package by.judoka.sold_cars_parse_service.client.impl

import by.judoka.sold_cars_parse_service.client.ProxyServiceClient
import by.judoka.sold_cars_parse_service.model.CarPricesResponse
import by.judoka.sold_cars_parse_service.properties.ClientsProperties
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class ProxyServiceClientImpl(
    private val webClient: WebClient,
    private val clientsProperties: ClientsProperties,
) : ProxyServiceClient {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    override suspend fun getBidCarsDetailsForCar(vin: String): CarPricesResponse {
        val url = "${clientsProperties.proxyServiceUrl}/bidcars/archived/${vin}"
        log.info("Send request to: $url")

        return webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(CarPricesResponse::class.java)
            .awaitSingle()
    }
}
