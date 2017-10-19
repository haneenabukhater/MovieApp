package com.epicodus.movieapp;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Guest on 10/18/17.
 */

public class MovieService {
    public static void findMovies(String search, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.API_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.API_QUERY_PARAMETER, search);
        urlBuilder.addQueryParameter(Constants.API_KEY_PARAMETER, Constants.API_KEY);

        String url = urlBuilder.build().toString();

        Log.d("URL", url);

        Request request = new Request.Builder()
                        .url(url)
                        .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    public ArrayList<Movie> processResults(String jsonData){
        ArrayList<Movie> movies = new ArrayList<>();
        try{
            JSONObject movieJSON = new JSONObject(jsonData);
            JSONArray resultsJSON = movieJSON.getJSONArray("results");
            for (int i =0; i < resultsJSON.length(); i++){
                JSONObject parseableJSON = resultsJSON.getJSONObject(i);

                String poster = parseableJSON.getString("poster_path");
                String title = parseableJSON.getString("title");
                String synopsis = parseableJSON.getString("overview");
                Double rating = parseableJSON.getDouble("vote_average");
                String release = parseableJSON.getString("release_date");

                Movie movie = new Movie(poster, title, synopsis, release, rating);
                movies.add(movie);

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

}
