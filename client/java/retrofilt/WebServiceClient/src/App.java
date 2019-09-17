

import java.io.IOException;
import java.util.List;
import retrofit2.Call;

public class App {

  Persona.Dao dao = WebService.service(Persona.Dao.class);

  public List<Persona> search(String search) throws IOException {
    Call<List<Persona>> call = dao.search("");
    List<Persona> result = call.execute().body();
    for (Persona model : result) {
      System.out.println(model);
    }
    return result;
  }
  
  public Persona findById(long id) throws IOException {
    Call<Result<Persona>> call = dao.findById(id);
    Result<Persona> result = call.execute().body();
    System.out.println("findById " + result);
    return result.result;
  }
  
  public int insert(String nombre, String apellidos) throws IOException {
    Call<Result<Integer>> call = dao.insert(nombre, apellidos);
    Result<Integer> result = call.execute().body();
    System.out.println("insert " + result);
    return result.result; 
  }

  public int delete(long id) throws IOException {
    Call<Result<Integer>> call = dao.delete(id);
    Result<Integer> result = call.execute().body();
    System.out.println("delete " + result);
    return result.result; 
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws IOException {
    App obj = new App();
    
    int id = obj.insert("Lara", "Crop");
    System.out.println("---");
    
    Persona p = obj.findById(id);
    System.out.println("---");
    
    obj.delete(p.id);
    System.out.println("---");
    
    p = obj.findById(p.id);
    System.out.println("---");
    
    obj.search("");
  }
}
