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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benja135 on 05/03/16.
 *
 */
public class Authentification extends AppCompatActivity {

    private List<Integer> trueMotDePasse = new ArrayList<Integer>();
    private List<Integer> inputMotDePasse = new ArrayList<Integer>();
    private final int nbIcone = 259;
    private int nbLigne = 6;
    private int nbColonne = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mot de passe de 4 en dur pour les tests
        trueMotDePasse.add(7);
        trueMotDePasse.add(7);
        trueMotDePasse.add(7);
        trueMotDePasse.add(7);

    }

    @Override
    protected void onStart() {
        super.onStart();
        drawAndSetListeners(trueMotDePasse.get(0));
    }

    /**
     * Affiche les icônes et ajoute un listener sur chacun d'entre eux.
     * iconToBeDisplaye sera forcément affiché, à une position aléatoire.
     */
    private void drawAndSetListeners(int iconToBeDisplayed) {

        boolean[] iconAlreadyDisplayed = new boolean[nbIcone];
        for (int i = 0; i < nbIcone; i++) {
            iconAlreadyDisplayed[i] = false;
        }

        int positionIconToBeDisplayed = randomInto(0, nbLigne*nbColonne-1);

        GridLayout gridLayout = new GridLayout(this);

        // Cache la barre d'action
        android.support.v7.app.ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.hide();
        }

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y - getStatusBarHeight();

        gridLayout.setColumnCount(nbColonne);
        gridLayout.setRowCount(nbLigne);

        for (int l = 0; l < nbLigne; l++) {
            for (int c = 0; c < nbColonne; c++) {

                ImageView iv;
                iv = new ImageView(this);

                // Crée un bitmap d'une icone piochée aléatoirement (ou pas)
                int numIcon;
                if ((l*nbColonne + c) == positionIconToBeDisplayed) {
                    numIcon = trueMotDePasse.get(inputMotDePasse.size());
                } else {
                    do {
                        numIcon = randomInto(1, 259);
                    } while (iconAlreadyDisplayed[numIcon] || numIcon == iconToBeDisplayed);
                }
                iconAlreadyDisplayed[numIcon] = true;

                Bitmap bmp;
                bmp = BitmapFactory.decodeResource(getResources(), getDrawableN(numIcon));
                bmp = Bitmap.createScaledBitmap(bmp, 256, 256, true); // les icones prennent moins de place en mémoire après cette méthode

                // On ajoute l'icone à l'ImageView
                iv.setImageBitmap(bmp);

                // Ajoute un listener sur l'icon
                final int finalNumIcon = numIcon;
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changePhase(finalNumIcon);
                        //Toast.makeText(Authentification.this, "! " + finalNumIcon, Toast.LENGTH_LONG).show();
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

        setContentView(gridLayout);
    }


    private void changePhase(int selectedIcon) {
        inputMotDePasse.add(selectedIcon);

        if (inputMotDePasse.size() == trueMotDePasse.size()) {
            if (inputMotDePasse.equals(trueMotDePasse)) {
                Toast.makeText(Authentification.this, "Authentification OK !", Toast.LENGTH_LONG).show();
                inputMotDePasse.clear(); // TODO modifier
            } else {
                Toast.makeText(Authentification.this, "Authentification échoué", Toast.LENGTH_LONG).show();
                inputMotDePasse.clear();
            }

        }

        drawAndSetListeners(trueMotDePasse.get(inputMotDePasse.size()));
    }

    /**
     * Retourne un nombre aléatoire entre min et max.
     *
     * @param min
     * @param max
     * @return entier aléatoire
     */
    private int randomInto(int min, int max) {
        return (int) Math.round(Math.random() * (max - min)) + min;
    }

    /**
     * Retourne l'icon n de res/drawable.
     *
     * @param n
     * @return id
     */
    private int getDrawableN(int n) {
        int id = getResources().getIdentifier("icon256x256_" + n, "drawable", getPackageName());
        return id;
    }


    /**
     * Retourne la hauteur de la barre de notification
     *
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
