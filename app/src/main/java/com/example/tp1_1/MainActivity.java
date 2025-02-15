package com.example.tp1_1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends Activity {

    private EditText etNom, etPrenom, etAge, etDomaine, etTelephone;
    private Button btnValider, btnChangeLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Charger la langue sauvegardée
        loadLocale();
        setContentView(R.layout.activity_main);

        etNom = findViewById(R.id.etNom);
        etPrenom = findViewById(R.id.etPrenom);
        etAge = findViewById(R.id.etAge);
        etDomaine = findViewById(R.id.etDomaine);
        etTelephone = findViewById(R.id.etTelephone);
        btnValider = findViewById(R.id.btnValider);
        btnChangeLanguage = findViewById(R.id.btnChangeLanguage);

        // Action du bouton Valider
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = etNom.getText().toString().trim();
                String prenom = etPrenom.getText().toString().trim();
                String age = etAge.getText().toString().trim();
                String domaine = etDomaine.getText().toString().trim();
                String telephone = etTelephone.getText().toString().trim();

                if (nom.isEmpty() || prenom.isEmpty() || age.isEmpty() || domaine.isEmpty() || telephone.isEmpty()) {
                    Toast.makeText(MainActivity.this, getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show();
                } else {
                    showConfirmationDialog(nom, prenom, age, domaine, telephone);
                }
            }
        });

        // Bouton pour changer la langue
        btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLanguage();
            }
        });
    }

    // Fonction pour afficher la boîte de dialogue de confirmation
    private void showConfirmationDialog(String nom, String prenom, String age, String domaine, String telephone) {
        // Création de la boîte de dialogue avec un style personnalisé
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_confirmation, null);
        builder.setView(dialogView);

        // Récupérer les éléments du layout
        TextView tvMessage = dialogView.findViewById(R.id.tvDialogMessage);
        Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);
        ImageButton btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnChangeColor = dialogView.findViewById(R.id.btnChangeColor);

        // Création du texte mis en forme avec du gras
        SpannableStringBuilder message = new SpannableStringBuilder();

        appendBoldText(message, getString(R.string.label_nom) + ": ", nom + "\n");
        appendBoldText(message, getString(R.string.label_prenom) + ": ", prenom + "\n");
        appendBoldText(message, getString(R.string.label_age) + ": ", age + " ans" + "\n");
        appendBoldText(message, getString(R.string.label_domaine) + ": ", domaine + "\n");
        appendBoldText(message, getString(R.string.label_telephone) + ": ", telephone + "\n");

        message.append("\n").append(getString(R.string.confirm_prompt));

        tvMessage.setText(message);

        AlertDialog dialog = builder.create();

        // Action du bouton "Confirmer"
        btnConfirm.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("nom", nom);
            intent.putExtra("prenom", prenom);
            intent.putExtra("age", age);
            intent.putExtra("domaine", domaine);
            intent.putExtra("telephone", telephone);
            startActivity(intent);
            dialog.dismiss();
        });

        // Action du bouton "Annuler"
        btnCancel.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, getString(R.string.validation_cancelled), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        // Action du bouton "Changer Couleur"
        btnChangeColor.setOnClickListener(v -> changeEditTextBackgroundColor(Color.YELLOW));

        dialog.show();
    }

    // Fonction pour ajouter du texte en gras dans un SpannableStringBuilder
    private void appendBoldText(SpannableStringBuilder builder, String label, String value) {
        int start = builder.length();
        builder.append(label);
        builder.setSpan(new StyleSpan(Typeface.BOLD), start, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // pour avoir du texte en gras
        builder.append(value).append("\n");
    }



    // Fonction pour changer la couleur de fond des champs de texte
    private void changeEditTextBackgroundColor(int color) {
        etNom.setBackgroundColor(color);
        etPrenom.setBackgroundColor(color);
        etAge.setBackgroundColor(color);
        etDomaine.setBackgroundColor(color);
        etTelephone.setBackgroundColor(color);
    }

    // Fonction pour changer la langue
    private void switchLanguage() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String currentLang = prefs.getString("My_Lang", "fr");
        String newLang = currentLang.equals("fr") ? "en" : "fr";

        saveLocale(newLang);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    // Sauvegarder la langue
    private void saveLocale(String lang) {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("My_Lang", lang);
        editor.apply();

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    // Charger la langue enregistrée
    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "fr");

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}
