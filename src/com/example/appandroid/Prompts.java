package com.example.appandroid;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Prompts extends Base{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prompts);
		 ActivarSpinner_101825PM();
		 ActivarSpinner_Alarm();
		 OnclickDelButton(findViewById(R.id.btn));
		 OnclickDelButton(findViewById(R.id.btnAdd));
		 OnclickDelButton(findViewById(R.id.btnCancel));
		 CrearBD();
		 
		 String msg = getIntent().getStringExtra("str1");
		 user = getIntent().getStringExtra("user");
		 Mensaje("Usuario: "+user);
		 TextView t = (TextView) findViewById(R.id.date);
			t.setText("");
			t.setText(t.getText().toString()+msg);
			
	}
	
}
