package com.example.joey.apiexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnGetPokemon = (Button) findViewById(R.id.btn_get_pokemon);
        final TextView txtPokemon  = (TextView) findViewById(R.id.txt_pokemon);

        btnGetPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONCallToGetPokemon(txtPokemon);
            }
        });
    }

    private void JSONCallToGetPokemon(final TextView txtPokemon) {
        // Make the API call
        Retrofit retrofit = new Retrofit.Builder().baseUrl(PokeapiEndpointInterface.URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        PokeapiEndpointInterface pAPI = retrofit.create(PokeapiEndpointInterface.class);

        Random rand = new Random();

        Call<JsonObject> call = pAPI.getRandomPokemon(rand.nextInt(151) + 1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject pokemon = response.body();

                    txtPokemon.setText(pokemon.get("forms").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString());
                } else {
                    Toast.makeText(MainActivity.this, "Unsuccessful callback",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure. " + t.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
