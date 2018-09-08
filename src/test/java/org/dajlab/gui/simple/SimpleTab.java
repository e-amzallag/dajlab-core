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

import org.dajlab.gui.AbstractDajlabTab;
import org.dajlab.gui.MessagesUtil;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * Simple tab with a text and a check box.
 * 
 * @author Erik Amzallag
 *
 */
public class SimpleTab extends AbstractDajlabTab {

	/**
	 * Model.
	 */
	private SimpleModel model;

	/**
	 * Constructor.
	 * 
	 * @param model
	 *            model
	 */
	public SimpleTab(final SimpleModel model) {
		this.model = model;
		textProperty().bind(model.tabTitleProperty());
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.add(new Text(MessagesUtil.getString("simpleapplication.key1", 123)), 0, 0);

		CheckBox cb = new CheckBox("A simple check box");
		cb.selectedProperty().bindBidirectional(model.simpleCheckProperty());
		grid.add(cb, 0, 1);
		setContent(grid);
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void updateTitle(String title) {
		// As the model is bound to the tab title, updating directly the model
		// is enough.
		model.setTabTitle(title);
	}
}
