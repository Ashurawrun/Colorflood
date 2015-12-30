package colors_flood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import owaa.colors_flood.R;

public class MainActivity extends Activity {

    private ViewCanvas mViewCanvas;
    Button button_menu;
    Button button_recomencer;
    Button button_continuer;
    public TextView coutTextView;
    public TextView temps;
    public MediaPlayer sound_win;
    public MediaPlayer sound_loose;
    public MediaPlayer sound_backGround;
    public MediaPlayer sound_touch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        sound_win = MediaPlayer.create(this, R.raw.victoire);
        sound_loose = MediaPlayer.create(this, R.raw.game_over);
        sound_touch = MediaPlayer.create(this, R.raw.bouton);
        sound_backGround = MediaPlayer.create(this, R.raw.game_music);

        sound_backGround.setLooping(true);

        sound_backGround.start();

        setContentView(R.layout.activity_main);
        addListenerOnButton();
        mViewCanvas = (ViewCanvas)findViewById(R.id.view);
        mViewCanvas.setVisibility(View.VISIBLE);

        final TextView tv = (TextView) findViewById(R.id.helloText);
       // final TextView temps = (TextView) findViewById(R.id.temps);

        coutTextView = (TextView) findViewById(R.id.Cout);
        temps = (TextView) findViewById(R.id.temps);

        temps.setText(" BouchAich");


        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String nbPoint = SP.getString("NbPoint", "0");
        String level = SP.getString("LevelActuelle", "3");

        mViewCanvas.nbCoutLimite += Integer.valueOf(nbPoint);
        mViewCanvas.nbColors = Integer.valueOf(level);

        coutTextView.setText("Coup : " + mViewCanvas.nbCoutActuelle +" / "+ mViewCanvas.nbCoutLimite);
      //  coutTextView.setText(nbPoint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // On  modifie la texte View pourqu'elle affiche le bon nombre de coup.
        coutTextView.setText("Coup : " + mViewCanvas.nbCoutActuelle +" / "+ mViewCanvas.nbCoutLimite);

        if(mViewCanvas.clickOK)
            sound_touch.start();

       if( mViewCanvas.win ) {

           sound_win.start();
           sound_backGround.pause();


           SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor edit = SP.edit();

            edit.putString("NbPoint", String.valueOf(mViewCanvas.nbCoutLimite - mViewCanvas.nbCoutActuelle) );
            edit.apply();
        }
        if( mViewCanvas.loose ) {

            sound_loose.start();
            sound_backGround.pause();

        }

        return super.onTouchEvent(event);
    }

    public void addListenerOnButton() {
        final Context context = this;

        button_recomencer = (Button) findViewById(R.id.button_recomencer);

        button_recomencer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mViewCanvas.loose = false;
                mViewCanvas.win = false;

                mViewCanvas.nbCoutActuelle = 0;
                mViewCanvas.nbCoutLimite = 30;
                mViewCanvas.nbColors = 3;

                mViewCanvas.grille.recommencer( mViewCanvas.nbColors);
                SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor edit = SP.edit();

                edit.putString("NbPoint", "0");
                edit.apply();



                edit.putString("LevelActuelle", "3");
                edit.apply();
                coutTextView.setText("Coup : " + mViewCanvas.nbCoutActuelle + " / " + mViewCanvas.nbCoutLimite);

                mViewCanvas.time_start = System.currentTimeMillis();
            }

        });

        button_menu = (Button) findViewById(R.id.button_menu);

        button_menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MenuActivity.class);
                startActivity(intent);
                sound_backGround.stop();

                sound_backGround.release();
                sound_loose.release();
                sound_backGround.release();
                sound_touch.release();


            }

        });


        button_continuer = (Button) findViewById(R.id.button_continuer);

        button_continuer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (mViewCanvas.win) {


                   // sound_backGround.stop();
                    sound_backGround.start();


                    mViewCanvas.nbColors++;
                    mViewCanvas.grille.recommencer(mViewCanvas.nbColors);
                    mViewCanvas.loose = false;
                    mViewCanvas.win = false;
                    mViewCanvas.time_start = System.currentTimeMillis();

                    mViewCanvas.nbCoutActuelle = 0;

                    SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    String nbPoint = SP.getString("NbPoint", "0");
                    mViewCanvas.nbCoutLimite += Integer.valueOf(nbPoint);


                    SharedPreferences.Editor edit = SP.edit();

                    edit.putString("Levels", String.valueOf(mViewCanvas.nbColors) );
                    edit.apply();
                    coutTextView.setText("Coup : " + mViewCanvas.nbCoutActuelle + " / " + mViewCanvas.nbCoutLimite);
                }
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the ; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
