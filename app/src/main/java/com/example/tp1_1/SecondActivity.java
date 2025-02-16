package com.example.tp1_1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity {

    private TextView tvData;
    private Button btnOk, btnRetour;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvData = findViewById(R.id.tvData);
        btnOk = findViewById(R.id.btnOk);
        btnRetour = findViewById(R.id.btnRetour);

        // Définir le titre de l'activité en fonction de la langue
        setTitle(getString(R.string.activity_second_title));

        // Récupérer les données de l'intent
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("telephone");

        String formattedData = String.format(
                "<b>%s</b>: %s<br><b>%s</b>: %s<br><b>%s</b>: %s ans<br><b>%s</b>: %s<br><b>%s</b>: %s",
                getString(R.string.label_nom), intent.getStringExtra("nom"),
                getString(R.string.label_prenom), intent.getStringExtra("prenom"),
                getString(R.string.label_age), intent.getStringExtra("age"),
                getString(R.string.label_domaine), intent.getStringExtra("domaine"),
                getString(R.string.label_telephone), phoneNumber
        );

        tvData.setText(Html.fromHtml(formattedData));

        // Action du bouton "Valider"
        btnOk.setOnClickListener(v -> {
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                afficherDialogAppel();
            } else {
                Toast.makeText(this, getString(R.string.no_phone_number), Toast.LENGTH_SHORT).show();
            }
        });

        // Action du bouton "Retour"
        btnRetour.setOnClickListener(v -> finish());
    }

    //Affiche un dialogue stylisé demandant à l'utilisateur de confirmer l'appel.
    private void afficherDialogAppel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_call_confirmation, null);
        builder.setView(dialogView);

        // Récupérer les vues
        TextView tvMessage = dialogView.findViewById(R.id.tvDialogMessage);
        Button btnCall = dialogView.findViewById(R.id.btnCall);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        // Définir le message
        tvMessage.setText(getString(R.string.call_message) + "\n\n📞 " + phoneNumber);

        AlertDialog dialog = builder.create();

        // Bouton pour appeler
        btnCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
            dialog.dismiss();
        });

        // Bouton pour annuler
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

}
