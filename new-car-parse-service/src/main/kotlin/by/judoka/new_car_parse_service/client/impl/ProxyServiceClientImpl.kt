package by.judoka.new_car_parse_service.client.impl

import by.judoka.new_car_parse_service.client.ProxyServiceClient
import by.judoka.new_car_parse_service.model.proxy.bidcars.BidCarsDetailsForCarResponse
import by.judoka.new_car_parse_service.model.proxy.bidcars.BidCarsNewCarsResponse
import by.judoka.new_car_parse_service.model.proxy.bidcars.ProxyPostRequest
import by.judoka.new_car_parse_service.properties.ClientsProperties
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

    private val log: Logger = LoggerFactory.getLogger(ProxyServiceClient::class.java)

    override suspend fun getNewCarsFromBidCars(request: ProxyPostRequest): BidCarsNewCarsResponse {
        val url = "${clientsProperties.proxyServiceUrl}/proxy"
        log.info("Send request to: $url")

        return webClient.post()
            .uri(url)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(BidCarsNewCarsResponse::class.java)
            .awaitSingle()
    }

    override suspend fun getBidCarsDetailsForCar(vin: String): BidCarsDetailsForCarResponse {
        val url = "${clientsProperties.proxyServiceUrl}/bidcars/archived/${vin}"
        log.info("Send request to: $url")

        return webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(BidCarsDetailsForCarResponse::class.java)
            .awaitSingle()
    }
}
