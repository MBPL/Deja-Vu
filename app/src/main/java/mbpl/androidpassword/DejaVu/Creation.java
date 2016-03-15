package mbpl.androidpassword.DejaVu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

import mbpl.androidpassword.R;

/**
 * Created by benja135 on 05/03/16.
 *
 * - affiche tout les icônes de maniére ordonné dans une grille
 * - possibilité de scroller
 * - un clique sur une icône l'ajoute à la liste des icônes choisies
 * - suppresion des icônes de la liste possible
 * TODO listener sur le bouton valider, création du data.xml, redirection vers DejaVu.Authentification
 * + faire en sorte que ce soit dynamique en fonction des params suivants :
 *      - passSize entre 2 et 8
 *      - nombre d'icônes afficher à l'écran (pour faciliter la localisation, ou augmenter la sécurité) -> phase d'authentification
 *      - permettre de mettre plusieurs fois un même icône ou pas
 *
 */
public class Creation extends AppCompatActivity {

    private final int nbIcone = 259;
    private final int passSize = 4;
    private int nbSelectedIcon = 0;
    private ArrayList pass = new ArrayList();

    private GridLayout gridToolbar;
    private GridLayout gridIcons;
    private ScrollView scrollView;

    private int screenWidth = 0;
    private final int nbColonne = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busy_circle); // test écran chargement
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.creation_deja_vu);

        gridToolbar = (GridLayout) findViewById(R.id.gridToolbar);
        scrollView = (ScrollView) findViewById(R.id.scrollview);
        gridIcons = new GridLayout(this);

        scrollView.addView(gridIcons);

        resetGridToolbar(); // pour désactiver les boutons

        // Cache la barre d'action si elle existe
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.hide();
        }

        // On récup la largeur de l'écran
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        screenWidth = size.x;
        //screenHeight = size.y - getStatusBarHeight();

        int nbLigne = (int) Math.ceil((float)nbIcone / (float)nbColonne);
        gridIcons.setColumnCount(nbColonne);
        gridIcons.setRowCount(nbLigne);

        //final View mProgressView = findViewById(R.id.progressBar);
        //mProgressView.setVisibility(View.VISIBLE);

        for (int i = 0; i < nbIcone; i++) {
            ImageView iv;
            iv = new ImageView(this);

            // Crée un bitmap de l'icone i
            Bitmap bmp;
            bmp = BitmapFactory.decodeResource(getResources(), getDrawableN(i+1));
            bmp = Bitmap.createScaledBitmap(bmp, 96, 96, true); // les icones prennent moins de place en mémoire après cette méthode

            // On ajoute l'icone à l'ImageView
            iv.setImageBitmap(bmp);

            // Ajoute un listener sur l'icon
            final int numIcone = i + 1;
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(Creation.this, "" + numIcone, Toast.LENGTH_SHORT).show();
                    if (nbSelectedIcon < passSize) {
                        pass.add(nbSelectedIcon, numIcone);
                        nbSelectedIcon++;
                        resetGridToolbar();
                    }
                }
            });

            // On ajoute l'ImageView au GridLayout en mettant les bon paramétres
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.width = screenWidth / nbColonne;
            param.height = param.width;
            param.setMargins(0, 0, 0, 0);
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(i % nbColonne);
            param.rowSpec = GridLayout.spec(i / nbColonne);
            iv.setLayoutParams(param);
            gridIcons.addView(iv);
        }

        // Listener sur le bouton "DEL"
        Button btn = (Button)findViewById(R.id.btnDel);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (nbSelectedIcon > 0) {
                    nbSelectedIcon--;
                    resetGridToolbar();
                }
            }
        });

        Toast.makeText(Creation.this, "Création. Essaye de scroller ! :)", Toast.LENGTH_LONG).show();
    }

    private void resetGridToolbar() {

        Button btnDel = (Button) findViewById(R.id.btnDel);
        Button btnValider = (Button) findViewById(R.id.btnValider);
        gridToolbar.removeAllViews();

        gridToolbar.addView(btnDel);
        if (nbSelectedIcon > 0) {
            btnDel.setEnabled(true);
        } else {
            btnDel.setEnabled(false);
        }

        gridToolbar.addView(btnValider);
        if (nbSelectedIcon == passSize) {
            btnValider.setEnabled(true);
        } else {
            btnValider.setEnabled(false);
        }

        for (int i = 0; i < nbSelectedIcon; i++) {
            ImageView iv;
            iv = new ImageView(this);

            Bitmap bmp;
            bmp = BitmapFactory.decodeResource(getResources(), getDrawableN((int)pass.get(i)));
            bmp = Bitmap.createScaledBitmap(bmp, 96, 96, true);

            iv.setImageBitmap(bmp);

            GridLayout.LayoutParams param = new GridLayout.LayoutParams();

            param.height = btnDel.getHeight()-20;
            param.width = btnDel.getHeight()-20;

            param.setMargins(0, 10, 0, 0);
            param.setGravity(Gravity.CENTER);

            param.columnSpec = GridLayout.spec(i+2);
            param.rowSpec = GridLayout.spec(0);
            iv.setLayoutParams(param);
            gridToolbar.addView(iv);
        }
    }

    /**
     * Retourne l'icon n de res/drawable.
     * @param n
     * @return id
     */
    private int getDrawableN(int n) {
        int id = getResources().getIdentifier("icon96x96_" + n,"drawable", getPackageName());
        return id;
    }

    @Override
    protected void onPause() {
        super.onPause();
        nbSelectedIcon = 0;
        resetGridToolbar();
    }

}
