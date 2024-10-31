package com.example.data;

import android.content.Context;
import android.util.Log;

import com.example.data.model.CharacterResponse;
import com.example.domain.model.Character;
import com.example.domain.repository.CharacterRepository;
import com.example.domain.repository.FavoriteCharacterRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterRepositoryImpl implements CharacterRepository {
    private final FavoriteCharacterRepository favoriteCharacterRepository;
    private final Context context; // Добавляем контекст

    public CharacterRepositoryImpl(Context context, FavoriteCharacterRepository favoriteCharacterRepository) {
        this.context = context;
        this.favoriteCharacterRepository = favoriteCharacterRepository;
    }

    @Override
    public void getCharactersAsync(CharacterCallback callback) {
        NetworkApi.CharacterService service = NetworkApi.getClient().create(NetworkApi.CharacterService.class);
        Call<CharacterResponse> call = service.getCharacters();

        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Character> domainCharacters = new ArrayList<>();
                    for (CharacterResponse.Character characterResponse : response.body().getResults()) {
                        domainCharacters.add(mapToDomainCharacter(characterResponse));
                    }
                    callback.onSuccess(domainCharacters);
                } else {
                    callback.onFailure(new Exception("Response unsuccessful"));
                }
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                Log.e("CharacterRepositoryImpl", "Failed to load characters: " + t.getMessage());
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void getFavoriteCharactersAsync(CharacterCallback callback) {
        favoriteCharacterRepository.getFavoriteCharactersAsync(new FavoriteCharacterRepository.CharacterCallback() {
            @Override
            public void onSuccess(List<Character> characters) {
                callback.onSuccess(characters);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public List<Character> getAllFavoriteCharacters() {
        return favoriteCharacterRepository.getAllFavoriteCharacters(); // Возвращаем список любимых персонажей
    }

    private Character mapToDomainCharacter(CharacterResponse.Character characterResponse) {
        return new Character(
                characterResponse.getId(),
                characterResponse.getName(),
                characterResponse.getSpecies(),
                characterResponse.getStatus(),
                characterResponse.getGender(),
                characterResponse.getImage()
        );
    }
}
