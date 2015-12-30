package colors_flood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import owaa.colors_flood.R;

/**
 * Created by Owaa on 25/11/2015.
 */
public class LevelsActivity extends Activity {


    Button lvl1_button;
    Button lvl2_button;
    Button lvl3_button;
    Button lvl4_button;
    MediaPlayer sound;

    int levels = 3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_levels);
        addListenerOnButton();

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String nbPoint = SP.getString("Levels", "3");
        levels = Integer.valueOf(nbPoint);

    }
    public void addListenerOnButton() {
        final Context context = this;
        sound = MediaPlayer.create(this, R.raw.menu);

        lvl1_button = (Button) findViewById(R.id.lvl1_button);

        lvl1_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor edit = SP.edit();

                edit.putString("LevelActuelle", "3");
                edit.apply();
                sound.start();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);


            }

        });

        lvl2_button = (Button) findViewById(R.id.lvl2_button);

        lvl2_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                    SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor edit = SP.edit();

                    edit.putString("LevelActuelle", "4");
                    edit.apply();
                    sound.start();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);



            }

        });
        lvl3_button = (Button) findViewById(R.id.lvl3_button);

        lvl3_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor edit = SP.edit();

                edit.putString("LevelActuelle", "5");
                edit.apply();

                sound.start();

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);

            }

        });
        lvl4_button = (Button) findViewById(R.id.lvl4_button);

        lvl4_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor edit = SP.edit();

                edit.putString("LevelActuelle", "6");
                edit.apply();

                sound.start();

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);

            }

        });

    }
}
