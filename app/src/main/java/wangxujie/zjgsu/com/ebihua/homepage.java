package wangxujie.zjgsu.com.ebihua;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by xu10858 on 2017/4/23.
 */

public class homepage extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        Button button_guanka = (Button) findViewById(R.id.guanka);
        Button button_tuichu = (Button) findViewById(R.id.tuichu);

        button_guanka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent successlogin = new Intent(homepage.this, one.class);
                startActivity(successlogin);
            }
        });
        button_tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
