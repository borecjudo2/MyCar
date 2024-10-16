package by.judoka.new_car_parse_service.scheduler

import by.judoka.new_car_parse_service.service.ParserService
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Profile("local")
@Component
class ParseSchedulerLocal(
    private var parserService: ParserService
) {

    @Scheduled(fixedDelay = 10000000, zone = "Europe/Minsk")
    suspend fun startParseNewCars() {
        parserService.parseNewCars()
    }
}
