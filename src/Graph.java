import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
  private Map<Movie, List<Actor>> movieActors;
  private Map<Actor, List<Movie>> actorMovies;
  private Map<String, Actor> nameActor;

  public Graph(Map<Movie, List<Actor>> movieActors, Map<Actor, List<Movie>> actorMovies,
      Map<String, Actor> nameActor) {
    super();
    this.movieActors = movieActors;
    this.actorMovies = actorMovies;
    this.nameActor = nameActor;
  }


  /**
   * method that calculate the shortest path (via BFS)
   * 
   * @param actorSrc The source actor
   * @param actorDest The destination actor
   * @param file output file
   * @throws CheminImpossibleException
   */
  public void calculerCheminLePlusCourt(String actorSrc, String actorDest, String file)
      throws CheminImpossibleException {
    Actor actSrc = nameActor.get(actorSrc);
    Actor actDest = nameActor.get(actorDest);

    Map<Actor, Actor> actorLinks = new HashMap<>();// destination --> source

    Set<Actor> alreadyCheckedActor = new HashSet<>(); // acteur déjà vérifié

    Deque<Actor> actorQueue = new ArrayDeque<>(); // pile d'acteur
    actorQueue.add(actSrc);
    alreadyCheckedActor.add(actSrc);

    Actor currentActor;

    while (!actorQueue.isEmpty() && alreadyCheckedActor.size() < this.actorMovies.size()) {
      currentActor = actorQueue.pollFirst();
      for (Movie m : actorMovies.get(currentActor)) {
        for (Actor a : movieActors.get(m)) {
          if (!alreadyCheckedActor.contains(a)) {

            if (a.equals(actDest)) { // si on a trouvé la destination
              Deque<Actor> resultHistory = new ArrayDeque<>();

              resultHistory.push(actDest);
              resultHistory.push(currentActor);

              Actor temp = currentActor;
              while (!temp.equals(actSrc)) {// tant qu'on est pas à la source
                temp = actorLinks.get(temp);// "bond" en arrière
                resultHistory.push(temp);
              }
              System.out.println(resultHistory.toString().replaceAll(",", " -->"));
              return;

            } else {
              actorQueue.add(a);
              alreadyCheckedActor.add(a);
              actorLinks.put(a, currentActor); // ajoute la destination --> source
            }
          }

        }
      }
    }

    throw new CheminImpossibleException();

  }

  /**
   * method that calculate the lowest cost path (via Dijkstra)
   * 
   * @param actorSrc The source actor
   * @param actorDest The destination actor
   * @param file output files
   */
  public void calculerCheminCoutMinimum(String actorSrc, String actorDest, String file) {

  }

}
