package com.example.appandroid;

import com.example.appandroid.Mi_fragment_112247AM;
import com.example.appandroid.R;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Base {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		user = getIntent().getStringExtra("user");
		Mensaje("Bienvenido "+user);
		android.app.FragmentManager FM_112242AM = getFragmentManager();
		FragmentTransaction FT_112242AM = FM_112242AM.beginTransaction();
		// creamos una instancia de nuestro fragment
		Mi_fragment_112247AM F1_112242AM = new Mi_fragment_112247AM();
		llenarLista(F1_112242AM);
		FT_112242AM.add(R.id.contenedor_fragment_112246AM, F1_112242AM);
		FT_112242AM.addToBackStack(null);
		FT_112242AM.commit();
		Init();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
