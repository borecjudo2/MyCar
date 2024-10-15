package by.judoka.new_car_parse_service.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "clients")
data class ClientsProperties(
    var carRequestServiceUrl: String = "",
    var proxyServiceUrl: String = "",
)
