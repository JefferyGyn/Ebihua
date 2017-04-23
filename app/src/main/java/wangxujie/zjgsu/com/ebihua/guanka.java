package wangxujie.zjgsu.com.ebihua;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by xu10858 on 2017/4/23.
 */

public class guanka extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guanka);


        Button button_one = (Button) findViewById(R.id.button_1);
        Button button_two = (Button) findViewById(R.id.button_2);

        button_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent successlogin = new Intent(guanka.this, one.class);
                startActivity(successlogin);
            }
        });

        button_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent successlogin = new Intent(guanka.this, two.class);
                startActivity(successlogin);
            }
        });
    }
}
