package mbpl.androidpassword.DejaVu;

import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

import mbpl.androidpassword.R;

/**
 * Created by benja135 on 05/03/16.
 * <p/>
 * - affiche tout les icônes de maniére ordonné dans une grille
 * - possibilité de scroller
 * - un clique sur une icône l'ajoute à la liste des icônes choisies
 * - suppresion des icônes de la liste possible
 * - passSize entre 1 et 12
 *
 * - nombre d'icônes afficher à l'écran (pour faciliter la localisation, ou augmenter la sécurité) -> phase d'authentification
 * - permettre de mettre plusieurs fois un même icône ou pas
 */
public class Creation extends AppCompatActivity {

    private final int nbIcone = 259;
    private final int tailleIcone = 96;
    private final int nbColonne = 10;
    private final int minPassSize = 1;
    private final int maxPassSize = 12;

    private GridLayout gridToolbar;
    private ArrayList pass = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setContentView(R.layout.creation_deja_vu);
        gridToolbar = (GridLayout) findViewById(R.id.gridToolbar);

        drawGridToolbar();
        filledScrollAndSetListeners();

        // Listener sur le bouton "DEL"
        Button btnDel = (Button) findViewById(R.id.btnDel);

        btnDel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pass.size() > 0) {
                    pass.remove(pass.size()-1);
                    drawGridToolbar();
                }
            }
        });

        // Listener sur le bouton "Valider"
        Button btnValider = (Button) findViewById(R.id.btnValider);

        btnValider.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pass.size() > 0) {
                    // TODO rentrer le mdp dans la base
                    Intent authentification = new Intent(Creation.this, Authentification.class);
                    startActivity(authentification);
                }
            }
        });

        Toast.makeText(Creation.this, "Création. Essaye de scroller ! :)", Toast.LENGTH_LONG).show();
    }


    /**
     * Rempli le scrollLayout d'icônes et place les listeners sur les icônes.
     */
    private void filledScrollAndSetListeners() {
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollview);
        GridLayout gridIcons = new GridLayout(this);

        scrollView.addView(gridIcons);

        // Cache la barre d'action si elle existe
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.hide();
        }

        // On récup la largeur de l'écran
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        //screenHeight = size.y - getStatusBarHeight();

        int nbLigne = (int) Math.ceil((float) nbIcone / (float) nbColonne);
        gridIcons.setColumnCount(nbColonne);
        gridIcons.setRowCount(nbLigne);

        //final View mProgressView = findViewById(R.id.progressBar);
        //mProgressView.setVisibility(View.VISIBLE);

        for (int i = 0; i < nbIcone; i++) {
            ImageView iv;
            iv = new ImageView(this);

            // Crée un bitmap de l'icone i
            Bitmap bmp;
            bmp = BitmapFactory.decodeResource(getResources(), getDrawableN(i + 1));
            bmp = Bitmap.createScaledBitmap(bmp, 96, 96, true); // les icones prennent moins de place en mémoire après cette méthode

            // On ajoute l'icone à l'ImageView
            iv.setImageBitmap(bmp);

            // Ajoute un listener sur l'icon
            final int numIcone = i + 1;
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pass.size() < maxPassSize) {
                        pass.add(numIcone);
                        drawGridToolbar();
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
    }

    /**
     * Affiche la toolbar contenant les deux boutons de controle et
     * les icônes choisi pour le mot de passe.
     */
    private void drawGridToolbar() {

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.jaugeSecurUti);

        /*if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(passSize*10);
        } else {
            Toast.makeText(Creation.this, "ProgressBar NULL !", Toast.LENGTH_LONG).show();
        }*/

        Button btnDel = (Button) findViewById(R.id.btnDel);
        Button btnValider = (Button) findViewById(R.id.btnValider);
        gridToolbar.removeAllViews();

        gridToolbar.addView(btnDel);
        if (pass.size() > 0) {
            btnDel.setEnabled(true);
        } else {
            btnDel.setEnabled(false);
        }

        gridToolbar.addView(btnValider);
        if (pass.size() >= minPassSize) {
            btnValider.setEnabled(true);
        } else {
            btnValider.setEnabled(false);
        }

        for (int i = 0; i < pass.size(); i++) {
            ImageView iv;
            iv = new ImageView(this);

            Bitmap bmp;
            bmp = BitmapFactory.decodeResource(getResources(), getDrawableN((int) pass.get(i)));
            bmp = Bitmap.createScaledBitmap(bmp, 96, 96, true);

            iv.setImageBitmap(bmp);

            GridLayout.LayoutParams param = new GridLayout.LayoutParams();

            param.height = btnDel.getHeight() - 20;
            param.width = btnDel.getHeight() - 20;

            param.setMargins(0, 10, 0, 0);
            param.setGravity(Gravity.CENTER);

            param.columnSpec = GridLayout.spec(1 + (i % 6));
            param.rowSpec = GridLayout.spec(i/6);
            iv.setLayoutParams(param);
            gridToolbar.addView(iv);
        }
    }

    /**
     * Retourne l'icon n de res/drawable.
     *
     * @param n numéro de l'icône à récupérer
     * @return id identifiant de l'icône
     */
    private int getDrawableN(int n) {
        return getResources().getIdentifier("icon"
                + tailleIcone + "x" + tailleIcone + "_" + n, "drawable", getPackageName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        pass.clear();
        drawGridToolbar();
    }

}
