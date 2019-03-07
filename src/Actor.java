import java.util.HashMap;
import java.util.Map;

public class Actor {

  private String id;
  private String name;
  private Map<Integer, Movie> moviesPlayed = new HashMap<>();


  public Actor(String id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Actor other = (Actor) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Actor [id=" + id + ", name=" + name + "]";
  }

  public void addMovie(Movie movie) {
    moviesPlayed.put(movie.getId(), movie);
  }


}
