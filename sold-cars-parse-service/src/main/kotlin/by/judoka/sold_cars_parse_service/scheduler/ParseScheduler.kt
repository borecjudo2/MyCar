package by.judoka.sold_cars_parse_service.scheduler

import by.judoka.sold_cars_parse_service.service.ParserService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ParseScheduler(
    private var parserService: ParserService
) {

    //    @Scheduled(fixedDelay = 10000000, zone = "Europe/Minsk")
    @Scheduled(cron = "0 0 0/12 * * ?", zone = "Europe/Minsk")
    suspend fun startToGetFinalPriceForSoldCars() {
        parserService.startToGetFinalPriceForSoldCars()
    }
}
