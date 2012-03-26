package annotation;




//source viewer configuration
public class CodeViewerConfiguration extends SourceViewerConfiguration {
	private ColorCache manager;

	public CodeViewerConfiguration(ColorCache manager) {
		System.out.println("CodeViewerConfiguration");	
		this.manager = manager;
	}

	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		return reconciler;
	}

	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
		return new AnnotationHover();
	}
}
