package mbpl.androidpassword.DejaVu;

import android.app.Activity;
import android.os.Bundle;

import mbpl.androidpassword.R;

/**
 * Created by benja135 on 06/03/16.
 * Configuration avancé de la méthode Déjà Vu.
 * TODO :
 * - nombre d'icônes afficher à l'écran (pour faciliter la localisation, ou augmenter la sécurité) -> phase d'authentification
 * - permettre de mettre plusieurs fois un même icône ou pas
 */
public class Configuration extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurations_deja_vu);
    }

}
