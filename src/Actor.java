public class Actor implements Comparable<Actor> {

  private String id;
  private String name;
  private int cost;


  public Actor(String id, String name) {
    super();
    this.id = id;
    this.name = name;
    this.cost = 0;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
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
    return name;
  }

  @Override
  public int compareTo(Actor a) {
    int delta = this.getCost() - a.getCost();
    if (delta == 0)
      return this.getId().compareTo(a.getId());
    else
      return delta;
  }


}
