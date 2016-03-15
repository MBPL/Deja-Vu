package mbpl.androidpassword.DejaVu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import mbpl.androidpassword.R;

/**
 * Created by benja135 on 05/03/16.
 *
 * - affiche 15*10 icônes choisi aléatoirement
 * TODO tout à faire !
 *
 */
public class Authentification extends AppCompatActivity {

    private final int nbIcone = 259;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busy_circle); // test écran chargement
    }

    @Override
    protected void onStart() {
        super.onStart();

        ScrollView scrollView = new ScrollView(this);

        GridLayout gridLayout = new GridLayout(this);
        scrollView.addView(gridLayout);

        // Cache la barre d'action
        android.support.v7.app.ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.hide();
        }

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y - getStatusBarHeight();

        int nbLigne = 15;
        int nbColonne = 10;
        gridLayout.setColumnCount(nbColonne);
        gridLayout.setRowCount(nbLigne);

        //final View mProgressView = findViewById(R.id.progressBar);
        //mProgressView.setVisibility(View.VISIBLE);

        for (int l = 0; l < nbLigne; l++) {
            for (int c = 0; c < nbColonne; c++) {

                ImageView iv;
                iv = new ImageView(this);

                // Crée un bitmap d'une icone piochée aléatoirement
                final int n = randomInto(1, 259);
                Bitmap bmp;
                bmp = BitmapFactory.decodeResource(getResources(), getDrawableN(n));
                bmp = Bitmap.createScaledBitmap(bmp, 96, 96, true); // les icones prennent moins de place en mémoire après cette méthode

                // On ajoute l'icone à l'ImageView
                iv.setImageBitmap(bmp);

                // Ajoute un listener sur l'icon
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Authentification.this, "" + n, Toast.LENGTH_SHORT).show();
                    }
                });

                // On ajoute l'ImageView au GridLayout en mettant les bon paramétres
                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.height = screenHeight / nbLigne;
                param.width = screenWidth / nbColonne;
                param.setMargins(0, 0, 0, 0);
                param.setGravity(Gravity.CENTER);
                param.columnSpec = GridLayout.spec(c);
                param.rowSpec = GridLayout.spec(l);
                iv.setLayoutParams(param);
                gridLayout.addView(iv);
            }
        }

        setContentView(scrollView);
    }

    /**
     * Retourne un nombre aléatoire entre min et max.
     * @param min
     * @param max
     * @return entier aléatoire
     */
    private int randomInto(int min, int max) {
        return (int) Math.round(Math.random() * (max - min)) + min;
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


    /**
     * Retourne la hauteur de la barre de notification
     * @return taille de la barre de notification
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
