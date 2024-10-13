package by.judoka.car_request_service.service.impl

import by.judoka.car_request_service.model.User
import by.judoka.car_request_service.repo.UserRepository
import by.judoka.car_request_service.service.UserService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
) : UserService {

    override fun createUser(user: User): User {
        return userRepository.save(user)
    }

    override fun getUserById(id: UUID): User {
        return userRepository.findById(id)
            .orElseThrow {
                RuntimeException("User with id=$id not found")
            }
    }
}
