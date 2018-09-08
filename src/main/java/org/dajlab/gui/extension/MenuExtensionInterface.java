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
package org.dajlab.gui.extension;

import java.util.Collection;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Interface for menu bar extension.
 * 
 * @author Erik Amzallag
 *
 */
public interface MenuExtensionInterface extends DajlabExtension {

	/**
	 * To add items to the File menu.
	 * 
	 * @return a collection of MenuItem. May return null.
	 */
	Collection<MenuItem> getFileItems();

	/**
	 * To add items to the About menu.
	 * 
	 * @return a collection of MenuItem. May return null.
	 */
	Collection<MenuItem> getAboutItems();

	/**
	 * To add menus to menu bar.
	 * 
	 * @return a collection of Menu. May return null.
	 */
	Collection<Menu> getMenus();
}
