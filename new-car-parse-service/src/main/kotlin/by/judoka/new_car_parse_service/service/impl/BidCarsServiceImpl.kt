package by.judoka.new_car_parse_service.service.impl

import by.judoka.new_car_parse_service.client.ProxyServiceClient
import by.judoka.new_car_parse_service.mapper.mapToCar
import by.judoka.new_car_parse_service.model.dao.Car
import by.judoka.new_car_parse_service.model.proxy.bidcars.BidCarsCar
import by.judoka.new_car_parse_service.model.proxy.bidcars.ProxyPostRequest
import by.judoka.new_car_parse_service.model.request.CarRequestServiceResponse
import by.judoka.new_car_parse_service.service.BidCarsService
import by.judoka.new_car_parse_service.service.CarService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.streams.asSequence

private val BID_CARS_NEW_CARS_URL = "https://bid.cars/app/search/request?" +
        "search-type=filters&type=Automobile&" +
        "make=%s&model=%s&" +
        "year-from=%s&year-to=%s&" +
        "odometer-from=%s&odometer-to=%s&" +
        "auction-type=All&start-code=All&drive-type=All&transmission=All&" +
        "order-by=date&hide-finished=true&" +
        "page=%s"

@Service
class BidCarsServiceImpl(
    private val proxyServiceClient: ProxyServiceClient,
    private val carService: CarService,
) : BidCarsService {

    private val log: Logger = LoggerFactory.getLogger(BidCarsServiceImpl::class.java)

    override suspend fun getNewCarsFromBidCars(carRequest: CarRequestServiceResponse): List<BidCarsCar> {
        val newCars = mutableListOf<BidCarsCar>()

        for (i in 1..16) {
            val url = prepareUrl(carRequest, i)
            val newCarsFromBidCars = proxyServiceClient.getNewCarsFromBidCars(ProxyPostRequest(url))

            if (newCarsFromBidCars.data.size == 0) {
                break
            }

            newCars.addAll(newCarsFromBidCars.data)
        }

        newCars.forEach {
            val bidCarsDetailsForCar = proxyServiceClient.getBidCarsDetailsForCar(it.vin!!)
            it.secondaryDamage = bidCarsDetailsForCar.data!!.secondaryDamage
        }

        return newCars
    }

    override fun saveCarToDb(car: BidCarsCar) {
        try {
            val carToSave = car.mapToCar()
            val saveCar: Car = carService.saveCar(carToSave)
            log.info(saveCar.toString())
        } catch (exception: Exception) {
            log.error(exception.localizedMessage, exception.printStackTrace())
        }
    }

    private fun prepareUrl(carRequest: CarRequestServiceResponse, pageNumber: Int): String {
        val minYearFrom: Int = carRequest.followData.stream()
            .map { it.yearFrom }
            .asSequence()
            .min()

        val maxYearTo: Int = carRequest.followData.stream()
            .map { it.yearTo }
            .asSequence()
            .max()

        return String.format(
            BID_CARS_NEW_CARS_URL,
            carRequest.bidCarsMake, carRequest.bidCarsModel,
            minYearFrom, maxYearTo,
            0, 75000,
            pageNumber
        )
    }
}
