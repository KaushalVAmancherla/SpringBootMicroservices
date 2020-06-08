package com.computersoftwaresolutionsllc.movieinfoservice.resources;

import com.computersoftwaresolutionsllc.movieinfoservice.MovieInfoClient;
import com.computersoftwaresolutionsllc.movieinfoservice.models.Movie;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.Document;
import java.io.*;

@RestController
@RequestMapping("/movies")

public class MovieResource{
    private Firestore db;

    public MovieResource(){
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
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
        DocumentReference docRef = db.collection("movies").document(movieId);
        ApiFuture<DocumentSnapshot> future = docRef.get();

        String movieName = "";

        try {
            DocumentSnapshot doc = future.get();
            movieName = doc.getString("name");

        }catch(Exception e){
            System.out.println("err");
        }

        return new Movie(movieId,movieName);
    }
}
