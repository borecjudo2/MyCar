package by.judoka.new_car_parse_service.client.impl

import by.judoka.new_car_parse_service.client.ProxyServiceClient
import by.judoka.new_car_parse_service.model.proxy.bidcars.BidCarsDetailsForCarResponse
import by.judoka.new_car_parse_service.model.proxy.bidcars.BidCarsNewCarsResponse
import by.judoka.new_car_parse_service.model.proxy.bidcars.ProxyPostRequest
import by.judoka.new_car_parse_service.properties.ClientsProperties
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class ProxyServiceClientImpl(
    private val webClient: WebClient,
    private val clientsProperties: ClientsProperties,
) : ProxyServiceClient {

    override suspend fun getNewCarsFromBidCars(request: ProxyPostRequest): BidCarsNewCarsResponse {
        return webClient.post()
            .uri("${clientsProperties.proxyServiceUrl}/proxy")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(BidCarsNewCarsResponse::class.java)
            .awaitSingle()
    }

    override suspend fun getBidCarsDetailsForCar(vin: String): BidCarsDetailsForCarResponse {
        return webClient.get()
            .uri("${clientsProperties.proxyServiceUrl}/bidcars/archived/${vin}")
            .retrieve()
            .bodyToMono(BidCarsDetailsForCarResponse::class.java)
            .awaitSingle()
    }
}
