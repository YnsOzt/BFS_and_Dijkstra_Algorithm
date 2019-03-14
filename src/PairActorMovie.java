public class PairActorMovie {

  private final Actor actor;
  private final Movie right;

  public PairActorMovie(Actor actor, Movie movie) {
    this.actor = actor;
    this.right = movie;
  }

  public Actor getActor() {
    return actor;
  }

  public Movie getMovie() {
    return right;
  }
}
