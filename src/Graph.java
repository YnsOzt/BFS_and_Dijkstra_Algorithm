import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
   * method that calculate the shortest path (via BFS) and write the path in an XML file
   * 
   * @param actorSrc The source actor
   * @param actorDest The destination actor
   * @param file output file
   * @throws CheminImpossibleException
   */
  public void calculerCheminLePlusCourt(String actorSrc, String actorDest, String file)
      throws CheminImpossibleException {
    Deque<PairActorMovie> shortestPath = calculerDistance(nameActor.get(actorSrc), nameActor.get(actorDest));

    writeXMLFile(file, shortestPath);
  }


  /**
   * 
   * @param actorSrc The source actor
   * @param actorDest The destination actor
   * @return The shortest path computed via BFS
   * @throws CheminImpossibleException
   */
  private Deque<PairActorMovie> calculerDistance(Actor actSrc, Actor actDest)
      throws CheminImpossibleException {

    Map<Actor, PairActorMovie> actorMovieLinks = new HashMap<>();
    Set<Actor> alreadyCheckedActor = new HashSet<>(); // acteur déjà vérifié
    Deque<Actor> actorQueue = new ArrayDeque<>(); // pile d'acteur
    actorQueue.add(actSrc);
    alreadyCheckedActor.add(actSrc);

    Actor currentActor;

    while (!actorQueue.isEmpty()) {
      currentActor = actorQueue.pollFirst();
      for (Movie m : actorMovies.get(currentActor)) {
        for (Actor a : movieActors.get(m)) {
          if (!alreadyCheckedActor.contains(a)) {// si pas encore checker
            // ajoute le film en relation avec les deux acteurs
            actorMovieLinks.put(a, new PairActorMovie(currentActor, m));

            if (a.equals(actDest)) { // si on a trouvé la destination
              return extractShortestPath(actSrc, actDest, actorMovieLinks);
            } else {
              actorQueue.add(a);
              alreadyCheckedActor.add(a);
            }
          }
        }
      }
    }
    throw new CheminImpossibleException();
  }

  /**
   * Method that extract the shortestPath from a map of pair and put it in a stack.
   * 
   * @param actorSrc The source actor
   * @param actorDest The destination actor
   * @param actorMovieLinks the links created between the actors
   * @return the shortestPath found by the links
   */
  private Deque<PairActorMovie> extractShortestPath(Actor actSrc, Actor actDest,
      Map<Actor, PairActorMovie> actorMovieLinks) {

    Deque<PairActorMovie> shortestPath = new ArrayDeque<PairActorMovie>();
    shortestPath.push(new PairActorMovie(actDest, null)); // push la destination
    Actor currentActor = actDest;
    while (currentActor != actSrc) {
      shortestPath.push(actorMovieLinks.get(currentActor));
      currentActor = actorMovieLinks.get(currentActor).getActor();
    }
    return shortestPath;
  }

  /**
   * method that write a path in an xml file
   * 
   * @param file name of the file
   * @param shortestPath the shortest path
   */
  private void writeXMLFile(String file, Deque<PairActorMovie> shortestPath) {
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.newDocument();

      Element rootElement = doc.createElement("path");
      doc.appendChild(rootElement);

      Element actor;
      Element movie;

      int cost = 0;
      int nbMovie = shortestPath.size() - 1;

      for (PairActorMovie p : shortestPath) {
        // ajout acteur
        actor = doc.createElement("actor");
        actor.appendChild(doc.createTextNode(p.getActor().getName()));
        rootElement.appendChild(actor);

        // ajout film
        if (p.getMovie() != null) {
          movie = doc.createElement("movie");
          movie.setAttribute("name", p.getMovie().getName());
          movie.setAttribute("year", p.getMovie().getYear());
          rootElement.appendChild(movie);
          cost += movieActors.get(p.getMovie()).size();
        }
      }

      rootElement.setAttribute("cost", String.valueOf(cost));
      rootElement.setAttribute("nbMovies", String.valueOf(nbMovie));

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(file));
      transformer.transform(source, result);
    } catch (Exception e) {
      e.printStackTrace();
    }


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
