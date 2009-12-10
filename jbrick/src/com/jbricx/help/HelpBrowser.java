package com.jbricx.help;

import org.eclipse.help.standalone.Help;

public class HelpBrowser {

	public static void main(String args[]){
		Help hs = new Help("");
		hs.displayHelp("plugin.xml");
		
	}
}
