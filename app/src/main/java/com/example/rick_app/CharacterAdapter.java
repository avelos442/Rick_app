package com.example.rick_app;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domain.model.Character;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private List<Character> characters;
    private CharacterViewModel characterViewModel;
    private static final String TAG = "CharacterAdapter";

    public CharacterAdapter(List<Character> characters, CharacterViewModel characterViewModel) {
        this.characters = characters;
        this.characterViewModel = characterViewModel;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Character character = characters.get(position);
        holder.characterName.setText(character.getName());
        holder.characterInfo.setText("Species: " + character.getSpecies() + ", Status: " + character.getStatus());
        Log.d(TAG, "Loading image from URL: " + character.getImage());
        Picasso.get().load(character.getImage()).into(holder.characterImage);

        characterViewModel.isFavorite(character).observe((LifecycleOwner) holder.itemView.getContext(), isFavorite -> {
            if (isFavorite) {
                holder.favoriteButton.setImageResource(R.drawable.ic_favorite_filled);
                Log.d(TAG, "Character is favorite: " + character.getName());
            } else {
                holder.favoriteButton.setImageResource(R.drawable.ic_favorite);
                Log.d(TAG, "Character is not favorite: " + character.getName());
            }
        });

        holder.favoriteButton.setOnClickListener(v -> {
            Log.d(TAG, "Favorite button clicked for character: " + character.getName());
            characterViewModel.isFavorite(character).observe((LifecycleOwner) holder.itemView.getContext(), isFavorite -> {
                if (isFavorite) {
                    Log.d(TAG, "Removing character from favorites: " + character.getName());
                    characterViewModel.deleteFavoriteCharacter(character.getId());
                } else {
                    Log.d(TAG, "Adding character to favorites: " + character.getName());
                    characterViewModel.addFavoriteCharacter(character);
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    // Метод для обновления данных адаптера
    public void updateData(List<Character> newCharacters) {
        this.characters = newCharacters;
        notifyDataSetChanged(); // Уведомляем адаптер о необходимости перерисовки
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
        ImageView characterImage;
        TextView characterName;
        TextView characterInfo;
        ImageButton favoriteButton;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            characterImage = itemView.findViewById(R.id.character_image);
            characterName = itemView.findViewById(R.id.character_name);
            characterInfo = itemView.findViewById(R.id.character_info);
            favoriteButton = itemView.findViewById(R.id.favorite_button);
        }
    }
}
