import java.util.HashMap;
import java.util.Map;

public class Movie {
  private static int idMovie = 1;
  private Map<String, Actor> actors = new HashMap<String, Actor>();
  private String name;
  private String year;
  private int id;

  public Movie() {
    super();
    this.id = idMovie++;
  }

  public String getName() {
    return name;
  }

  public String getYear() {
    return year;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public boolean isActorPresent(String actorId) {
    return actors.containsKey(actorId);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Movie other = (Movie) obj;
    if (id != other.id)
      return false;
    return true;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public void addActor(Actor actor) {
    actors.put(actor.getId(), actor);
  }



}
