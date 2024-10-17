package by.judoka.sold_cars_parse_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SoldCarsParseServiceApplication

fun main(args: Array<String>) {
	runApplication<SoldCarsParseServiceApplication>(*args)
}
