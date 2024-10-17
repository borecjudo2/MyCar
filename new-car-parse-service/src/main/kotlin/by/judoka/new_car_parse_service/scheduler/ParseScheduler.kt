package by.judoka.new_car_parse_service.scheduler

import by.judoka.new_car_parse_service.service.ParserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ParseScheduler(
    private var parserService: ParserService
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    //    @Scheduled(fixedDelay = 10000000, zone = "Europe/Minsk")
    @Scheduled(cron = "0 0 0/12 * * ?", zone = "Europe/Minsk")
    suspend fun startParseNewCars() {
        try {
            parserService.parseNewCars()
        } catch (e: Exception) {
            log.error(e.localizedMessage, e)
        }
    }
}
