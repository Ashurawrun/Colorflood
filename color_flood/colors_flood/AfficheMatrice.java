package colors_flood;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import owaa.colors_flood.R;

/**
 * Created by Owaa on 15/11/15.
 */
public class AfficheMatrice extends View {



    private Bitmap violet;
    private Bitmap jaune;

    // Declaration des objets Ressources et Context permettant d'acc�der aux ressources de notre application et de les charger
    private Resources mRes;
    private Context   mContext;

    static final int carteHeight = 10;
    static final int carteWidth = 10;
    static final int    carteTileSize = 50;



    int carteTopAnchor  = (getHeight()- carteHeight*carteTileSize)/2;
    int carteLeftAnchor = (getWidth()- carteWidth*carteTileSize)/2;

    int tailleMapX = getWidth();



    public AfficheMatrice(Context context) {
        super(context);


        mContext	= context;
        mRes 		= mContext.getResources();
        violet 		= BitmapFactory.decodeResource(getResources(), R.mipmap.violet);
        jaune 		= BitmapFactory.decodeResource(getResources(), R.mipmap.jaune);
    }


    private void paintcarte(Canvas canvas) {
        boolean t = true;
        Paint paint = new Paint();
        int tailleCarre = tailleMapX/carteWidth;
        paint.setColor(Color.RED);


        for (int i=0; i< carteHeight ; i++) {
            for (int j=0; j< carteWidth; j++) {
                System.out.print("taille carre " + tailleCarre);
                     //   if(t)
                  //      canvas.drawBitmap(violet, carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                          //  canvas.drawRect(carteLeftAnchor + j * carteTileSize, (carteLeftAnchor + j * carteTileSize) + carteTileSize,
                                 //   carteTopAnchor + i * carteTileSize, (carteTopAnchor + i * carteTileSize) + carteTileSize, paint);
              //  else
                  //      canvas.drawBitmap(jaune, carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                          //  canvas.drawRect(carteLeftAnchor+ j*carteTileSize, ( carteLeftAnchor+ j*carteTileSize) + carteTileSize ,
                                  //  carteTopAnchor+ i*carteTileSize, (carteTopAnchor+ i*carteTileSize) +carteTileSize ,  paint);
               // t =!t;
                canvas.drawRect(tailleCarre , tailleCarre ,tailleCarre+tailleCarre , tailleCarre+tailleCarre ,  paint);



            }
        }
    }
    // Dessinons sur la totalité de l'écran
    protected void onDraw(Canvas canvas) {
        canvas.drawRGB(0, 0, 0);

        // Instance de Paint pour définir l'attribut couleur de notre point, ainsi que
        // sa taille.
        Paint paint = new Paint();

        // Nous allons dessiner nos points par rapport à la résolution de l'écran
        int iWidth = canvas.getWidth(); // Largeur
        int iHeight = canvas.getHeight(); // Hauteur
       // paintcarte(canvas);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
               // canvas.drawBitmap(violet, 0, 0, null);
            }
        }
    }




}