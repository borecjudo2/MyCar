package by.judoka.new_car_parse_service.model.request

import java.util.UUID

data class FollowCarData(
    var userId: UUID,
    var runStatus: String = "",
    var yearFrom: Int,
    var yearTo: Int,
    var driveType: Int,
    var engineCapacity: Int,
)
