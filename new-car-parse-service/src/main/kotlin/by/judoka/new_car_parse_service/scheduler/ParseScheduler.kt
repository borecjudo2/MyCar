package by.judoka.new_car_parse_service.scheduler

import by.judoka.new_car_parse_service.service.ParserService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ParseScheduler(
    private var parserService: ParserService
) {

//    @Scheduled(cron = "0 */12 * * *", zone = "Europe/Minsk")
    @Scheduled(fixedDelay = 10000000, zone = "Europe/Minsk")
    suspend fun startParseNewCars() {
        parserService.parseNewCars()
    }
}
