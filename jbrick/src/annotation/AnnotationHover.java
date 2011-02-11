package annotation;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;

//annotation hover manager
public class AnnotationHover implements IAnnotationHover, ITextHover {

  public String getHoverInfo(ISourceViewer sourceViewer, int lineNumber) {
    System.out.println("line Number is: " + lineNumber);
    return "test string";

  }

  public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
    System.out
        .println("String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) ");
    if (hoverRegion != null) {
      try {
        if (hoverRegion.getLength() > -1)
          return textViewer.getDocument().get(hoverRegion.getOffset(),
              hoverRegion.getLength());
      } catch (BadLocationException x) {
      }
    }
    return "empty selection";

  }

  public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
    System.out
        .println("IRegion getHoverRegion(ITextViewer textViewer, int offset)");
    return null;
  }

}
