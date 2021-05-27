package com.example.moviee.controller

import com.example.moviee.model.Movie
import com.example.moviee.service.MovieService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RequestMapping("/movies")
@RestController
class MovieController constructor(val movieService : MovieService) {

    @GetMapping
    fun getMovies() : Flux<Movie> {
        return this.movieService.findAll();
    }

    @GetMapping("/{id}")
    fun getMovie(@PathVariable id : Int) : Mono<Movie> {
        return this.movieService.findOne(id);
    }

    @PutMapping("/{id}/rate")
    fun rateMovie(@PathVariable id : Int, @RequestParam rating : Int, @RequestParam comment : String) : Mono<Movie> {
        return this.movieService.rate(id, comment, rating);
    }


}