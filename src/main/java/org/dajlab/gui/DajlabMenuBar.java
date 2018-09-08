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

import java.awt.Desktop;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.dajlab.core.DaJLabRuntimeException;
import org.dajlab.gui.extension.DajlabControllerExtensionInterface;
import org.dajlab.gui.extension.DajlabModelInterface;
import org.dajlab.gui.extension.MenuExtensionInterface;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;

/**
 * Application menu bar.
 * 
 * @author Erik Amzallag
 *
 */
public class DajlabMenuBar extends MenuBar {

	/**
	 * Constructor.
	 * 
	 * @param extensions
	 *            menu extensions.
	 * @param scene
	 *            scene
	 * @param appTitle
	 *            application title
	 */
	public DajlabMenuBar(final List<MenuExtensionInterface> extensions, final DajlabScene scene,
			final String appTitle) {

		super();

		Menu menuFile = new Menu(MessagesUtil.getString("dajlab.item_File"));

		// Open
		MenuItem itemOpen = new MenuItem(MessagesUtil.getString("dajlab.item_Open"));
		itemOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
		itemOpen.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					((DajlabScene) getScene()).loadModel();
				} catch (DaJLabRuntimeException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(MessagesUtil.getString(e.getMessage()));
					alert.showAndWait();
				}
			}

		});
		menuFile.getItems().add(itemOpen);

		// Save as
		MenuItem itemSaveAs = new MenuItem(MessagesUtil.getString("dajlab.item_Saveas"));
		itemSaveAs.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		itemSaveAs.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					((DajlabScene) getScene()).saveModel();
				} catch (DaJLabRuntimeException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(MessagesUtil.getString(e.getMessage()));
					alert.showAndWait();
				}
			}

		});
		menuFile.getItems().add(itemSaveAs);

		// Add extra items for File menu from extension points
		if (CollectionUtils.isNotEmpty(extensions)) {
			for (MenuExtensionInterface extension : extensions) {
				Collection<MenuItem> items = extension.getFileItems();
				if (CollectionUtils.isNotEmpty(items)) {
					menuFile.getItems().add(new SeparatorMenuItem());
					menuFile.getItems().addAll(items);
				}
			}
		}

		// Exit
		menuFile.getItems().add(new SeparatorMenuItem());
		MenuItem itemQuit = new MenuItem(MessagesUtil.getString("dajlab.item_Exit"));
		itemQuit.setAccelerator(new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN));
		itemQuit.setOnAction(e -> {
			e.consume();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle(MessagesUtil.getString("dajlab.exit_confirm_title"));
			alert.setHeaderText(MessagesUtil.getString("dajlab.exit_confirm_text", appTitle));
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				// Disconnect plugin controllers
				for (DajlabControllerExtensionInterface<DajlabModelInterface> controller : scene.getControllers()) {
					controller.disconnect();
				}
				Platform.exit();
			}

		});
		menuFile.getItems().add(itemQuit);

		getMenus().add(menuFile);

		// Add extra menus from extension points
		if (CollectionUtils.isNotEmpty(extensions)) {
			for (MenuExtensionInterface extension : extensions) {
				Collection<Menu> menus = extension.getMenus();
				if (CollectionUtils.isNotEmpty(menus)) {
					getMenus().addAll(menus);
				}
			}
		}

		// About/help menu
		Menu menuHelp = new Menu("?");
		boolean itemHelp = false;
		// Add extra items for Help/About menu from extension points
		if (CollectionUtils.isNotEmpty(extensions)) {
			for (MenuExtensionInterface extension : extensions) {
				Collection<MenuItem> items = extension.getAboutItems();
				if (CollectionUtils.isNotEmpty(items)) {
					menuHelp.getItems().addAll(items);
					itemHelp = itemHelp || items.size() > 0;
				}
			}
		}
		if (itemHelp) {
			// Add a separator only if items or submenu have been added
			menuHelp.getItems().add(new SeparatorMenuItem());
		}
		MenuItem itemAbout = new MenuItem(MessagesUtil.getString("dajlab.item_About_dajlab"));
		itemAbout.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(MessagesUtil.getString("dajlab.about_dajlab_title"));
				alert.setHeaderText(null);

				// Override the content to add an active link
				Label text = new Label(MessagesUtil.getString("dajlab.about_dajlab_text"));

				Hyperlink link = new Hyperlink();
				link.setText("https://www.dajlab.org");
				link.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent t) {
						try {
							Desktop.getDesktop().browse(new URI("https://www.dajlab.org"));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

				VBox box = new VBox();
				box.getChildren().add(text);
				box.getChildren().add(link);
				alert.getDialogPane().contentProperty().set(box);

				alert.setGraphic(new ImageView(new Image("pictures/dajlab_100.png")));

				alert.showAndWait();

			}
		});
		menuHelp.getItems().add(itemAbout);
		getMenus().add(menuHelp);
	}
}
