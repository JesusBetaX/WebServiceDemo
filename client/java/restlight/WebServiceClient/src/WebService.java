
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.Reader;
import restlight.Call;
import restlight.HttpUrlStack;
import restlight.Request;
import restlight.ResponseBody;
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
  
  public static <V> Call<Result<V>> newCallResult(Request request, final Class<V> classOf) {
    final WebService api = getInstance();
    return api.restlight.newCall(request, new RequestResult<V>(api.gson, classOf));
  }

  public static WebService getInstance() {
    if (instance == null) {
      instance = new WebService();
    }
    return instance;
  }
  
  public static class RequestResult<T> extends Request.Parse<Result<T>> {
    final Gson gson;
    final Class<T> classOf;

    public RequestResult(Gson gson, Class<T> classOf) {
      this.gson = gson;
      this.classOf = classOf;
    }
    
    @Override public Result<T> parseResponse(ResponseBody rb) throws Exception {
      Reader json = rb.charStream(getCharset());
      JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
      return Result.of(gson, jsonObject, classOf);
    }
  }
}