package com.webservicedemo.dao;

import restlight.Call;
import restlight.HttpUrl;
import restlight.Request;
import restlight.FormBody;

import com.webservicedemo.model.Persona;
import com.webservicedemo.model.Response;

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
    String url = service.getUrl("WebServiceDemo/persona/index");
    
    Request request = new Request();
	request.setMethod("GET");
	request.setUrl(url);
    
    return newCall(request, Persona[].class);
  }

  /**
   * Obtiene un registro por su id.
   *
   * @param id
   * @return Call
   */
  public Call<Persona> findById(int id) {
    HttpUrl url = new HttpUrl( service.getUrl("WebServiceDemo/persona/find") )
        .addQueryParameter("id", id);
	  
	Request request = new Request();
	request.setMethod("GET");
	request.setUrl(url);
   
    return newCall(request, Persona.class);
  }

  public Call<Response> save(Persona obj) {
    return (obj.id > 0) ? update(obj) : insert(obj);
  }

  /**
   * Inserta un registro.
   *
   * @param obj
   * @return Call
   */
  public Call<Response> insert(Persona obj) {
    String url = service.getUrl("WebServiceDemo/persona/insert");
    
    Request request = new Request();
    request.setMethod("POST");
    request.setUrl(url);
    
    request.setBody(new FormBody()
    	.add("nombre", obj.nombre)
    	.add("apellidos", obj.apellidos)
	);

    return newCall(request, Response.class);
  }

  /**
   * Modifica un registro.
   *
   * @param obj
   * @return Call
   */
  public Call<Response> update(Persona obj) {  
    String url = service.getUrl("WebServiceDemo/persona/update");
    
    Request request = new Request();
    request.setMethod("POST");
    request.setUrl(url);
    
    request.setBody(new FormBody()
		.add("id", obj.id)
		.add("nombre", obj.nombre)
		.add("apellidos", obj.apellidos)
	);

    return newCall(request, Response.class);
  }

  /**
   * Elimina un registro
   *
   * @param id
   * @return Call
   */
  public Call<Response> delete(long id) {
	HttpUrl url = new HttpUrl( service.getUrl("WebServiceDemo/persona/delete") )
	    .addQueryParameter("id", id);

    Request request = new Request();
    request.setMethod("DELETE");
    request.setUrl(url);
    
    return newCall(request, Response.class);
  }

  public String getUrlFoto() {
    return service.getUrl("WebServiceDemo/images.jpg");
  }

  /**
   * Metodo para crear una invocación de una request.
   *
   * @param request
   * @return
   */
  private <T> Call<T> newCall(Request request, Class<T> classOf) {
    // [Opcional] Cancela todas la request que tengan esta TAG
    service.restlight().cancelAll(TAG);
    request.setTag(TAG);
    
    // Crea un convertidor tipo GSON para la petición.
    Request.Parse<T> parse = service.gsonRequest(classOf);
    
    // Crea una invocacion.
    return service.restlight().newCall(request, parse);
  }
  

  public static PersonaDao getInstance() {
    if (instance == null) {
      instance = new PersonaDao();
    }
    return instance;
  }
}
