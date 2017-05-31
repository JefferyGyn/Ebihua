package Epackage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import wangxujie.zjgsu.com.ebihua.R;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViewById(R.id.start_game).setOnClickListener(this);
		findViewById(R.id.exit).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		final int id = v.getId();
		Intent	 i = null;
		switch(id){
		case R.id.start_game:
			i=new Intent(this,GameChooseActivity.class);
			startActivity(i);
			break;
		case R.id.exit:
			finish();
		break;
		}
	}
	@Override
	protected void onPause() {
		super.onPause();
	}
}
