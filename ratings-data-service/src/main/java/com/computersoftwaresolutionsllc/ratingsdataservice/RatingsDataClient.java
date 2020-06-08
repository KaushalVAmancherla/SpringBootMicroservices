package com.computersoftwaresolutionsllc.ratingsdataservice;

import com.computersoftwaresolutionsllc.ratingsdataservice.models.Rating;
import com.computersoftwaresolutionsllc.ratingsdataservice.models.UserRating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="ratings-data-service")
public interface RatingsDataClient {

    @RequestMapping("/ratingsdata/{movieId}")
    Rating getRating(@PathVariable("movieId") String movieId);

    @RequestMapping("/ratingsdata/users/{userId}")
    UserRating getUserRating(@PathVariable("userId") String userId);
}
