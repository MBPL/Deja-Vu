package mbpl.androidpassword.DejaVu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mbpl.androidpassword.R;

/**
 * Created by benja135 on 05/03/16.
 * Accueil de la méthode Déjà Vu.
 */
public class Accueil extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.accueil_deja_vu);

        // Listener sur le bouton "Mode par défaut"
        Button btn = (Button) findViewById(R.id.buttonDefault);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent creation = new Intent(Accueil.this, Creation.class);
                startActivity(creation);
            }
        });

        // Listener sur le bouton "Paramétrage avancé"
        Button btn2 = (Button) findViewById(R.id.buttonAdvanced);

        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent creation = new Intent(Accueil.this, Configuration.class);
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
