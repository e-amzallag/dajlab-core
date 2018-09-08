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

import org.dajlab.gui.extension.DajlabModelInterface;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This simple model contains two properties :
 * <ul>
 * <li>the first one is a boolean bound to the checkbox</li>
 * <li>the second one is a string bound to the tab title</li>
 * </ul>
 * Launch the SimpleApplication, check the box and change the tab title, save
 * the settings, uncheck the box, change the tab title, load the previous
 * settings.
 * 
 * @author Erik Amzallag
 *
 */
public class SimpleModel implements DajlabModelInterface {

	/**
	 * Simple check boolean.
	 */
	private BooleanProperty simpleCheck = new SimpleBooleanProperty();

	/**
	 * Tab title.
	 */
	private StringProperty tabTitle = new SimpleStringProperty("My simple tab");

	public final BooleanProperty simpleCheckProperty() {
		return this.simpleCheck;
	}

	public final boolean isSimpleCheck() {
		return this.simpleCheckProperty().get();
	}

	public final void setSimpleCheck(final boolean simpleCheck) {
		this.simpleCheckProperty().set(simpleCheck);
	}

	public final StringProperty tabTitleProperty() {
		return this.tabTitle;
	}

	public final java.lang.String getTabTitle() {
		return this.tabTitleProperty().get();
	}

	public final void setTabTitle(final java.lang.String tabTitle) {
		this.tabTitleProperty().set(tabTitle);
	}

}
