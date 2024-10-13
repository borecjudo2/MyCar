package by.judoka.car_request_service.model

import java.util.UUID

class FollowCarData(
    var userId: UUID,
    var runStatus: String = "",
    var yearFrom: Int,
    var yearTo: Int,
    var driveType: Int,
    var engineCapacity: Int,
)
