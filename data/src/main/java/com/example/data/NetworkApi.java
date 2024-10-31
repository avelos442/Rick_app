package com.example.data;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import com.example.data.model.CharacterResponse;

public class NetworkApi {
    private static final String BASE_URL = "https://rickandmortyapi.com/api/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public interface CharacterService {
        @GET("character") // Убедитесь, что URL соответствует вашему API
        Call<CharacterResponse> getCharacters(); // Должен возвращать Call<CharacterResponse>
    }
}
