package com.webservicedemo;

import restlight.Call;
import restlight.Callback;
import restlight.Response;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.webservicedemo.dao.PersonaDao;
import com.webservicedemo.model.Persona;
import com.webservicedemo.widget.PersonaAdapter;

public class PersonaList extends Activity implements
        AdapterView.OnItemClickListener, View.OnClickListener {

  public static final int REQUEST_UPDATE = 1;
  private PersonaAdapter mAdapter;
  ListView list;

  /**
   * Crea las vistas.
   *
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);

    list = (ListView) findViewById(R.id.list);
    list.setEmptyView(findViewById(R.id.empty));
    list.setOnItemClickListener(this);

    View btnNueva = findViewById(R.id.add);
    btnNueva.setOnClickListener(this);
  }

  /**
   * Carga los datos en las vistas.
   *
   * @param savedInstanceState
   */
  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    mAdapter = new PersonaAdapter(this);
    list.setAdapter(mAdapter);

    if (!mAdapter.loadCache()) {
      refrescarLista();
    }
  }

  /**
   * Metodo para refrescar el ListView.
   */
  private void refrescarLista() {
    PersonaDao dao = PersonaDao.getInstance();
    Call<Persona[]> call = dao.getAll();

    call.queue(new Callback<Persona[]>() {
      @Override
      public void onResponse(Response<Persona[]> response) throws Exception {
        mAdapter.setAll(response.result());
      }
      @Override
      public void onErrorResponse(Exception e) {
        Dialog.showErrorWeb(PersonaList.this, e);
      }
    });
  }

  /**
   * Lanza un Intent para agregar una nueva nota.
   */
  @Override
  public void onClick(View v) {
    Intent i = new Intent(getApplicationContext(), PersonaActivity.class);
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    startActivityForResult(i, REQUEST_UPDATE);
  }

  /**
   * Valida que item de ListView se a seleccionado.
   */
  @Override
  public void onItemClick(AdapterView<?> l, View view, int position, long id) {
    Intent i = new Intent(getApplicationContext(), PersonaDetalles.class);
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
            | Intent.FLAG_ACTIVITY_SINGLE_TOP);

    final Persona o = (Persona) l.getItemAtPosition(position);
    i.putExtra("id", o.id);
    i.putExtra("nombre", o.nombre);
    i.putExtra("apellidos", o.apellidos);

    startActivityForResult(i, REQUEST_UPDATE);
  }

  /**
   * Resultado obtenido del activity lanzado por el metodo
   * {@link #startActivityForResult(Intent, int)}
   *
   * @param requestCode código para identificar la petición que se lanzo en el
   * metodo {@link #startActivityForResult(Intent, int)}
   *
   * @param resultCode código de respuesta que devolvió la actividad lanzada a
   * través de su metodo {@link #setResult(int)}
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        // Si se modifico o elimino una nota, se refresca la lista.
        case REQUEST_UPDATE:
          refrescarLista();
          break;
      }
    }
  }
}
