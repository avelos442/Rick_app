package com.example.rick_app;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.data.FirebaseAuthRepository;
import com.example.domain.repository.AuthRepository;

public class AuthActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        AuthRepository authRepository = new FirebaseAuthRepository();
        authViewModel = new AuthViewModel(authRepository);

        // Наблюдение за состоянием успешного логина
        authViewModel.getLoginSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                Toast.makeText(AuthActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                saveUserData();
                startActivity(new Intent(AuthActivity.this, ProfileActivity.class));
                finish();
            }
        });

        // Наблюдение за сообщением об ошибке
        authViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(AuthActivity.this, "Login failed: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // Логика для авторизации
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Проверка на пустые поля
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(AuthActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Выполнение логина
            authViewModel.login(email, password);
        });

        // Логика для перехода на страницу регистрации
        registerButton.setOnClickListener(v -> {
            // Открытие страницы регистрации
            Intent intent = new Intent(AuthActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void saveUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", emailEditText.getText().toString());
        editor.putBoolean("isLoggedIn", true);
        editor.apply();
    }
}

