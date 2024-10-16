package by.judoka.new_car_parse_service.client.impl

import by.judoka.new_car_parse_service.client.CarsRequestServiceClient
import by.judoka.new_car_parse_service.model.request.CarRequestServiceResponse
import by.judoka.new_car_parse_service.properties.ClientsProperties
import kotlinx.coroutines.reactive.awaitSingle
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class CarsRequestServiceClientImpl(
    private val webClient: WebClient,
    private val clientsProperties: ClientsProperties
) : CarsRequestServiceClient {

    private val log: Logger = LoggerFactory.getLogger(CarsRequestServiceClient::class.java)


    override suspend fun getAllFollowedCarsRequest(): List<CarRequestServiceResponse> {
        val url = "${clientsProperties.carRequestServiceUrl}/cars/followed"
        log.info("Send request to: $url")

        return webClient.get()
            .uri(url)
            .retrieve()
            .bodyToFlux(CarRequestServiceResponse::class.java)
            .collectList()
            .awaitSingle()
    }
}
