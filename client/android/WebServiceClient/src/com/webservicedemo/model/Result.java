package com.webservicedemo.model;

import com.google.gson.annotations.SerializedName;

/**
 * Respuesta del servidor.
 * 
 * @author Jesus
 *
 * @param <T>
 */
public class Result<T> {

  @SerializedName("result")
  public T result;
  
  @SerializedName("success")
  public boolean success;
  
  @SerializedName("message")
  public String message;
  
  
  @Override public String toString() {
    return "Result{" + "result=" + result + ", success=" + success + ", message=" + message + '}';
  }
}