package com.webservicedemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public final class Dialog {

  private Dialog() {
    // TODO Auto-generated constructor stub
  }

  public static void showErrorWeb(final Activity context, Exception e) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("Error").setCancelable(false);

    if (e instanceof java.net.SocketException) {
      builder.setMessage("En estos momentos no podemos conectarnos para "
              + "realizar la búsqueda. Comprueba que tengas conexión de red.");
      builder.setPositiveButton("Ir a ajustes",
              new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int witch) {
                  context.startActivity(new Intent(
                          android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                }
              });

    } else if (e instanceof java.net.SocketTimeoutException) {
      builder.setMessage("Tiempo de espera agotado. Intente de nuevo.");

    } else if (e instanceof org.json.JSONException) {
      builder.setMessage("El servidor no a respondido correctamente.");

    } else {
      builder.setMessage(e.getMessage());
    }

    builder.setNeutralButton(android.R.string.ok,
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
              }
            });

    builder.create().show();
  }
}
