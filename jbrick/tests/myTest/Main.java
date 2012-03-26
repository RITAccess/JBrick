package myTest;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Image;

import annotation.AnnotationConfiguration;
import annotation.AnnotationHover;
import annotation.AnnotationMarkerAccess;
import annotation.CodeViewerConfiguration;
import annotation.ColorCache;
import annotation.ErrorAnnotation;

public class Main {

	// error identifiers, images and colors
	public static String ERROR_TYPE = "error.type";
	public static Image ERROR_IMAGE;
	public static final RGB ERROR_RGB = new RGB(255, 0, 0);

	// annotation model
	private AnnotationModel fAnnotationModel = new AnnotationModel();

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		Display display = new Display();
		Shell parent = new Shell(display, SWT.SHELL_TRIM);
		parent.setText("Annotation Test");
		parent.setLayout(new FillLayout());

		buildCodePage(parent);

		parent.setSize(400, 400);
		parent.open();

		while (!parent.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private void buildCodePage(Composite parent) {
		ERROR_IMAGE = new Image(Display.getDefault(),
				"src/images/error_ovr.gif");

		IAnnotationAccess fAnnotationAccess = new AnnotationMarkerAccess();

		ColorCache cc = new ColorCache();

		// rulers
		CompositeRuler fCompositeRuler = new CompositeRuler();
		OverviewRuler fOverviewRuler = new OverviewRuler(fAnnotationAccess, 12,	cc);
		AnnotationRulerColumn annotationRuler = new AnnotationRulerColumn(
				fAnnotationModel, 16, fAnnotationAccess);
		fCompositeRuler.setModel(fAnnotationModel);
		fOverviewRuler.setModel(fAnnotationModel);

		// annotation ruler is decorating our composite ruler
		fCompositeRuler.addDecorator(0, annotationRuler);

		// add what types are show on the different rulers
		annotationRuler.addAnnotationType(ERROR_TYPE);
		fOverviewRuler.addAnnotationType(ERROR_TYPE);
		fOverviewRuler.addHeaderAnnotationType(ERROR_TYPE);
		// set what layer this type is on
		fOverviewRuler.setAnnotationTypeLayer(ERROR_TYPE, 3);
		// set what color is used on the overview ruler for the type
		fOverviewRuler.setAnnotationTypeColor(ERROR_TYPE, new Color(Display
				.getDefault(), ERROR_RGB));

		// source viewer
		SourceViewer sv = new SourceViewer(parent, fCompositeRuler,
				fOverviewRuler, true, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		Document document = new Document();
		fAnnotationModel.connect(document);
		sv.setDocument(document, fAnnotationModel);
		

		// hover manager that shows text when we hover
		AnnotationBarHoverManager fAnnotationHoverManager = new AnnotationBarHoverManager(
				fCompositeRuler, sv, new AnnotationHover(),
				new AnnotationConfiguration());
		fAnnotationHoverManager.install(annotationRuler.getControl());

		// to paint the annotations
		AnnotationPainter ap = new AnnotationPainter(sv, fAnnotationAccess);
		ap.addAnnotationType(ERROR_TYPE);
		ap.setAnnotationTypeColor(ERROR_TYPE, new Color(Display.getDefault(),
				ERROR_RGB));

		// this will draw the squigglies under the text
		sv.addPainter(ap);

		sv.configure(new CodeViewerConfiguration(cc));

		// some misspelled text
		document
				.set("Here's some texst so that we have somewhere to show an error");
		System.out.println("6");
		// add an annotation
		ErrorAnnotation errorAnnotation = new ErrorAnnotation(1,
				"Learn how to spell \"text!\"");

		// lets underline the word "texst"
		fAnnotationModel.addAnnotation(errorAnnotation, new Position(12, 5));
	}
	
}