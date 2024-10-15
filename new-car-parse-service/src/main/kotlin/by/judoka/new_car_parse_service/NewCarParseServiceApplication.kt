package by.judoka.new_car_parse_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class NewCarParseServiceApplication

fun main(args: Array<String>) {
	runApplication<NewCarParseServiceApplication>(*args)
}
