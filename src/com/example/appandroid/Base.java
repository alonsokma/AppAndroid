package com.example.appandroid;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;

public class Base extends ActionBarActivity {
	static String TextoCompartido = "";
	static ImageView ImagenCompartida;

	public GregorianCalendar month, itemmonth;// calendar instances.

	public CalendarAdapter adapter;// adapter instance
	public Handler handler;// for grabbing some event values for showing the dot
							// marker.
	public ArrayList<String> items; // container to store calendar items which
									// needs showing the event marker

	public void Mensaje(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	};

	public void Init() {
		Locale.setDefault(Locale.US);
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		itemmonth = (GregorianCalendar) month.clone();

		items = new ArrayList<String>();
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

	protected void OnClickdeGridView() {
		GridView gridview = (GridView) findViewById(R.id.gridview);
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

				Mensaje(selectedGridDate);

			}
		});
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

}// fin de la clase base
