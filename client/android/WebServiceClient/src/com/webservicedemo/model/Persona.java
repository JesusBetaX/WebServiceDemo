package com.webservicedemo.model;

import restlight.Call;
import restlight.FormBody;
import restlight.HttpUrl;
import restlight.Request;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

/**
 * Modelo
 * 
 * @author Jesus
 */
public class Persona 
{	
  @SerializedName("id")
  public long id;
	
  @SerializedName("nombre")
  public String nombre;
	
  @SerializedName("apellidos")
  public String apellidos;
 
  
  @Override public String toString() {
    return "Model{" + "id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + '}';
  }
  
  /**
   * Origen de datos
   * 
   * @author Jesus
   */
  public static class Dao 
  {  
	/**
	 * Obtiene todo los registros.
	 *
	 * @return Call
	 */
	public Call<Persona[]> getAll() {
	  String url = WebService.url("WebServiceDemo/persona/index");
	    
	  Request request = new Request();
      request.setMethod("GET");
	  request.setUrl(url);
	    
	  return WebService.newCall(request, Persona[].class);
	}

	/**
	 * Obtiene un registro por su id.
	 *
	 * @param id
	 * @return Call
	 */
	public Call<Result<Persona>> findById(int id) {
	  HttpUrl url = new HttpUrl( WebService.url("WebServiceDemo/persona/find") )
	        .addQueryParameter("id", id);
		  
      Request request = new Request();
	  request.setMethod("GET");
	  request.setUrl(url);
	   
	  return WebService.newCall(request, new TypeToken< Result<Persona> >(){});
	}

	public Call<Result<Integer>> save(Persona obj) {
	  return (obj.id > 0) ? update(obj) : insert(obj);
	}

	/**
	 * Inserta un registro.
	 *
	 * @param obj
	 * @return Call
	 */
	public Call<Result<Integer>> insert(Persona obj) {
	  String url = WebService.url("WebServiceDemo/persona/insert");
	    
	  Request request = new Request();
	  request.setMethod("POST");
	  request.setUrl(url);
	    
	  request.setBody(new FormBody()
	    	.add("nombre", obj.nombre)
	    	.add("apellidos", obj.apellidos)
      );

	  return WebService.newCall(request, new TypeToken< Result<Integer> >(){});
	}

	/**
	 * Modifica un registro.
	 *
	 * @param obj
	 * @return Call
	 */
	public Call<Result<Integer>> update(Persona obj) {  
	  String url = WebService.url("WebServiceDemo/persona/update");
	    
	  Request request = new Request();
	  request.setMethod("POST");
	  request.setUrl(url);
	    
	  request.setBody(new FormBody()
			.add("id", obj.id)
			.add("nombre", obj.nombre)
			.add("apellidos", obj.apellidos)
	  );

	  return WebService.newCall(request, new TypeToken< Result<Integer> >(){});
	}

	/**
	 * Elimina un registro
	 *
	 * @param id
	 * @return Call
	 */
	public Call<Result<Integer>> delete(long id) {
	  HttpUrl url = new HttpUrl( WebService.url("WebServiceDemo/persona/delete") )
		    .addQueryParameter("id", id);

	  Request request = new Request();
	  request.setMethod("DELETE");
	  request.setUrl(url);
	    
	  return WebService.newCall(request, new TypeToken< Result<Integer> >(){});
	}

	public String getUrlFoto() {
	  return WebService.url("WebServiceDemo/images.jpg");
	}
  }
}