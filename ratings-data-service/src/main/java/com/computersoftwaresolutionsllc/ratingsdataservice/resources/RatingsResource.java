package com.computersoftwaresolutionsllc.ratingsdataservice.resources;

import com.google.firebase.FirebaseApp;
import com.computersoftwaresolutionsllc.ratingsdataservice.models.Rating;
import com.computersoftwaresolutionsllc.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {
    private Firestore db;

    public RatingsResource(){
        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .setDatabaseUrl("https://microservicesexample-d822d.firebaseio.com/")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();

    }

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        DocumentReference docRef = db.collection("movies").document(movieId);
        ApiFuture<DocumentSnapshot> future = docRef.get();

        String rating = "";

        try {
            DocumentSnapshot doc = future.get();
            rating = doc.getString("rating");
        }catch(Exception e){
            System.out.println("err");
        }

        return new Rating(movieId, rating);
    }

    @RequestMapping("users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId) {
        DocumentReference docRef = db.collection("users").document(userId);

        ApiFuture<DocumentSnapshot> future = docRef.get();

        String favorites = "";

        List<Rating> ratings= new ArrayList<Rating>();

        try {
            DocumentSnapshot doc = future.get();
            favorites = doc.getString("movies");
        }catch(Exception e){
            System.out.println("err");
        }

        String[] movieIds = favorites.split(",");

        for(int i=0;i<movieIds.length;i++){
            String rating = getRating(movieIds[i]).getRating();
            ratings.add(new Rating(movieIds[i],rating));
        };

        UserRating userRating = new UserRating();
        userRating.setUserRating(ratings);

        return userRating;
    }
}
