package com.webservicedemo.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import restlight.Call;
import restlight.Request;
import restlight.Restlight;
import restlight.request.GsonRequest;
import restlight.widget.ImageLoader;
import restlight.widget.LruImageCache;

public class WebService {
	private static WebService instance;

	/** API para WebService. */
	private final Restlight restlight;

	/** Centro de carga pra imagnes.*/
	private final ImageLoader imageLoader;

	/** Host de nuestro web service. */
	private String host = "http://10.0.0.2/";

	/** Gson Serialized.*/
	private final Gson gson;

	
	private WebService() {
		gson = new GsonBuilder()
			.serializeNulls()
			.create();
		
		restlight = Restlight.get();
		LruImageCache imageCache = new LruImageCache();
		imageLoader = new ImageLoader(restlight, imageCache);
	}

	// TODO: Funciones

	public static <V> Call<V> newCall(Request request, Class<V> classOf) {
		WebService api = getInstance();
	    GsonRequest<V> parse = GsonRequest.of(api.gson, classOf);
	    return api.restlight.newCall(request, parse);
	}
	 
	public static <V> Call<V> newCall(Request request, TypeToken<V> token) {
	    WebService api = getInstance();
	    GsonRequest<V> parse = GsonRequest.of(api.gson, token);
	    return api.restlight.newCall(request, parse);
	}

	public static String url(String link) {
		return getInstance().getHost() + link;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	public String getHost() {
		return host;
	}

	public Restlight restlight() {
		return restlight;
	}

	public ImageLoader imageLoader() {
		return imageLoader;
	}

	public static WebService getInstance() {
		if (instance == null) {
			instance = new WebService();
		}
		return instance;
	}

	public <V> V fromJson(String json, Class<V> classOf) {
		return gson.fromJson(json, classOf);
	}

	public String toJson(Object src) {
		return gson.toJson(src);
	}
}
