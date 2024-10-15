package by.judoka.car_request_service.controller

import by.judoka.car_request_service.model.Car
import by.judoka.car_request_service.model.FollowCarData
import by.judoka.car_request_service.service.CarsService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/cars")
class CarsController(
    val carsService: CarsService
) {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody baseCar: Car): Car {
        return carsService.createCar(baseCar)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getCarById(@PathVariable id: UUID): Car {
        return carsService.getCarById(id)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllCars(): List<Car> {
        return carsService.getAllCars()
    }

    @GetMapping("/followed")
    @ResponseStatus(HttpStatus.OK)
    fun getAllFollowedCarsRequest(): List<Car> {
        return carsService.getAllFollowedCarsRequest()
    }

    @PostMapping("/{id}/follow")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun followCar(@PathVariable id: UUID, @RequestBody followCarData: FollowCarData) {
        carsService.followCar(id, followCarData)
    }
}
