package by.judoka.new_car_parse_service.client.impl

import by.judoka.new_car_parse_service.client.CarsRequestServiceClient
import by.judoka.new_car_parse_service.model.request.CarRequestServiceResponse
import by.judoka.new_car_parse_service.properties.ClientsProperties
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class CarsRequestServiceClientImpl(
    private val webClient: WebClient,
    private val clientsProperties: ClientsProperties
) : CarsRequestServiceClient {

    override suspend fun getAllFollowedCarsRequest(): List<CarRequestServiceResponse> {
        return webClient.get()
            .uri("${clientsProperties.carRequestServiceUrl}/cars/followed")
            .retrieve()
            .bodyToFlux(CarRequestServiceResponse::class.java)
            .collectList()
            .awaitSingle()
    }
}
