package com.computersoftwaresolutionsllc.movieinfoservice;

import com.computersoftwaresolutionsllc.movieinfoservice.models.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="movie-info-service")
public interface MovieInfoClient {
    @RequestMapping("/movies/{movieId}")
    Movie getMovieInfo(@PathVariable(name="movieId") String movieId);
}
