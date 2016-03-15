package mbpl.androidpassword.DejaVu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import mbpl.androidpassword.R;

/**
 * Created by benja135 on 05/03/16.
 *
 * - menu de paramétrage de DéjàVu
 *
 */
public class Accueil extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.accueil_deja_vu);

        // Listener sur le bouton "Mode par défaut"
        Button btn = (Button)findViewById(R.id.buttonDefault);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent creation = new Intent(Accueil.this, Creation.class);
                startActivity(creation);
            }
        });

        /* TODO : faire une classe supplémentaire pour les paramétres avancés
        * ou communiquer dans le bundle pour gérer ça dans Creation */

        // Listener sur le bouton "Paramétrage avancé"
        Button btn2 = (Button) findViewById(R.id.buttonAdvanced);

        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(Accueil.this, "Pas encore implémenté !\n(debug) Vous allez être envoyé sur l'activité d'authentification.", Toast.LENGTH_LONG).show();
                Intent creation = new Intent(Accueil.this, Authentification.class);
                startActivity(creation);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
