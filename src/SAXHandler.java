import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {

  private List<Movie> movies = new ArrayList<>();
  private Map<String, Actor> actors = new HashMap<>();

  // actor --> film joué (??)
  // film --> acteur (??)

  private boolean bMovie;
  private Movie currentMovie;


  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    if (qName.equalsIgnoreCase("actor")) {
      String id = attributes.getValue("id");
      String name = attributes.getValue("name");
      actors.put(id, new Actor(id, name));
    }

    if (qName.equalsIgnoreCase("movie")) {
      bMovie = true;

      currentMovie = new Movie();
      currentMovie.setYear(attributes.getValue("year"));
      for (String a : attributes.getValue("actors").split(" ")) {
        currentMovie.addActor(actors.get(a));
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
      movies.add(currentMovie);
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
    System.out.println(actors.size());
    System.out.println(this.movies.size());
  }

}
