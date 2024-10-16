package by.judoka.car_request_service.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID
import kotlin.collections.ArrayList

@Document("car-request")
class Car(
    @Id var id: UUID = UUID.randomUUID(),

    var make: String = "",
    var model: String = "",

    var bidCarsMake: String = "",
    var bidCarsModel: String = "",

    var avByMake: String = "",
    var avByModel: String = "",

    var followData: List<FollowCarData> = ArrayList()
)
