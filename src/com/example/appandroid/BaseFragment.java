package com.example.appandroid;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// Modifique el manifest: android:minSdkVersion="12" 
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
// Recuerde:l manifest: android:minSdkVersion="12" 
public class BaseFragment extends Fragment {
private List<ClaseListView_112257AM> MiLista_112257AM= new ArrayList<ClaseListView_112257AM>();



	public void Mensaje(String msg){
	   	 Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();};


protected void LlenarLista_112257AM() {
	MiLista_112257AM.add(new ClaseListView_112257AM("Primero", R.drawable.ic_launcher));
            MiLista_112257AM.add(new ClaseListView_112257AM("Segundo", R.drawable.ic_launcher));
            MiLista_112257AM.add(new ClaseListView_112257AM("Tercero",R.drawable.ic_launcher));
	 
	
}

protected void LlenarListView_112257AM( View v ) {
	ArrayAdapter<ClaseListView_112257AM> adapter = new MyListAdapter_112257AM();
	ListView list = (ListView) v.findViewById(R.id.ListView_112257AM);
	list.setAdapter(adapter);
}

protected void OnClickdeListView_112257AM( View v ) {
	ListView list = (ListView) v.findViewById(R.id.ListView_112257AM);
	list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View viewClicked,
				int position, long id) {
			
			ClaseListView_112257AM clickedItem = MiLista_112257AM.get(position);
			String message = "Elegiste item No.  " + (1+position)
							+ " que es " + clickedItem.getNombre();
			Mensaje(message);
		}
	});
}

private class MyListAdapter_112257AM extends ArrayAdapter<ClaseListView_112257AM> {
	public MyListAdapter_112257AM() {
		super(getActivity(), R.layout.listview_112257am, MiLista_112257AM);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Nos aseguramos que tenemos un view con el que trabajar (que no sea nulo)
		View itemView = convertView;
		if (itemView == null) {
			itemView =
					 LayoutInflater.from(getActivity()).inflate(
					            R.layout.listview_112257am, null);
				//	getActivity().getLayoutInflater(R.layout.listview_112257am, parent, false);
			
			//getLayoutInflater().inflate(R.layout.listview_112257am, parent, false);
			
		}
		
		ClaseListView_112257AM ObjetoActual = MiLista_112257AM.get(position);
		
		// Llenamos el  view
		ImageView imageView = (ImageView)itemView.findViewById(R.id.imagen_112303AM);
		imageView.setImageResource(ObjetoActual.getIconID());
		
		// Make:
		TextView makeText = (TextView) itemView.findViewById(R.id.textView_112303AM);
		makeText.setText(ObjetoActual.getNombre());
		

		return itemView;
	}				
}	
} // Fin class BaseFragment