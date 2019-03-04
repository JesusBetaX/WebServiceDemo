package com.webservicedemo.dao;

import restlight.Call;
import restlight.HttpUrl;
import restlight.Request;
import restlight.FormBody;

import com.webservicedemo.model.ServerResponse;
import com.webservicedemo.model.Persona;

public class PersonaDao {
  public static final String TAG = "PersonaDao";
  
  private static PersonaDao instance;
  private final WebService service;

  public PersonaDao() {
    service = WebService.getInstance();
  }

  /**
   * Obtiene todo los registros.
   *
   * @return Call
   */
  public Call<Persona[]> getAll() {
    String url = service.getHost() + "WebServiceDemo/persona/index";
    Request.Parse<Persona[]> request = service.request(Persona[].class);
	request.setMethod("GET");
	request.setUrl(url);
    
    return newCall(request);
  }

  /**
   * Obtiene un registro por su id.
   *
   * @param id
   * @return Call
   */
  public Call<Persona> findById(int id) {
	HttpUrl url = new HttpUrl()
		.setUrl(service.getHost() + "WebServiceDemo/persona/find")
        .addQueryParameter("id", id);
	  
	Request.Parse<Persona> request = service.request(Persona.class);
	request.setMethod("GET");
	request.setUrl(url);
   
    return newCall(request);
  }

  public Call<ServerResponse> save(Persona obj) {
    if (obj.id > 0) {
      return update(obj);
    } else {
      return insert(obj);
    }
  }

  /**
   * Inserta un registro.
   *
   * @param obj
   * @return Call
   */
  public Call<ServerResponse> insert(Persona obj) {
    FormBody body = new FormBody()
	    .add("nombre", obj.nombre)
	    .add("apellidos", obj.apellidos);
	  
    String url = service.getHost() + "WebServiceDemo/persona/insert";
    Request.Parse<ServerResponse> request = service.request(ServerResponse.class);
    request .setMethod("POST");
    request.setUrl(url);
    request.setBody(body);

    return newCall(request);
  }

  /**
   * Modifica un registro.
   *
   * @param obj
   * @return Call
   */
  public Call<ServerResponse> update(Persona obj) {
	FormBody body = new FormBody()
	    .add("id", obj.id)
	    .add("nombre", obj.nombre)
	    .add("apellidos", obj.apellidos);
	  
    String url = service.getHost() + "WebServiceDemo/persona/update";
    Request.Parse<ServerResponse> request = service.request(ServerResponse.class);
    request.setMethod("POST");
    request.setUrl(url);
    request.setBody(body);

    return newCall(request);
  }

  /**
   * Elimina un registro
   *
   * @param id
   * @return Call
   */
  public Call<ServerResponse> delete(long id) {
	HttpUrl url = new HttpUrl()
		.setUrl(service.getHost() + "WebServiceDemo/persona/delete")
	    .addQueryParameter("id", id);

    Request.Parse<ServerResponse> request = service.request(ServerResponse.class);
    request.setMethod("DELETE");
    request.setUrl(url);
    
    return newCall(request);
  }

  public String getUrlFoto() {
    String url = service.getHost() + "WebServiceDemo/images.jpg";
    return url;
  }

  /**
   * Metodo para crear una invocación de una request.
   *
   * @param request
   * @return
   */
  private <T> Call<T> newCall(Request.Parse<T> request) {
    // [Opcional] Cancela todas la request que tengan esta TAG
    service.restlight().getQueue().cancelAll(TAG);
    request.setTag(TAG);
    // Crea una invocacion.
    return service.restlight().newCall(request);
  }

  public static PersonaDao getInstance() {
    if (instance == null) {
      instance = new PersonaDao();
    }
    return instance;
  }
}
