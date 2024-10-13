package by.judoka.car_request_service.service

import by.judoka.car_request_service.model.User
import java.util.UUID

interface UserService {

    fun createUser(user: User): User

    fun getUserById(id: UUID): User
}
