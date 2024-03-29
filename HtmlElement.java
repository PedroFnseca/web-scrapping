public class HtmlElement {
  private int depth;
  private String content;

  public HtmlElement(int depth, String content) {
    this.depth = depth;
    this.content = content;
  }

  public int getDepth() {
    return depth;
  }

  public void setDepth(int depth) {
    this.depth = depth;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}