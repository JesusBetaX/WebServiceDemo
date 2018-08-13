package com.webservicedemo.model;

import com.google.gson.annotations.SerializedName;

public class Persona {

  @SerializedName("id")
  public long id;
  
  @SerializedName("nombre")
  public String nombre;
  
  @SerializedName("apellidos")
  public String apellidos;
}