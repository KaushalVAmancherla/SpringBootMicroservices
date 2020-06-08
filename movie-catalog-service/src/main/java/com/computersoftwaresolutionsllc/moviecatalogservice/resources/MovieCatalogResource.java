package com.computersoftwaresolutionsllc.moviecatalogservice.resources;

import com.computersoftwaresolutionsllc.moviecatalogservice.models.CatalogItem;
import com.computersoftwaresolutionsllc.moviecatalogservice.models.Movie;
import com.computersoftwaresolutionsllc.moviecatalogservice.models.Rating;

import com.computersoftwaresolutionsllc.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;
    private Firestore db;

    public MovieCatalogResource(){
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

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);


        return ratings.getUserRating().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

            DocumentReference docRef = db.collection("movies").document(rating.getMovieId());
            ApiFuture<DocumentSnapshot> future = docRef.get();

            String description = "";
            try {
                DocumentSnapshot doc = future.get();
                description = doc.getString("desc");

                //System.out.println(doc.getString("name"));
            }catch(Exception e){
                System.out.println("err");
            }

            return new CatalogItem(movie.getName(),description,rating.getRating());

        }).collect(Collectors.toList());
    }
}
