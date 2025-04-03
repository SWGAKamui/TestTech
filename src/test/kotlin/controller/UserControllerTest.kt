package org.example.controller

import com.fasterxml.jackson.databind.ObjectMapper
import net.datafaker.Faker
import org.example.model.UserModel
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Testcontainers

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc
    private val faker = Faker()

    companion object {
        private val mongoContainer = MongoDBContainer("mongo:5.0.21")

        init {
            mongoContainer.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun mongoProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri", mongoContainer::getReplicaSetUrl)
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            mongoContainer.close()
        }
    }

    private val user = "Batman"

    @Test
    fun `should create a new user`() {
        val username = "Pikachu"
        mockMvc.perform(
            post("/user/$username")
                .contentType(MediaType.APPLICATION_JSON)

        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.nickname").value(username))
            .andExpect(jsonPath("$.points").value(0))
    }

    @Test
    fun `should get user by nickname`() {
        mockMvc.perform(
            post("/user/${user}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

        mockMvc.perform(
            get("/user/$user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(user))
        )
            .andExpect(status().isOk)
    }


    @Test
    fun `should get all user sorted by points`() {
        val thirdPlayer = createAnUser(10)
        val firstPlayer = createAnUser(2500)
        val secondPlayer = createAnUser(1000)

        mockMvc.perform(
            get("/user")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].points").value(firstPlayer.points))
            .andExpect(jsonPath("$[1].points").value(secondPlayer.points))
            .andExpect(jsonPath("$[2].points").value(thirdPlayer.points))
            .andExpect(jsonPath("$[2].nickname").value(thirdPlayer.nickname))
    }

    @Test
    fun `should delete all user`() {
        mockMvc.perform(
            delete("/user")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

        mockMvc.perform(
            get("/user")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isEmpty)
    }

    @Test
    fun `should update an user`() {
        val userModel = createAnUser(10)
        userModel.points = 1000
        mockMvc.perform(
            put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(userModel))
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.nickname").value(userModel.nickname))
            .andExpect(jsonPath("$.points").value(userModel.points))

    }

    private fun createAnUser(points: Int?): UserModel {
        val userModel = UserModel(
            nickname = faker.pokemon().name(),
            points = points ?: faker.number().randomDigitNotZero()
        )

        mockMvc.perform(
            put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(userModel))
        )
        return userModel
    }
}