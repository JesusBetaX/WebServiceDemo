package com.webservicedemo;

import restlight.Call;
import restlight.Callback;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.webservicedemo.model.Persona;
import com.webservicedemo.model.Result;
import com.webservicedemo.model.WebService;

public class PersonaDetalles extends Activity {

  long id;
  TextView nombre;
  TextView apellidos;
  restlight.widget.NetworkImageView image;
  Button btnEdit, btnDelete;

  /**
   * Crea las vistas.
   *
   * @param savedInstanceState
   */
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detalles);

    nombre = (TextView) findViewById(R.id.nombre);
    apellidos = (TextView) findViewById(R.id.apellidos);
    image = (restlight.widget.NetworkImageView) findViewById(R.id.image);

    btnEdit = (Button) findViewById(R.id.edit);
    btnEdit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(), PersonaActivity.class);
        i.putExtras(getIntent());
        startActivityForResult(i, PersonaList.REQUEST_UPDATE);
      }
    });

    btnDelete = (Button) findViewById(R.id.delete);
    btnDelete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showDialogDelete();
        ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(100);
      }
    });
  }

  /**
   * Carga los datos en las vistas.
   *
   * @param savedInstanceState
   */
  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    id = getIntent().getLongExtra("id", 0);
    nombre.setText(getIntent().getStringExtra("nombre"));
    apellidos.setText(getIntent().getStringExtra("apellidos"));

    Persona.Dao dao = new Persona.Dao();
    WebService service = WebService.getInstance();
    image.setImageUrl(dao.getUrlFoto(), service.imageLoader());
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
    setResult(resultCode, data);
    finish();
  }

  /**
   * Muestra un dialogo para confirmar la eliminacion.
   */
  private void showDialogDelete() {
    new AlertDialog.Builder(this)
            .setTitle("Eliminar")
            .setIcon(android.R.drawable.ic_delete)
            .setMessage("¡Desea eliminar este registro?")
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            	@Override
            	public void onClick(DialogInterface dialog, int id) {
            		delete();
            	}
            })
            .setNegativeButton(android.R.string.cancel, null)
            .create()
            .show();
  }

  /**
   * Elimina la nota.
   */
  private void delete() {
	  Persona.Dao dao = new Persona.Dao();
    Call<Result<Integer>> call = dao.delete(id);

    call.execute(new Callback<Result<Integer>>() {
      @Override
      public void onResponse(Result<Integer> result) throws Exception {
        if (result.success) {
          Toast.makeText(getApplicationContext(), "¡Ok Datos Borrados!", Toast.LENGTH_SHORT).show();
          setResult(RESULT_OK);
          finish();
        } else {
          Toast.makeText(getApplicationContext(), "¡No se pudo Borrar los datos!", Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Exception e) {
        Dialog.showErrorWeb(PersonaDetalles.this, e);
      }
    });
  }
}
