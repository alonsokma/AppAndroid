package com.example.appandroid;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.example.appandroid.CalendarAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;

public class Base extends Activity {
	static String TextoCompartido = "";
	static ImageView ImagenCompartida;

	public GregorianCalendar month, itemmonth;
	public CalendarAdapter adapter;
	public Handler handler;
	public ArrayList<String> items;
	public ArrayList<Actividad> actividades;
	final Context context = this;
	private String selectedGridDate;

	private String nombre;
	private String fecha;
	private String detalle;
	private int tipo;
	private Timestamp hora;
	private String nick;
	public static String user;
	private String contactName;
	private String contactPhone;
	private GregorianCalendar calen;

	public void Mensaje(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	};

	public void Init() {
		Locale.setDefault(Locale.US);
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		itemmonth = (GregorianCalendar) month.clone();

		items = new ArrayList<String>();
		actividades = new ArrayList<Actividad>();
		adapter = new CalendarAdapter(this, month);

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(adapter);

		handler = new Handler();
		handler.post(calendarUpdater);

		TextView title = (TextView) findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

		OnClickdeGridView();
		OnClickdeNextMonth();
		OnClickdeNextYear();
		OnClickdePreviousMonth();
		OnClickdePreviousYear();
	};

	public void MensajeOK(String msg) {
		TextView texto;
		texto = new TextView(this);
		texto.setText(msg);
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setView(texto);

		builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Puede colocar código aquí
			}
		});
		AlertDialog alert11 = builder1.create();
		alert11.show();
	};

	public void AgregarActividad() {
		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View promptView = layoutInflater.inflate(R.layout.prompts, null);

		Intent intento = new Intent(getApplicationContext(), Prompts.class);
		intento.putExtra("str1", fecha);
		intento.putExtra("user", nick);
		startActivity(intento);
		/*
		 * AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
		 * context);
		 * 
		 * // set prompts.xml to be the layout file of the alertdialog builder
		 * alertDialogBuilder.setView(promptView);
		 * 
		 * // setup a dialog window ActivarSpinner_74134PM(); alertDialogBuilder
		 * .setCancelable(false) .setPositiveButton("Agregar", new
		 * DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dialog, int id) { // get user input and set
		 * it to result //editTextMainScreen.setText(input.getText()); } })
		 * .setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
		 * { public void onClick(DialogInterface dialog, int id) {
		 * dialog.cancel(); } });
		 * 
		 * // create an alert dialog AlertDialog alertD =
		 * alertDialogBuilder.create();
		 * 
		 * alertD.show();
		 */
	}

	@SuppressLint("SimpleDateFormat")
	protected void OnClickdeGridView() {
		final GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				String selectedGridDate = CalendarAdapter.dayString
						.get(position);
				String[] separatedTime = selectedGridDate.split("-");
				String gridvalueString = separatedTime[2].replaceFirst("^0*",
						"");// taking last part of date. ie; 2 from 2012-12-02.
				int gridvalue = Integer.parseInt(gridvalueString);
				// navigate to next or previous month on clicking offdays.
				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				}
				((CalendarAdapter) parent.getAdapter()).setSelected(v);

				//registerForContextMenu(v);
				/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		        Date parsed = null;
				try {
					parsed = (Date) format.parse(selectedGridDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        fecha = new java.sql.Date(parsed.getTime());*/
				fecha = selectedGridDate;
				Mensaje(selectedGridDate);

			}
		});
		
		gridview.setOnCreateContextMenuListener(this);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.mymenu, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AgregarActividad();
		return super.onContextItemSelected(item);
	}

	protected void OnClickdeNextMonth() {
		RelativeLayout next = (RelativeLayout) findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();

			}
		});
	}

	protected void OnClickdePreviousMonth() {
		RelativeLayout previous = (RelativeLayout) findViewById(R.id.previous);

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});
	}

	protected void OnClickdeNextYear() {
		RelativeLayout nextYear = (RelativeLayout) findViewById(R.id.nextyear);
		nextYear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setNextYear();
				refreshCalendar();

			}
		});
	}

	protected void OnClickdePreviousYear() {
		RelativeLayout previousYear = (RelativeLayout) findViewById(R.id.previousyear);

		previousYear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setPreviousYear();
				refreshCalendar();
			}
		});
	}

	protected void setNextMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1),
					month.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) + 1);
		}

	}

	protected void setPreviousMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}

	}

	protected void setNextYear() {
		month.set(GregorianCalendar.YEAR, month.get(GregorianCalendar.YEAR) + 1);
	}

	protected void setPreviousYear() {
		month.set(GregorianCalendar.YEAR, month.get(GregorianCalendar.YEAR) - 1);

	}

	public void refreshCalendar() {
		TextView title = (TextView) findViewById(R.id.title);

		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		handler.post(calendarUpdater); // generate some calendar items

		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}

	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			items.clear();

			// Print dates of the current week
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			String itemvalue;
			for (int i = 0; i < 7; i++) {
				itemvalue = df.format(itemmonth.getTime());
				itemmonth.add(GregorianCalendar.DATE, 1);
				items.add("2015-09-12");
				items.add("2015-10-07");
				items.add("2015-10-15");
				items.add("2015-10-20");
				items.add("2015-11-30");
				items.add("2015-11-28");
			}

			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};

	public void showTimePickerDialog(View v) {
		int hour;
		int minute;
		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		TimePickerDialog newFragment = new TimePickerDialog(this,
				timePickerListener, hour, minute, false);
		newFragment.show();
		// TextView hora = (TextView) findViewById(R.id.hour);
		// hora.setText(hora.getText().toString()+24+":"+34);
	}

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {

			TextView thora = (TextView) findViewById(R.id.hour);
			thora.setText("Hora de inicio: ");
			thora.setText(thora.getText().toString() + padding_str(selectedHour)
					+ ":" + padding_str(selectedMinute));
			// Mensaje(hora.getText().toString());
			
			/*String str_hour=padding_str(selectedHour)+ ":" + padding_str(selectedMinute);
	        DateFormat formatter ; 
	        Date date = null ; 
	        formatter = new SimpleDateFormat("hh:mm");
	        try {
				date = (Date)formatter.parse(str_hour);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	        hora = new Timestamp(date.getTime());*/
		}
	};

	private static String padding_str(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	static final int PICK_CONTACT = 1;

	public void OnclickDelButton(final View view) {
		Button miButton = (Button) view;
		miButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// if(msg.equals("Texto")){Mensaje("Texto en el botón ");};
				switch (v.getId()) {
				case R.id.btnRegistrar: {
					Intent intento = new Intent(getApplicationContext(),
							Registro.class);
					startActivity(intento);
				};break;
				case R.id.btnRegU: {
					EditText innick = (EditText) findViewById(R.id.input_nick);
					String nick = innick.getText().toString();
					EditText innombre = (EditText) findViewById(R.id.input_nombre);
					String nombre = innombre.getText().toString();
					EditText inpass = (EditText) findViewById(R.id.input_pass);
					String pass = inpass.getText().toString();
					if (AgregarUsuario(nick, nombre, pass)) {
						Intent intento = new Intent(getApplicationContext(),
								Login.class);
						startActivity(intento);
					}
				};break;
				case R.id.btnIniciar: {
					EditText innick = (EditText) findViewById(R.id.inputnick);
					String nick = innick.getText().toString();
					EditText inpass = (EditText) findViewById(R.id.inputpass);
					String pass = inpass.getText().toString();
					if (ObtenerUsuario(nick, pass)) {
						Intent intento = new Intent(getApplicationContext(),
								MainActivity.class);
						intento.putExtra("user", nick);
						startActivity(intento);				
					} else {
						Mensaje("Usuario o Contraseña Invalidos");
					}
				};break;
				case R.id.btn:{
					Intent intent = new Intent(Intent.ACTION_PICK,
							ContactsContract.Contacts.CONTENT_URI);
					startActivityForResult(intent, PICK_CONTACT);
					EditText incontact = (EditText) findViewById(R.id.contactsearch);
					incontact.setText(contactName +":"+contactPhone);
				};break;
				case R.id.btnAdd:{
					EditText nom = (EditText) findViewById(R.id.namein);
					nombre = nom.getText().toString();
					EditText det = (EditText) findViewById(R.id.detallesin);
					detalle = det.getText().toString();
					
				};break;
				case R.id.btnCancel:{
					Intent intento = new Intent(getApplicationContext(),
							MainActivity.class);
					intento.putExtra("str1", fecha);
					startActivity(intento);
				};break;
				default:
					break;
				}// fin de casos (Button)
			}// fin del onclick
		});
	}

	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);

		switch (reqCode) {
		case (PICK_CONTACT):
			if (resultCode == Activity.RESULT_OK) {
				Uri contactData = data.getData();
				Cursor c = managedQuery(contactData, null, null, null, null);
				if (c.moveToFirst()) {
					contactName = c
							.getString(c
									.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
					String id = c
							.getString(c
									.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

					String hasPhone = c
							.getString(c
									.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

					if (hasPhone.equalsIgnoreCase("1")) {
						Cursor phones = getContentResolver()
								.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
										null,
										ContactsContract.CommonDataKinds.Phone.CONTACT_ID
												+ " = " + id, null, null);
						phones.moveToFirst();

						contactPhone = phones.getString(phones
								.getColumnIndex("data1"));
						
						Mensaje(contactName + contactPhone);
					}

				}
			}
		}
	}

	protected void ActivarSpinner_101825PM() {
		final String[] valores = { "Solo una vez", "Diariamente",
				"Mensualmente", "Anualmente" };
		Spinner spinner = (Spinner) findViewById(R.id.Spinner_101825PM);
		ArrayAdapter<String> Adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, valores);
		Adaptador
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner.setAdapter(Adaptador);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int posicion, long id) {
				tipo = posicion;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		spinner.setAdapter(Adaptador);

	};

	protected void ActivarSpinner_Alarm() {
		final String[] valores = { "A la hora del evento", "1 hora antes",
				"1 dia antes", "1 semana antes" };
		Spinner spinner = (Spinner) findViewById(R.id.Spinner_alarm);
		ArrayAdapter<String> Adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, valores);
		Adaptador
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner.setAdapter(Adaptador);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int posicion, long id) {
				//aqui hay q setear la alarma 
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		spinner.setAdapter(Adaptador);

	};

	public void llenarLista(Mi_fragment_112247AM f) {
		f.LlenarLista_112257AM("Nombre del evento obtenido desde la lista o base de datos");
		// Aqui se cargan las actividades de la base de datos por usuario
	}

	DBAdapter db;

	public void CrearBD() {
		db = new DBAdapter(this);
	}

	public boolean AgregarUsuario(String nick, String nombre, String pass) {
		if (db == null) {
			Log.i("ERROR DB: ", "Error db es null");
			return false;
		}
		db.open();
		if (db.insertUsuario(nick, nombre, pass)) {
			Mensaje("Se Agrego un Usuario");
			db.close();
			return true;
		} else {
			Mensaje("No se pudo agregar un Usuario");
			return false;
		}
	}

	public void ObtenerDatos() {
		// --cargamos todos los datos---
		db.open();
		Cursor c = db.CargarTodosLosUsuarios();
		if (c.moveToFirst()) {
			do {
				MostarDato(c);
			} while (c.moveToNext());
		}
		db.close();
	}

	public boolean ObtenerUsuario(String nick, String pass) {
		// ---cargar un contacto ---
		db.open();
		Cursor c = db.ObtenerUsuario(nick, pass);
		if (c.moveToFirst()) {
			db.close();
			return true;
		} else {
			return false;
		}
	}

	public void ActualizarUsuario(String nick, String nombre, String pass) {
		// ---update a contact---
		db.open();
		if (db.ActualizarActividad(nick, nombre, pass))
			Mensaje("Se actualizó la Actividad");
		else
			Mensaje("Fallo al actualizar");
		db.close();
	}

	public void BorrarDato(String nick) {
		db.open();
		if (db.BorrarUsuario(nick))
			Mensaje("Borrado exitoso");
		else
			Mensaje("Fallo al intentar borrar");
		db.close();
	}

	public void MostarDato(Cursor c) {
		/*
		 * Mensaje("id: " + c.getString(0) + "\n" + "Nombre: " + c.getString(1)
		 * + "\n" + "Dir:  " + c.getString(2));
		 */
	}

}// fin de la clase base
