package service

import org.example.dto.UserDto
import org.example.model.UserModel
import org.example.repository.UserRepository
import org.example.service.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class UserServiceTest {
    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var userService: UserService


    @Test
    fun `should save user and return UserDto`() {
        val userModel = UserModel( nickname = "User1", points = 0)
        val userDto = UserDto(nickname = "User1", points = 0, classement = 1)

        Mockito.`when`(userRepository.save(userModel)).thenReturn(userModel)
        Mockito.`when`(userRepository.findUserByNickname("User1")).thenReturn(null)
        val result = userService.saveUser(userModel.nickname)
        assertEquals(userDto, result)
    }

    @Test
    fun `should return all users as UserDto list`() {
        val userModel1 = UserModel(nickname = "User1", points = 10)
        val userModel2 = UserModel(nickname = "User2", points = 20)
        val userDto1 = UserDto(
            nickname = "User1", points = 10,
            classement = 2
        )
        val userDto2 = UserDto(
            nickname = "User2", points = 20,
            classement = 1
        )

        Mockito.`when`(userRepository.findAll()).thenReturn(listOf(userModel1, userModel2))
        Mockito.`when`(userRepository.countDistinctByPointsAfter(userDto1.points)).thenReturn(userDto1.classement - 1)
        Mockito.`when`(userRepository.countDistinctByPointsAfter(userDto2.points)).thenReturn(userDto2.classement - 1)

        val result = userService.getAllUser()

        assertEquals(listOf(userDto2, userDto1), result)
    }
}