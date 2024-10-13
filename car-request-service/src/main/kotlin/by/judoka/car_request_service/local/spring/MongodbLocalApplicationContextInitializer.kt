package by.judoka.car_request_service.local.spring

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName
import java.util.Arrays

class MongodbLocalApplicationContextInitializer : ApplicationContextInitializer<GenericApplicationContext> {

    private val LOCAL_ENV = "local"

    private val mongoDBContainer = MongoDBContainer(DockerImageName.parse("mongo:latest"))

    override fun initialize(applicationContext: GenericApplicationContext) {
        Arrays.stream(applicationContext.environment.activeProfiles)
            .filter { env: String -> env == LOCAL_ENV }
            .findFirst()
            .ifPresent {
                mongoDBContainer.start()
                val host: String = mongoDBContainer.host
                val port: Int = mongoDBContainer.firstMappedPort
                val connectionString = String.format("mongodb://%s:%d", host, port)

                System.setProperty("spring.data.mongodb.uri", connectionString)
                System.setProperty("spring.data.mongodb.database", "test")
            }
    }
}
