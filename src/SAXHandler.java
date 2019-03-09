import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {

  private Map<Movie, List<Actor>> movieActors = new HashMap<>();// film --> acteur
  private Map<Actor, List<Movie>> actorMovies = new HashMap<>();// actor --> film joué
  private Map<String, Actor> nameActor = new HashMap<>();// nomActeur-->acteur
  private Map<String, Actor> idActor = new HashMap<>();

  private boolean bMovie;
  private Movie currentMovie;

  private Graph graph;


  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    if (qName.equalsIgnoreCase("actor")) {
      String id = attributes.getValue("id");
      String name = attributes.getValue("name");
      Actor act = new Actor(id, name);
      actorMovies.put(act, new ArrayList<>());
      nameActor.put(name, act);
      idActor.put(id, act);
    }

    if (qName.equalsIgnoreCase("movie")) {
      bMovie = true;
      currentMovie = new Movie();
      currentMovie.setYear(attributes.getValue("year"));
      movieActors.put(currentMovie, new ArrayList<>());
      for (String a : attributes.getValue("actors").split(" ")) {
        Actor act = idActor.get(a);
        movieActors.get(currentMovie).add(act);
        actorMovies.get(act).add(currentMovie);
      }
    }

  }



  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    if (bMovie) {
      currentMovie.setName(new String(ch, start, length));
    }
  }



  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (bMovie) {
      bMovie = false;
    }
  }



  /**
   * 
   * @return a graph build by the handler
   */
  public Graph getGraph() {
    return graph;
  }

  @Override
  public void endDocument() {
    graph = new Graph(movieActors, actorMovies, nameActor);
  }

}
