package by.judoka.new_car_parse_service.model.request

import java.util.UUID
import kotlin.collections.ArrayList

data class CarRequestServiceResponse(
    var id: UUID,

    var make: String,
    var model: String,

    var bidCarsMake: String,
    var bidCarsModel: String,

    var avByMake: String,
    var avByModel: String,

    var followData: ArrayList<FollowCarData>,
)
