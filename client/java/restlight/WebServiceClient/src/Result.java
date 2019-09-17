
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class Result<T> {

  @SerializedName("result")
  public T result;
  
  @SerializedName("success")
  public boolean success;
  
  @SerializedName("message")
  public String message;

  public static <V> Result<V> of(Gson gson, JsonObject json, Class<V> classOf) {
    Result<V> result = gson.fromJson(json, Result.class);
    result.result = gson.fromJson(json.get("result"), classOf); 
    return result; 
  }
  
  @Override public String toString() {
    return "Result{" + "result=" + result + ", success=" + success + ", message=" + message + '}';
  }
}
