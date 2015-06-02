package com.example.appandroid;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.TextView;

public class Temporizador extends BroadcastReceiver{
	@Override
    public void onReceive(Context context, Intent intent)  { 
            MensajeOK("ALARMA",context);
    }
	
	public void MensajeOK(String msg,Context ctx) {
		TextView texto;
		texto = new TextView(ctx);
		texto.setText(msg);
		AlertDialog.Builder builder1 = new AlertDialog.Builder(ctx);
		builder1.setView(texto);

		builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Puede colocar código aquí
			}
		});
		AlertDialog alert11 = builder1.create();
		alert11.show();
	};
}

