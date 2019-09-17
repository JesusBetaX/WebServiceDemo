package com.webservicedemo.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import restlight.Request;
import restlight.ResponseBody;
import restlight.Restlight;
import restlight.widget.ImageLoader;
import restlight.widget.LruImageCache;

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
	 * Gson Serialized.
	 */
	private final Gson gson = new GsonBuilder().setDateFormat("M/d/yy hh:mm a")
			.create();

	// TODO: Contructor

	private WebService() {
		restlight = Restlight.get();
		LruImageCache imageCache = new LruImageCache();
		imageLoader = new ImageLoader(restlight, imageCache);
	}

	// TODO: Funciones

	public <T> Request.Parse<T> gsonRequest(final Class<T> classOf) {
		return new Request.Parse<T>() {
			@Override
			public T parseResponse(ResponseBody response) throws Exception {
				// Reader json = response.charStream(getCharset());
				String json = response.string(getCharset());
				return fromJson(json, classOf);
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

	public String getUrl(String link) {
		return getHost() + link;
	}

	public Restlight restlight() {
		return restlight;
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

	public <V> V fromJson(String json, Class<V> classOf) {
		return gson.fromJson(json, classOf);
	}

	public String toJson(Object src) {
		return gson.toJson(src);
	}
}
