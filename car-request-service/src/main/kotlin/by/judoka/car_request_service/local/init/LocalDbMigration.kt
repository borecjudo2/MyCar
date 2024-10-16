package by.judoka.car_request_service.local.init

import by.judoka.car_request_service.model.Car
import by.judoka.car_request_service.model.FollowCarData
import by.judoka.car_request_service.model.User
import by.judoka.car_request_service.repo.CarRepository
import by.judoka.car_request_service.repo.UserRepository
import jakarta.annotation.PostConstruct
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import java.util.Collections
import java.util.UUID

private const val USER_ID = "51129080-24ad-4f62-92c6-a37018ecef21"
private const val CAR_ID = "51129080-24ad-4f62-92c6-a37018ecef70"
private const val CAR_ID_1 = "51129080-24ad-4f62-92c6-a37018ecef71"
private const val CAR_ID_2 = "51129080-24ad-4f62-92c6-a37018ecef72"

@Service
class LocalDbMigration(
    val userRepository: UserRepository,
    val carRepository: CarRepository,
    var environment: Environment,
) {

    @PostConstruct
    fun init() {
        if (environment.activeProfiles.contains("local")) {
            userRepository.save(
                User(UUID.fromString(USER_ID))
            )

            val cars = mutableListOf(
                Car(
                    id = UUID.fromString(CAR_ID),
                    make = "Volkswagen",
                    model = "Golf Gti",
                    bidCarsMake = "Volkswagen",
                    bidCarsModel = "Golf Gti",
                    avByMake = "234",
                    avByModel = "345",
                    followData = arrayListOf(
                        FollowCarData(
                            userId = UUID.fromString(USER_ID),
                            runStatus = "Run and Drive",
                            yearFrom = 2020,
                            yearTo = 2022,
                            driveType = 1,
                            engineCapacity = 2000
                        )
                    )
                ),
                Car(
                    id = UUID.fromString(CAR_ID_1),
                    make = "BMW",
                    model = "4 Series",
                    bidCarsMake = "BMW",
                    bidCarsModel = "4 Series",
                    avByMake = "234",
                    avByModel = "345",
                    followData = arrayListOf(
                        FollowCarData(
                            userId = UUID.fromString(USER_ID),
                            runStatus = "Run and Drive",
                            yearFrom = 2020,
                            yearTo = 2022,
                            driveType = 2,
                            engineCapacity = 2000
                        )
                    )
                ),

                Car(
                    id = UUID.fromString(CAR_ID_2),
                    make = "BMW",
                    model = "3 Series",
                    bidCarsMake = "BMW",
                    bidCarsModel = "3 Series",
                    avByMake = "234",
                    avByModel = "345",
                    followData = arrayListOf()
                )
            )

            carRepository.saveAll(cars)
        }

    }
}
