import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {

  private Map<Movie, List<Actor>> moviesActors = new HashMap<>();// film --> acteur
  private Map<Actor, List<Movie>> actorsMovies = new HashMap<>();// actor --> film joué
  // nom-->acteur
  // id acteur


  private boolean bMovie;
  private Movie currentMovie;


  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    if (qName.equalsIgnoreCase("actor")) {
      String id = attributes.getValue("id");
      String name = attributes.getValue("name");
      actorsMovies.put(new Actor(id, name), new ArrayList<>());
    }

    if (qName.equalsIgnoreCase("movie")) {
      bMovie = true;
      currentMovie = new Movie();
      currentMovie.setYear(attributes.getValue("year"));
      moviesActors.put(currentMovie, new ArrayList<>());
      for (String a : attributes.getValue("actors").split(" ")) {
        Actor act = new Actor(a, "");
        moviesActors.get(currentMovie).add(act);
        actorsMovies.get(act).add(currentMovie);
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
    return null;
  }

  @Override
  public void endDocument() {
    System.out.println(actorsMovies.size());
    System.out.println(moviesActors.size());
  }

}
