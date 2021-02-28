
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import restlight.Call;
import restlight.FormBody;
import restlight.Request;

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

  public static class Dao 
  {
    /**
     * Obtiene todo los registros.
     *
     * @return Call
     */
    public Call<Persona[]> search(String search) {
      FormBody body = new FormBody()
            .add("search", search); // ?search={search}
      
      Request request = new Request(
              "GET", "http://127.0.0.1/WebServiceDemo/persona/index", body);
      
      return WebService.newCall(request, Persona[].class);
    }

    /**
     * Obtiene un registro por su id.
     *
     * @param id
     * @return Call
     */
    public Call<Result<Persona>> findById(long id) {
      FormBody body = new FormBody()
            .add("id", id); // ?id={id}
        
      Request request = new Request(
              "GET", "http://127.0.0.1/WebServiceDemo/persona/find", body);
      
      return WebService.newCall(request, new TypeToken< Result<Persona> >(){});
    }

    /**
     * Inserta un registro.
     *
     * @param nombre
     * @param apellidos
     * @return Call
     */
    public Call<Result<Integer>> insert(String nombre, String apellidos) {
      FormBody body = new FormBody()
            .add("nombre", nombre)
            .add("apellidos", apellidos);
        
      Request request = new Request(
              "POST", "http://127.0.0.1/WebServiceDemo/persona/insert", body);

      return WebService.newCall(request, new TypeToken< Result<Integer> >(){});
    }

    /**
     * Modifica un registro.
     *
     * @param nombre
     * @param apellidos
     * @return Call
     */
    public Call<Result<Integer>> update(int id, String nombre, String apellidos) {
      FormBody body = new FormBody()
            .add("id", id)
            .add("nombre", nombre)
            .add("apellidos", apellidos);
        
      Request request = new Request(
              "PUT", "http://127.0.0.1/WebServiceDemo/persona/update", body);

      return WebService.newCall(request, new TypeToken< Result<Integer> >(){});
    }

    /**
     * Elimina un registro
     *
     * @param id
     * @return Call
     */
    public Call<Result<Integer>> delete(long id) {
      FormBody body = new FormBody()
            .add("id", id); // ?id={id}
      
      Request request = new Request(
              "DELETE", "http://127.0.0.1/WebServiceDemo/persona/delete", body);
      
      return WebService.newCall(request, new TypeToken< Result<Integer> >(){});
    }

    
  }
}
