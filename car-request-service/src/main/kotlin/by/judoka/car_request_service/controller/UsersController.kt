package by.judoka.car_request_service.controller

import by.judoka.car_request_service.model.Car
import by.judoka.car_request_service.model.FollowCarData
import by.judoka.car_request_service.model.User
import by.judoka.car_request_service.service.CarsService
import by.judoka.car_request_service.service.UserService
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
@RequestMapping("/users")
class UsersController(
    val userService: UserService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody user: User): User {
        return userService.createUser(user)
    }
}
