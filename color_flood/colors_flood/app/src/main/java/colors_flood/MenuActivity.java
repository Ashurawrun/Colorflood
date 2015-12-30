package colors_flood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import owaa.colors_flood.R;


/**
 * Created by Owaa on 25/11/2015.
 */
public class MenuActivity extends Activity {

    Button play_button;
    Button level_button;
    Button about_button;
    MediaPlayer sound;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_menu);
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        sound = MediaPlayer.create(this, R.raw.menu);
        final Context context = this;


        play_button = (Button) findViewById(R.id.play_button);

        play_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                sound.start();

            }

        });

        level_button = (Button) findViewById(R.id.level_button);

        level_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, LevelsActivity.class);
                startActivity(intent);
                sound.start();
            }

        });
        about_button = (Button) findViewById(R.id.about_button);

        about_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, AboutActivity.class);
                startActivity(intent);
                sound.start();
            }

        });

    }


}
