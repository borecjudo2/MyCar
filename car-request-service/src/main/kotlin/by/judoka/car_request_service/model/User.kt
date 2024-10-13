package by.judoka.car_request_service.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document("users")
class User(
    var id: UUID,
)
