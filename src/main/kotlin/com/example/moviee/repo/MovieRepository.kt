package com.example.moviee.repo

import com.example.moviee.model.Movie
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface MovieRepository : ReactiveMongoRepository<Movie, Int>
