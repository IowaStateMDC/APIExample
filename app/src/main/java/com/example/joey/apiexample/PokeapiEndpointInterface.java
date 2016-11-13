package com.example.joey.apiexample;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Joey on 08-Nov-16.
 */

public interface PokeapiEndpointInterface {

    String URL = "http://pokeapi.co/api/v2/";

    @GET("pokemon/{number}")
    Call<JsonObject> getRandomPokemon(@Path("number") int number);

}
