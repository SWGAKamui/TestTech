package org.example.repository

import org.example.model.UserModel

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<UserModel, String> {
    fun findUserByNickname(nickname: String): UserModel?
    fun save(userModel: UserModel): UserModel
    fun countDistinctByPointsAfter(pointsAfter: Int): Long
    fun findAllByOrderByPointsDesc(): List<UserModel>
}
