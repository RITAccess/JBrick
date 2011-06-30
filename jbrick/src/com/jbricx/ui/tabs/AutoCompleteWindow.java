package com.jbricx.ui.tabs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class AutoCompleteWindow extends org.eclipse.swt.widgets.Composite {
	private List keywordList;

	public AutoCompleteWindow(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	/**
	* Initializes the GUI.
	*/
	private void initGUI() {
		try {
			this.setSize(245, 219);
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			{
				FormData keywordListLData = new FormData();
				keywordListLData.left =  new FormAttachment(0, 1000, 0);
				keywordListLData.top =  new FormAttachment(0, 1000, 0);
				keywordListLData.width = 235;
				keywordListLData.height = 210;
				keywordListLData.bottom =  new FormAttachment(1000, 1000, 0);
				keywordListLData.right =  new FormAttachment(1000, 1000, 0);
				keywordList = new List(this, SWT.NONE);
				keywordList.setLayoutData(keywordListLData);
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public List getKeywordList() {
		return keywordList;
	}

}
