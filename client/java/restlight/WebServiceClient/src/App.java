import restlight.Call;

/**
 *
 * @author jesus
 */
public class App {
  
  Persona.Dao dao = new Persona.Dao();

  public Persona[] search(String search) throws Exception {
    Call<Persona[]> call = dao.search("");
    Persona[] result = call.execute();
    for (Persona model : result) {
      System.out.println(model);
    }
    return result;
  }
  
  public Persona findById(long id) throws Exception {
    Call<Result<Persona>> call = dao.findById(id);
    Result<Persona> result = call.execute();
    System.out.println("findById " + result);
    return result.result;
  }
  
  public int insert(String nombre, String apellidos) throws Exception {
    Call<Result<Integer>> call = dao.insert(nombre, apellidos);
    Result<Integer> result = call.execute();
    System.out.println("insert " + result);
    return result.result; 
  }

  public int delete(long id) throws Exception {
    Call<Result<Integer>> call = dao.delete(id);
    Result<Integer> result = call.execute();
    System.out.println("delete " + result);
    return result.result; 
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws Exception {
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
