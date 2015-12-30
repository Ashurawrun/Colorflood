package colors_flood;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

import owaa.colors_flood.R;

/**
 * Created by Owaa on 16/11/15.
 */

public class ViewCanvas extends SurfaceView implements SurfaceHolder.Callback, Runnable {



    private Bitmap imgVictoire;
    private Bitmap imgDefaite;
    private     Thread  cv_thread;
    SurfaceHolder holder;
    private Resources mRes;
    private Context 	mContext;
    // Nombre de case en X et Y
    static final int carteWidth = 20;
    static final int carteHeight = 23;

    static private int countDown = 60;

    int nbCoutLimite = 30;
    int nbCoutActuelle = 0;
    int nbColors = 3;
    boolean clickOK = false;
    // Grille
    Grille grille = new Grille(carteWidth,carteHeight , nbColors);

    int tailleMapX , tailleMapY;


    int colorSelected = 0;
    int colorActual = 0;
    double time_start;
    int time_passe;
    boolean loose = false;
    boolean win = false;


    public ViewCanvas(Context cxt, AttributeSet attrs) {


        super(cxt, attrs);
        setMinimumHeight(100);
        setMinimumWidth(100);

        mContext	= cxt;
        mRes 		= mContext.getResources();
        imgVictoire 		= BitmapFactory.decodeResource(mRes, R.drawable.victoire);
        imgDefaite		= BitmapFactory.decodeResource(mRes, R.drawable.gameover);

        initparameters();
        cv_thread   = new Thread(this);

        holder = getHolder();
        holder.addCallback(this);
        // prise de focus pour gestion des touches
        setFocusable(true);
        loose = false;
        time_start = System.currentTimeMillis();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        //Here you can get the size!
        tailleMapX = getWidth();
        tailleMapY = getHeight();
    }

    private void paintcarte(Canvas canvas) {

        Paint paint = new Paint();

        int tailleCarre  = tailleMapX / carteWidth ;
        int tailleCarreY = tailleMapY / carteWidth  ;

        paint.setColor(Color.RED);

        float left = 0;
        float top =  0;
        float right = tailleCarre;
        float bottom = tailleCarre;


        for(int i = 0 ; i< carteWidth ;i++){
            for(int j = 0 ; j < carteHeight ; j++){

                // Change l'emplacement du carre
                left   =  tailleCarre * i;
                top    =  tailleCarre * j;
                right  = (tailleCarre * i) + tailleCarre;
                bottom = (tailleCarre * j) + tailleCarre;

                switch( grille.map[i][j]){

                    case 1 :  paint.setColor(Color.RED); break;
                    case 2 :  paint.setColor(Color.YELLOW); break;
                    case 3 :  paint.setColor(Color.GREEN); break;
                    case 4 :  paint.setColor(Color.BLUE); break;
                    case 5 :  paint.setColor(Color.CYAN); break;
                    case 6 :  paint.setColor(Color.MAGENTA); break;
                }
                canvas.drawRect(left, top, right, bottom, paint);


            }

        }

    }

