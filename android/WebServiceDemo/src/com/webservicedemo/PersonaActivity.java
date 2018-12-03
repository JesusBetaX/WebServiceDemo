package com.webservicedemo;

import restlight.Call;
import restlight.Callback;
import restlight.Response;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.webservicedemo.dao.PersonaDao;
import com.webservicedemo.model.ServerResponse;
import com.webservicedemo.model.Persona;

public class PersonaActivity extends Activity {

  long id;
  TextView nombre;
  TextView apellidos;
  Button save, cancel;

  /**
   * Crea las vistas.
   *
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_form);

    nombre = (TextView) findViewById(R.id.nombre);
    apellidos = (TextView) findViewById(R.id.apellidos);

    save = (Button) findViewById(R.id.save);
    save.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        guardar();
      }
    });

    cancel = (Button) findViewById(R.id.cancel);
    cancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        setResult(RESULT_CANCELED);
        finish();
      }
    });
  }

  /**
   * Guarda la nota en la base de datos.
   */
  private void guardar() {
    Persona obj = new Persona();
    obj.id = id;
    obj.nombre = nombre.getText().toString();
    obj.apellidos = apellidos.getText().toString();

    PersonaDao dao = PersonaDao.getInstance();
    Call<ServerResponse> call = dao.save(obj);

    call.queue(new Callback<ServerResponse>() {
      @Override
      public void onResponse(Response<ServerResponse> response) throws Exception {
        if (response.result().success) {
          Toast.makeText(getApplicationContext(), "¡Ok Datos Guardados!",
                  Toast.LENGTH_SHORT).show();
          setResult(RESULT_OK);
          finish();
        } else {
          Toast.makeText(getApplicationContext(),
                  "¡No se pudo guardar los datos!", Toast.LENGTH_SHORT)
                  .show();
        }
      }
      @Override
      public void onErrorResponse(Exception e) {
        Dialog.showErrorWeb(PersonaActivity.this, e);
      }
    });
  }

  /**
   * Carga los datos recibidos por el intent
   *
   * @param savedInstanceState
   */
  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    Bundle extras = savedInstanceState;
    if (extras == null) {
      extras = getIntent().getExtras();
    }
    onRestoreInstanceState((extras == null) ? new Bundle() : extras);
  }

  /**
   * Se restauran los datos que se guardaron en
   * {@link #onSaveInstanceState(Bundle)}
   */
  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    id = savedInstanceState.getLong("id", 0);
    nombre.setText(savedInstanceState.getCharSequence("nombre"));
    apellidos.setText(savedInstanceState.getCharSequence("apellidos"));
  }

  /**
   * Cuando se gira la pantalla se guardan los datos de los componentes
   */
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    outState.putLong("id", id);
    outState.putCharSequence("nombre", nombre.getText());
    outState.putCharSequence("apellidos", apellidos.getText());
    super.onSaveInstanceState(outState);
  }
}
