

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebService {
  public static final String TOKEN = "hsjye87787dh86rucgvfvc8qdbdtcx478qixxfgxrncmaount45gioeansrqzxebrvibkk";
  private static WebService instance;
  private final Retrofit retrofit;

  private WebService() {
    Gson gson = new GsonBuilder()
        .serializeNulls()
        .create();

    retrofit = new Retrofit.Builder()
        .baseUrl("http://127.0.0.1/WebServiceDemo/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();
  }

  public static <T> T service(Class<T> service) {
    WebService ws = getInstance();
    return ws.retrofit.create(service);
  }
  
  public Retrofit api() {
    return retrofit;
  }

  public static WebService getInstance() {
    if (instance == null) {
      instance = new WebService();
    }
    return instance;
  }
}