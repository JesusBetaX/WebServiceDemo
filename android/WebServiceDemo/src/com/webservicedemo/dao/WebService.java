package com.webservicedemo.dao;

import java.io.Reader;

import restlight.Request;
import restlight.ResponseBody;
import restlight.Restlight;
import restlight.widget.ImageLoader;
import restlight.widget.LruImageCache;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WebService {
// TODO: Variables
	
  private static WebService instance;
  /**
   * API para WebService.
   */
  private final Restlight restlight;
  /**
   * Centro de carga pra imagnes.
   */
  private final ImageLoader imageLoader;
  /**
   * Host de nuestro web service.
   */
  private String host = "http://10.0.0.2/";
  /**
   * Json Serialized.
   */
  private final Gson gson = new GsonBuilder()
  		.setDateFormat("M/d/yy hh:mm a")
		  .create();
  
//TODO: Contructor
  
  private WebService() {
    restlight = Restlight.getInstance();
    LruImageCache imageCache = new LruImageCache();
    imageLoader = new ImageLoader(restlight.getQueue(), imageCache);
  }
  
//TODO: Funciones
  
  public <T> Request<T> request(final Class<T> classOf) {
    return new Request<T>() {
	    @Override
	    public T parseResponse(ResponseBody response) throws Exception {
	      //Reader json = response.charStream(getCharset());
	      String json = response.string(getCharset());
	      return gson.fromJson(json, classOf);
	    }
	  };
  }
  
  public WebService setHost(String host) {
    this.host = host;
    return this;
  }
  public String getHost() {
    return host;
  }

  public Restlight restlight() {
    return restlight;
  }
  
  public Gson getGson() {
	return gson;
  }

  public ImageLoader getImageLoader() {
    return imageLoader;
  }

  public static WebService getInstance() {
    if (instance == null) {
      instance = new WebService();
    }
    return instance;
  }
}
