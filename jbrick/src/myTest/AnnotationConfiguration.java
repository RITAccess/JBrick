package myTest;

import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.swt.widgets.Shell;

public class AnnotationConfiguration implements IInformationControlCreator {
	public IInformationControl createInformationControl(Shell shell) {
		return new DefaultInformationControl(shell);
	}
}