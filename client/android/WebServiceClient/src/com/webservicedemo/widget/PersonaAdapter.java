package com.webservicedemo.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.webservicedemo.R;
import com.webservicedemo.dao.WebService;
import com.webservicedemo.model.Persona;

public class PersonaAdapter extends BaseAdapter {
  public static final String TAG = "PersonaAdapter";
  
// TODO: Variables
  
  /**
   * Inflador del la vista.
   */
  private final LayoutInflater mInflater;
  /**
   * Coleccion de items.
   */
  private final List<Persona> mList;
  /**
   * Cache
   */
  private final SharedPreferences mPreferences;
  /**
   * Api Web
   */
  private final WebService mService = WebService.getInstance();

// TODO: Constructor
  
  public PersonaAdapter(Context context) {
    mInflater = (LayoutInflater) context.getApplicationContext()
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    mList = new ArrayList<Persona>();
  }

// TODO: Funciones
  
  /**
   * Carga la cache
   *
   * @return
   */
  public boolean loadCache() {
	String json = mPreferences.getString("list", null);
    if (TextUtils.isEmpty(json)) return false;
    setAll(mService.fromJson(json, Persona[].class));
    return true;
  }

  public void setAll(Persona[] array) {
    synchronized (this) {
      String json = mService.toJson(array);
      mPreferences.edit().putString("list", json).commit();
    	
      mList.clear();
      mList.addAll(Arrays.asList(array));
    }
    // Notifica el cambio al ListView.
    notifyDataSetChanged();
  }

  @Override public int getCount() {
    return mList.size();
  }

  @Override public Object getItem(int position) {
    return mList.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    Holder h;

    if (convertView == null) {
      convertView = mInflater.inflate(R.layout.item_list, parent, false);
      h = new Holder(convertView);
      convertView.setTag(h);
    } else {
      h = (Holder) convertView.getTag();
    }

    final Persona o = mList.get(position);
    h.title.setText(o.nombre);
    h.subtitle.setText(o.apellidos);
    h.right_title.setText(loadIndex(o.nombre));

    return convertView;
  }

// TODO: Extras
  
  class Holder {

    TextView title, subtitle, right_title;

    public Holder(View convertView) {
      subtitle = (TextView) convertView.findViewById(R.id.subtitle);
      title = (TextView) convertView.findViewById(R.id.title);
      right_title = (TextView) convertView.findViewById(R.id.right_title);
    }
  }

  static CharSequence loadIndex(String text) {
    if (!TextUtils.isEmpty(text)) {
      return text.substring(0, 1).toUpperCase(Locale.getDefault());
    }
    return "#";
  }
}