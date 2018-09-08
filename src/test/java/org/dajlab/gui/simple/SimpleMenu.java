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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.dajlab.gui.extension.MenuExtensionInterface;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Simple implementation of MenuExtensionInterface.
 * 
 * @author Erik Amzallag
 *
 */
public class SimpleMenu implements MenuExtensionInterface {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<MenuItem> getFileItems() {
		// no extra file items.
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<MenuItem> getAboutItems() {

		// We add an item which open an alert.
		List<MenuItem> lists = new ArrayList<>();
		MenuItem item = new MenuItem("A simple item");
		item.setOnAction(e -> {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Simple alert");
			alert.setHeaderText("Simple header");
			alert.setContentText("Simple content");
			alert.showAndWait();

		});
		lists.add(item);
		return lists;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Menu> getMenus() {
		// no extra menus
		return null;
	}

}
