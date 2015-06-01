package com.example.appandroid;

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
		 
		 String msg = getIntent().getStringExtra("str1");
		 TextView t = (TextView) findViewById(R.id.date);
			t.setText("");
			t.setText(t.getText().toString()+msg);
		 
	}
	
}
