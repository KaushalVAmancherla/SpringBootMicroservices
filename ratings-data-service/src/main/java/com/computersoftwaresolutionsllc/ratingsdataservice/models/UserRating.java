package com.computersoftwaresolutionsllc.ratingsdataservice.models;

import java.util.ArrayList;
import java.util.List;

public class UserRating {
    private List<Rating> userRating;

    public List<Rating> getUserRating() {
        return userRating;
    }

    public void setUserRating(List<Rating> userRating) {
        this.userRating = userRating;
    }
}
