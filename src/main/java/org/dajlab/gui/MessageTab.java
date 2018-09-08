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
package org.dajlab.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * Tab to display messages.
 * 
 * @author Erik Amzallag
 *
 */
public class MessageTab extends AbstractDajlabTab {

	/**
	 * Constructor.
	 * 
	 * @param title
	 *            tab title
	 * @param picturePath
	 *            path of the icon (will be fit to 20px height)
	 * @param message
	 *            message(s)
	 */
	public MessageTab(final String title, final String picturePath, String... message) {

		setGraphic(getIcon(picturePath));
		setText(title);
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		int i = 0;
		for (String mess : message) {
			Text text = new Text(mess);
			grid.add(text, 0, i);
			i++;
		}
		setContent(grid);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTitle(final String title) {
		// N/A
	}

}
