import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class HtmlAnalyzer {
  private URL url;

  public HtmlAnalyzer(String urlString) throws Exception {
    this.url = new URL(urlString);
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.out.println("Usage: java HtmlAnalyzer <URL>");
      return;
    }

    HtmlAnalyzer analyzer = new HtmlAnalyzer(args[0]);
    analyzer.analyze();
  }

  public void analyze() {
    try (InputStream is = this.url.openStream(); Scanner scanner = new Scanner(is)) {
      scanner.useDelimiter("\\A");

      while (scanner.hasNext()) {
        String html = scanner.next();

        List<HtmlElement> htmlElements = catalogHtmlElements(html);

        if (!validateHtml(htmlElements)) {
          System.out.println("malformed HTML");

          return;
        }

        HtmlElement depthElement = getDepthElement(htmlElements);

        System.out.println(depthElement.getContent());
      }
    } catch (Exception e) {
      System.out.println("URL connection error");
    }
  }

  private List<HtmlElement> catalogHtmlElements(String html) {
    String[] lines = html.split("\n");
    List<HtmlElement> elements = new ArrayList<>();

    int depth = 0;
    for (int i = 0; i < lines.length; i++) {
      String line = lines[i].trim();

      if (line.startsWith("<") && !line.startsWith("</")) {
        elements.add(new HtmlElement(depth, line));
        depth++;
      } else if (line.startsWith("</")) {
        depth--;
        elements.add(new HtmlElement(depth, line));
      } else {
        elements.add(new HtmlElement(depth, line));
      }
    }

    return elements;
  }

  private boolean validateHtml(List<HtmlElement> elements) {
    Stack<String> openTags = new Stack<>();

    for (HtmlElement element : elements) {
      String content = element.getContent();

      if (content.startsWith("<") && !content.startsWith("</")) {
        openTags.push(content);
      } else if (content.startsWith("</")) {
        if (openTags.isEmpty() || !isMatchingTag(openTags.peek(), content)) {
          return false;
        }
        openTags.pop();
      }
    }

    return openTags.isEmpty();
  }

  private boolean isMatchingTag(String openingTag, String closingTag) {
    String openingTagName = openingTag.substring(1);
    String closingTagName = closingTag.substring(2);

    return openingTagName.equals(closingTagName);
  }

  private HtmlElement getDepthElement(List<HtmlElement> elements) {
    HtmlElement depthElement = null;

    for (HtmlElement element : elements) {
      if (depthElement == null || element.getDepth() > depthElement.getDepth()) {
        depthElement = element;
      }
    }

    return depthElement;
  }
}