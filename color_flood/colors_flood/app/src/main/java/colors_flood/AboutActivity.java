package colors_flood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import owaa.colors_flood.R;

/**
 * Created by Owaa on 25/11/2015.
 */
public class AboutActivity extends Activity {


    Button return_button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        final Context context = this;


        return_button = (Button) findViewById(R.id.return_button);

        return_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MenuActivity.class);
                startActivity(intent);


            }

        });
    }



}
