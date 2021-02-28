
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import restlight.Call;
import restlight.HttpUrlStack;
import restlight.Request;
import restlight.ResponseBody;
import restlight.Restlight;
import restlight.request.GsonRequest;

public final class WebService extends HttpUrlStack {
  
  private static Gson gson = new GsonBuilder()
            .serializeNulls()
            .create();
  
  private static Restlight restlight = new Restlight(
          new WebService(), 1);

  private WebService() {
  }

  public static <V> Call<V> newCall(Request request, Class<V> classOf) {
    return restlight.newCall(request, 
            newParseRequest(gson.getAdapter(classOf)));
  }
  
  public static <V> Call<V> newCall(Request request, TypeToken<V> type) {
    return restlight.newCall(request, 
            newParseRequest(gson.getAdapter(type)));
  }
  
  public static <V> GsonRequest<V> newParseRequest(TypeAdapter<V> adapter) {
    return new GsonRequest(gson, adapter) {
        
        @Override
        public Object parseResponse(ResponseBody response) throws Exception {
            if (response.code != 200) 
                throw new IOException("Response code " + response.code + ", " 
                        + response.string(getCharset()));
            
            return super.parseResponse(response);
        }
    };
  }
}