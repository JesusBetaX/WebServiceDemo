
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import restlight.Call;
import restlight.HttpUrlStack;
import restlight.Request;
import restlight.Restlight;
import restlight.request.GsonRequest;

public class WebService {
  private static WebService instance;
  
  private Gson gson;
  private Restlight restlight;
  
  private WebService() {
    gson = new GsonBuilder()
            .serializeNulls()
            .create();
    
    restlight = new Restlight(new HttpUrlStack(), 1);
  }

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

  public static WebService getInstance() {
    if (instance == null) {
      instance = new WebService();
    }
    return instance;
  }
}