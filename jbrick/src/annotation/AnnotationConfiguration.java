package annotation;


public class AnnotationConfiguration implements IInformationControlCreator {
	public IInformationControl createInformationControl(Shell shell) {
		return new DefaultInformationControl(shell);
	}
}