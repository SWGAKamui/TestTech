package org.example.service

import org.example.dto.UserDto
import org.example.model.UserModel
import org.example.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun getAllUser(): List<UserDto> {
        val userModels: List<UserModel> = userRepository.findAllByOrderByPointsDesc()
        return userModels.mapIndexed{ index, userModel ->
            userModel.toDto(index + 1L)
        }
    }

    fun saveUser(nickname: String): UserDto {
        val userExist = userRepository.findUserByNickname(nickname)
        if (userExist != null) {
            throw Exception("User already exist")
        }
        val userModel = UserModel(
            nickname = nickname,
            points = 0
        )
        return userRepository.save(userModel).toDtoWithOrder()
    }

    fun updateUser(nickname: String, points: Int): UserDto {
        val userModel = UserModel(nickname = nickname, points = points)
        return userRepository.save(userModel).toDtoWithOrder()
    }

    private fun getClassement(user: UserModel): Long {
        val classement = userRepository.countDistinctByPointsAfter(user.points) //TODO en cas d'égalité
        return classement + 1
    }

    private fun UserModel.toDtoWithOrder(): UserDto{
        return UserDto(
            nickname = this.nickname,
            points = this.points,
            classement = getClassement(this)
        )
    }
    private fun UserModel.toDto(classement: Long): UserDto{
        return UserDto(
            nickname = this.nickname,
            points = this.points,
            classement = classement
        )
    }


}