package com.example.tp1_1;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class JavaActivity extends AppCompatActivity {

    private EditText etNom, etPrenom, etAge, etDomaine, etTelephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Créer le Layout principal
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(50, 50, 50, 50);
        mainLayout.setGravity(Gravity.CENTER);

        // Ajouter un titre
        TextView tvTitle = new TextView(this);
        tvTitle.setText(getString(R.string.app_name));
        tvTitle.setTextSize(22);
        tvTitle.setGravity(Gravity.CENTER);
        mainLayout.addView(tvTitle);

        // Créer les champs de saisie avec labels
        etNom = createInputField(mainLayout, getString(R.string.label_prenom));
        etPrenom = createInputField(mainLayout, getString(R.string.label_prenom));
        etAge = createInputField(mainLayout, getString(R.string.label_age), InputType.TYPE_CLASS_NUMBER);
        etDomaine = createInputField(mainLayout, getString(R.string.label_domaine));
        etTelephone = createInputField(mainLayout, getString(R.string.label_telephone), InputType.TYPE_CLASS_PHONE);

        // Bouton de validation
        Button btnValidate = new Button(this);
        btnValidate.setText(getString(R.string.btn_valider));
        btnValidate.setOnClickListener(v -> validateInputs());
        mainLayout.addView(btnValidate);

        // Définir la vue principale
        setContentView(mainLayout);
    }

    // Méthode pour créer un champ de saisie avec un label
    private EditText createInputField(LinearLayout parent, String labelText) {
        return createInputField(parent, labelText, InputType.TYPE_CLASS_TEXT);
    }

    private EditText createInputField(LinearLayout parent, String labelText, int inputType) {
        TextView label = new TextView(this);
        label.setText(labelText + " :");
        label.setTextSize(16);
        parent.addView(label);

        EditText editText = new EditText(this);
        editText.setInputType(inputType);
        parent.addView(editText);

        return editText;
    }

    // Validation des entrées et affichage du résultat
    private void validateInputs() {
        String nom = etNom.getText().toString().trim();
        String prenom = etPrenom.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String domaine = etDomaine.getText().toString().trim();
        String telephone = etTelephone.getText().toString().trim();

        if (nom.isEmpty() || prenom.isEmpty() || age.isEmpty() || domaine.isEmpty() || telephone.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show();
            return;
        }

        String message = getString(R.string.label_nom) + nom + "\n" +
                getString(R.string.label_prenom) + prenom + "\n" +
                getString(R.string.label_age) + age + "\n" +
                getString(R.string.label_domaine) + domaine + "\n" +
                getString(R.string.label_telephone) + telephone;

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
