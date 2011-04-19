package com.jbricx.source;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import com.jbricx.pjo.JBrickEditor;

/**
 * This class provides the source viewer configuration
 */
public class JBrickEditorSourceViewerConfiguration extends SourceViewerConfiguration {
  // Set up the name of the partitioner
  public static final String JBRICK_PARTITIONING = "jbrick_partitioning";

  private RuleBasedScanner codeScanner;
  private RuleBasedScanner commentScanner;

  public JBrickEditorSourceViewerConfiguration(final RuleBasedScanner codeScanner, final RuleBasedScanner commentScanner) {
    this.codeScanner = codeScanner;
    this.commentScanner = commentScanner;
  }
  /**
   * Gets the presentation reconciler. This will color the code.
   */
  @Override
  public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
    // Create the presentation reconciler
    PresentationReconciler reconciler = new PresentationReconciler();
    reconciler.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));

    // Create the damager/repairer for comment partitions
    DefaultDamagerRepairer dr = new DefaultDamagerRepairer(commentScanner);
    reconciler.setDamager(dr, JBrickPartitionScanner.COMMENT);
    reconciler.setRepairer(dr, JBrickPartitionScanner.COMMENT);

    // Create the damager/repairer for default
    dr = new DefaultDamagerRepairer(codeScanner);
    reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
    reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

    return reconciler;
  }

  /**
   * Gets the configured document partitioning
   * 
   * @return String
   */
  @Override
  public String getConfiguredDocumentPartitioning(ISourceViewer sourceViewer) {
    return JBRICK_PARTITIONING;
  }

  /**
   * Gets the configured partition types
   * 
   * @return String[]
   */
  @Override
  public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
    return new String[] { IDocument.DEFAULT_CONTENT_TYPE, JBrickPartitionScanner.COMMENT };
  }
  /**
   * 783 * Returns the outline presenter which will determine and shown 784 * information requested for the current
   * cursor position. 785 * 786 * @param sourceViewer the source viewer to be configured by this configuration 787 * @param
   * doCodeResolve a boolean which specifies whether code resolve should be used to compute the Java element 788 * @return
   * an information presenter 789 * @since 2.1 790 791 public IInformationPresenter getOutlinePresenter(ISourceViewer
   * sourceViewer, boolean doCodeResolve) { 792 InformationPresenter presenter; 793 if (doCodeResolve) 794 presenter=
   * new InformationPresenter(getOutlinePresenterControlCreator(sourceViewer,
   * IJavaEditorActionDefinitionIds.OPEN_STRUCTURE)); 795 else 796 presenter= new
   * InformationPresenter(getOutlinePresenterControlCreator(sourceViewer, IJavaEditorActionDefinitionIds.SHOW_OUTLINE));
   * 797 presenter.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer)); 798
   * presenter.setAnchor(AbstractInformationControlManager.ANCHOR_GLOBAL); 799 IInformationProvider provider= new
   * JavaElementProvider(getEditor(), doCodeResolve); 800 presenter.setInformationProvider(provider,
   * IDocument.DEFAULT_CONTENT_TYPE); 801 presenter.setInformationProvider(provider, IJavaPartitions.JAVA_DOC); 802
   * presenter.setInformationProvider(provider, IJavaPartitions.JAVA_MULTI_LINE_COMMENT); 803
   * presenter.setInformationProvider(provider, IJavaPartitions.JAVA_SINGLE_LINE_COMMENT); 804
   * presenter.setInformationProvider(provider, IJavaPartitions.JAVA_STRING); 805
   * presenter.setInformationProvider(provider, IJavaPartitions.JAVA_CHARACTER); 806 presenter.setSizeConstraints(50,
   * 20, true, false); 807 return presenter; 808 }
   * 
   * Read more: http://kickjava.com/src/org/eclipse/jdt/ui/text/JavaSourceViewerConfiguration.java.htm#ixzz0dxFo5hWR
   */
}
