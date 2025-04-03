package org.example.controller

import org.example.dto.UserDto
import org.example.model.UserModel
import org.example.repository.UserRepository
import org.example.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(private val userRepository: UserRepository, private val userService: UserService) {
    @PostMapping("/user/{nickname}")
    fun user(@PathVariable nickname: String): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.saveUser(nickname))
    }

    @GetMapping("/user/{nickname}")
    fun getUserByNickname(@PathVariable nickname: String): ResponseEntity<out Any>? {
        val user = userRepository.findUserByNickname(nickname)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.status(404).body(mapOf("error" to "User not found"))
        }
    }

    @GetMapping("/user")
    fun getAllUser(): ResponseEntity<out Any>? {
        val user = userService.getAllUser()
        return ResponseEntity.ok(user)
    }

    @DeleteMapping("/user")
    fun deleteAllUser(): ResponseEntity<Unit> {
        userRepository.deleteAll()
        return ResponseEntity.ok().build()
    }

    @PutMapping("/user")
    fun updateUser(@RequestBody user: UserModel): Any {
        // TODO que faire si le joueur n'existe pas ?
        return userService.updateUser(nickname = user.nickname, points = user.points)
    }
}