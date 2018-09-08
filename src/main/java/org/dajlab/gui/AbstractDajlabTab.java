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

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * All tabs must extend this abstract class.
 * 
 * @author Erik Amzallag
 *
 */
public abstract class AbstractDajlabTab extends Tab {

	/**
	 * Path to the default icon for tab.
	 */
	private static final String DEFAULT_ICON = "/pictures/roundicon.png";

	/**
	 * If used, this method allows the renaming of a tab by the user with a
	 * double-click on the icon of the tab. It seems there is no mouse event on
	 * a Tab title, that's why an icon should be used.
	 * 
	 * @param picturePath
	 *            path of the icon (will be fit to 20px height)
	 */
	public final void enableRenaming(final String picturePath) {

		HBox hb = new HBox();

		ImageView iv = getIcon(picturePath);
		iv.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event mouseEvent) {

				if (((MouseEvent) mouseEvent).getButton().equals(MouseButton.PRIMARY)) {
					if (((MouseEvent) mouseEvent).getClickCount() == 2) {
						TextInputDialog input = new TextInputDialog(textProperty().get());
						input.setTitle(MessagesUtil.getString("dajlab.change_tab_title"));
						input.setContentText(null);
						input.setHeaderText(MessagesUtil.getString("dajlab.change_tab_text"));
						input.setGraphic(null);
						Optional<String> ret = input.showAndWait();
						if (ret.isPresent() && StringUtils.isNoneBlank(ret.get())) {
							updateTitle(ret.get());
						}
					}
				}
			}
		});
		// Without HBox, the click event is not detected ??
		hb.getChildren().add(iv);
		setGraphic(hb);
	}

	/**
	 * Return a picture node from the path.
	 * 
	 * @param picturePath
	 *            path
	 * @return an image
	 */
	protected ImageView getIcon(final String picturePath) {

		ImageView iv = null;
		if (picturePath == null) {
			iv = new ImageView(new Image(DEFAULT_ICON));
		} else {
			iv = new ImageView(new Image(picturePath));
		}
		iv.setPreserveRatio(true);
		iv.setFitHeight(20);
		return iv;
	}

	/**
	 * Update the tab title. Useful only if renaming is enable.
	 * 
	 * @param title
	 *            title
	 */
	public abstract void updateTitle(final String title);
}