    private void paintColorButton(Canvas canvas, int nbColors) {

        Paint paint = new Paint();

        int tailleCarre  = tailleMapX / 6 ;
        int tailleCarreLargeur  = tailleMapX / nbColors ;
        // Initialisation de la position
        float leftx = 0;
        float topy =  tailleMapY - tailleCarre - 1;
        float rightx = tailleCarreLargeur;
        float bottomy = tailleMapY;

            for(int j = 0 ; j < nbColors ; j++){
                // Dessine en coulleur nbColors en bas
                paint.setStyle(Paint.Style.FILL);
                switch( j + 1){

                    case 1 :  paint.setColor(Color.RED); break;
                    case 2 :  paint.setColor(Color.YELLOW); break;
                    case 3 :  paint.setColor(Color.GREEN); break;
                    case 4 :  paint.setColor(Color.BLUE); break;
                    case 5 :  paint.setColor(Color.CYAN); break;
                    case 6 :  paint.setColor(Color.MAGENTA); break;
                }
                canvas.drawRect(leftx, topy, rightx, bottomy, paint);

                // Dessine en Noir les contours
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.BLACK);
                canvas.drawRect(leftx, topy, rightx, bottomy, paint);

                // Change l'emplacement du carre
                leftx += tailleCarreLargeur;
                rightx += tailleCarreLargeur;

            }
    }

    private void paintBarreTemps(Canvas canvas , float ratioBarre) {

        Paint paint = new Paint();


        int tailleCarre  = tailleMapX / carteWidth ;
        // gere le decalage de la barre de temps.

        float left = (tailleMapX *  ratioBarre);;
        float top =  tailleCarre * carteHeight;
        float right = (float) (tailleMapX );
        float bottom = (tailleCarre * carteHeight) + tailleCarre - 1;

        // Si la barre atteint un certain seuil elle devient rouge
        if(ratioBarre <= (float) 0.80)
        paint.setColor(Color.GREEN);
        else  paint.setColor(Color.RED);

        canvas.drawRect(left, top, right, bottom, paint);
        // dessein en NOIR du contour de la bare de temps
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawRect(left, top, right, bottom, paint);
    }

    public void drawAll(Canvas cv) {


        float ratioBarre = (float) time_passe / countDown;
        if(ratioBarre > 1.0)
            ratioBarre = (float) 1.0;

        cv.drawColor(Color.BLACK);

        paintBarreTemps(cv, ratioBarre);
        paintcarte(cv);
        paintColorButton(cv, nbColors);

        if(win){
            Paint textPaint = new Paint();
            textPaint.setARGB(250, 0, 0, 0);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(100);

            //cv.drawText("Victoire", cv.getWidth() / 2, cv.getHeight() / 2, textPaint);

            cv.drawBitmap(imgVictoire, tailleMapY/2 - 400, tailleMapX/2, null);

        }
        else if(loose){
            Paint textPaint = new Paint();
            textPaint.setARGB(250, 0, 0, 0);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(100);
            cv.drawBitmap(imgDefaite, tailleMapY/2 - 400, tailleMapX/2 , null);
           // cv.drawText("Defaite", cv.getWidth()/2, cv.getHeight()/2  , textPaint);

        }



    }


    public void initparameters() {


    // met au hasard la map pour les test
        Random rand = new Random();
        for(int i = 0 ; i< carteWidth ;i++){
            for(int j = 0 ; j < carteHeight ; j++){

                grille.map[i][j] = rand.nextInt( nbColors) + 1;

            }
        }


        if ((cv_thread!=null) && (!cv_thread.isAlive())) {
            cv_thread.start();
            Log.e("-FCT-", "cv_thread.start()");
        }
    }

    // callback sur le cycle de vie de la surfaceview
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("-> FCT <-", "surfaceChanged " + width + " - " + height);
        initparameters();
    }

    public void surfaceCreated(SurfaceHolder arg0) {
        Log.i("-> FCT <-", "surfaceCreated");
    }


    public void surfaceDestroyed(SurfaceHolder arg0) {
        Log.i("-> FCT <-", "surfaceDestroyed");
    }

    /**
     * run (run du thread cr��)
     * on endort le thread, on modifie le compteur d'animation, on prend la main pour dessiner et on dessine puis on lib�re le canvas
     */
    public void run() {
        Canvas c = null;
        while ( true) {
            try {
                cv_thread.sleep(40);

                try {
                    c = holder.lockCanvas(null);
                    drawAll(c);

                    if( !(loose || win)) {
                        time_passe = (int) (System.currentTimeMillis() - time_start) / 1000;
                        if (time_passe - 2 >= countDown)
                            loose = true;
                    }
                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }
            } catch(Exception e) {
            //    Log.e("-> RUN <-", "PB DANS RUN");
            }
        }
    }
    /*

    Modifier le color actual et selected


     */
    public void changeColors(){

        if (colorSelected != colorActual){

            win = grille.changeColors(colorSelected);

            nbCoutActuelle++;

            if( nbCoutActuelle >= nbCoutLimite)
                loose = true;
        }

    }


    // fonction permettant de recuperer les evenements tactiles
    public boolean onTouchEvent (MotionEvent event) {

        Log.i("-> FCT <-", "onTouchEvent: x :"+ event.getX()+ " y :  " + event.getY());

        // Quant on pert on ne gére plus les touches
        clickOK = false;
        if( loose) return super.onTouchEvent(event);
        if( win) return super.onTouchEvent(event);

        if (event.getY() > 870) {

            clickOK = true;
            // 6 = nbCouleurs !
            int tailleCarre = tailleMapX/nbColors ;
            colorSelected =(int) event.getX()/tailleCarre +1;
            changeColors();
            Log.i("-> FCT <-", "onTouchEvent: Couleur selectioner = "  + colorSelected );


        }
        return super.onTouchEvent(event);
    }


}