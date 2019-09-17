
import com.google.gson.annotations.SerializedName;
import restlight.Call;
import restlight.FormBody;
import restlight.HttpUrl;
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
      Request request = new Request();
      request.setMethod("GET");
      request.setUrl(new HttpUrl("http://127.0.0.1/WebServiceDemo/persona/index")
              .addQueryParameter("search", search) // ?search={search}
      );
      return WebService.newCall(request, Persona[].class);
    }

    /**
     * Obtiene un registro por su id.
     *
     * @param id
     * @return Call
     */
    public Call<Result<Persona>> findById(long id) {
      Request request = new Request();
      request.setMethod("GET");
      request.setUrl(new HttpUrl("http://127.0.0.1/WebServiceDemo/persona/find")
              .addQueryParameter("id", id) // ?id={id}
      );
      return WebService.newCallResult(request, Persona.class);
    }

    /**
     * Inserta un registro.
     *
     * @param nombre
     * @param apellidos
     * @return Call
     */
    public Call<Result<Integer>> insert(String nombre, String apellidos) {
      Request request = new Request();
      request.setMethod("POST");
      request.setUrl("http://127.0.0.1/WebServiceDemo/persona/insert");

      request.setBody(new FormBody()
              .add("nombre", nombre)
              .add("apellidos", apellidos));

      return WebService.newCallResult(request, Integer.class);
    }

    /**
     * Modifica un registro.
     *
     * @param nombre
     * @param apellidos
     * @return Call
     */
    public Call<Result<Integer>> update(int id, String nombre, String apellidos) {
      Request request = new Request();
      request.setMethod("POST");
      request.setUrl("http://127.0.0.1/WebServiceDemo/persona/update");

      request.setBody(new FormBody()
              .add("id", id)
              .add("nombre", nombre)
              .add("apellidos", apellidos));

      return WebService.newCallResult(request, Integer.class);
    }

    /**
     * Elimina un registro
     *
     * @param id
     * @return Call
     */
    public Call<Result<Integer>> delete(long id) {
      Request request = new Request();
      request.setMethod("DELETE");
      request.setUrl(new HttpUrl("http://127.0.0.1/WebServiceDemo/persona/delete")
              .addQueryParameter("id", id) // ?id={id}
      );
      return WebService.newCallResult(request, Integer.class);
    }

    
  }
}
