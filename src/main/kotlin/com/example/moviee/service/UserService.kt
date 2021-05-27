package com.example.moviee.service

import com.example.moviee.model.User
import com.example.moviee.repo.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService(val userRepository: UserRepository) {
    fun getByUsername(username: String): Flux<User> {
        return userRepository.findByUsername(username);
    }
    fun save(user : User) : Mono<User> {
        return userRepository.save(user);
    }

}