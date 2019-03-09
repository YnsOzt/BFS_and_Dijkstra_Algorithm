public class Movie {
  private static int idMovie = 1;
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

  @Override
  public String toString() {
    return name;
  }


}
