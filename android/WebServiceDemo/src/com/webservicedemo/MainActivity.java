package com.webservicedemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.webservicedemo.dao.WebService;

public class MainActivity extends Activity {

  EditText host;
  Button btnOk;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    host = (EditText) findViewById(R.id.host);

    btnOk = (Button) findViewById(R.id.ok);
    btnOk.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("host", host.getText().toString());
        editor.commit();

        WebService service = WebService.getInstance();
        service.setHost(host.getText().toString());

        Intent i = new Intent(getApplicationContext(), PersonaList.class);
        startActivity(i);
        finish();
      }
    });
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    WebService service = WebService.getInstance();

    SharedPreferences sp = getPreferences(MODE_PRIVATE);
    host.setText(sp.getString("host", service.getHost()));
  }
}