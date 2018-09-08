/*
 * Copyright 2018 Erik Amzallag
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.dajlab.gui.simple;

import org.dajlab.gui.AbstractDajlabApplication;
import org.dajlab.gui.AbstractDajlabTab;

/**
 * Basic implementation of a daJLab application.<br>
 * The tab contains a check box, to illustrate how to bind JavaFX controls to
 * the model.<br>
 * 
 * @author Erik Amzallag
 *
 */
public class SimpleApplication extends AbstractDajlabApplication {

	/**
	 * Constructor.
	 */
	public SimpleApplication() {
		// Create the controller.
		SimpleController simpleController = new SimpleController();
		// Register the controller.
		registerPlugin(simpleController);

		// Create a menu. In this example, the menu is separated from the
		// controller, but the controller may implements MenuExtensionInterface
		// too.
		SimpleMenu simpleMenu = new SimpleMenu();
		// Register the menu.
		registerPlugin(simpleMenu);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AbstractDajlabTab selectDefaultTab(AbstractDajlabTab[] tabsList) {

		// Select the last tab
		if (tabsList.length > 0) {
			return tabsList[tabsList.length - 1];
		} else {
			return null;
		}
	}

	/**
	 * Main application.
	 * 
	 * @param args
	 *            args
	 */
	public static void main(String[] args) {
		// We start the application SimpleApplication called "My simple app"
		startApplication(SimpleApplication.class, "My simple app");
	}

}
