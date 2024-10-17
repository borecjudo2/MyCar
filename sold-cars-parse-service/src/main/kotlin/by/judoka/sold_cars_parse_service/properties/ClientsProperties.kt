package by.judoka.sold_cars_parse_service.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "clients")
data class ClientsProperties(
    var proxyServiceUrl: String = "",
)
