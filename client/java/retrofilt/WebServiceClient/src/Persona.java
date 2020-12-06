

import com.google.gson.annotations.SerializedName;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class Persona 
{
  @SerializedName("id")
  public long id;
  
  @SerializedName("nombre")
  public String nombre;
  
  @SerializedName("apellidos")
  public String apellidos;

  @Override public String toString() {
    return "Model{" + "id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + '}';
  }
  
  public interface Dao 
  {
    @Headers("Token: " + WebService.TOKEN)
    @GET("persona/index")
    Call<List<Persona>> all();
    
    @Headers("Token: " + WebService.TOKEN)
    @GET("persona/index")
    Call<List<Persona>> search(
            @Query("search") String search
    );

    @Headers("Token: " + WebService.TOKEN)
    @GET("persona/find")
    Call<Result<Persona>> findById(
            @Query("id") long id
    );

    @Headers("Token: " + WebService.TOKEN)
    @FormUrlEncoded
    @POST("persona/insert")
    Call<Result<Integer>> insert(
            @Field("nombre") String nombre,
            @Field("apellidos") String apellidos
    );

    @Headers("Token: " + WebService.TOKEN)
    @FormUrlEncoded
    @POST("persona/update")
    Call<Result<Integer>> update(
            @Field("nombre") String nombre,
            @Field("apellidos") String apellidos
    );

    @Headers("Token: " + WebService.TOKEN)
    @DELETE("persona/delete")
    Call<Result<Integer>> delete(
            @Query("id") long id
    );
  }
}
