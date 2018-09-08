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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.dajlab.gui.extension.DajlabControllerExtensionInterface;
import org.dajlab.gui.extension.DajlabExtension;
import org.dajlab.gui.extension.DajlabModelInterface;
import org.dajlab.gui.extension.MenuExtensionInterface;
import org.dajlab.gui.extension.TabExtensionInterface;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Abstract class for all GUI application.<br>
 * The implemented class must call the <code>startApplication(...)</code> method
 * from a <code>main()</code> method.
 * 
 * @author Erik Amzallag
 *
 */
public abstract class AbstractDajlabApplication extends Application {

	/**
	 * List of registered controllers.
	 */
	private List<DajlabControllerExtensionInterface<DajlabModelInterface>> controllers = new ArrayList<>();

	/**
	 * List of registered tabs extensions.
	 */
	private List<TabExtensionInterface> tabPlugins = new ArrayList<>();

	/**
	 * List of registered menus extensions.
	 */
	private List<MenuExtensionInterface> menuPlugins = new ArrayList<>();

	/**
	 * Application title.
	 */
	private String appTitle = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void start(Stage stage) throws Exception {

		Parameters params = getParameters();
		List<String> paramsList = params.getRaw();

		VBox box = new VBox();
		DajlabScene scene = new DajlabScene(box, 1024, 600);
		scene.setControllers(controllers);

		// Initialize localization.
		MessagesUtil.initializeMessages(controllers);

		stage.setScene(scene);

		// Getting the full application title.
		if (!paramsList.isEmpty()) {
			appTitle = paramsList.get(0);
		}
		if (appTitle != null) {
			stage.setTitle(MessagesUtil.getString("dajlab.application_by", appTitle));
		} else {
			appTitle = "daJLab";
			stage.setTitle(MessagesUtil.getString("dajlab.default_title"));
		}

		TabPane tabPan = new TabPane();

		// Connecting plugin controllers
		for (DajlabControllerExtensionInterface<DajlabModelInterface> controller : controllers) {
			controller.connect();
		}

		// Creating plugin tabs
		for (TabExtensionInterface tabPlugin : tabPlugins) {
			Collection<AbstractDajlabTab> tabs = tabPlugin.getTabs();
			if (CollectionUtils.isNotEmpty(tabs)) {
				tabPan.getTabs().addAll(tabs);
			}
		}
		// Selecting the default tab
		if (tabPan.getTabs().size() > 0) {
			AbstractDajlabTab[] tabl = new AbstractDajlabTab[tabPan.getTabs().size()];
			tabPan.getTabs().toArray(tabl);
			AbstractDajlabTab defaultTab = selectDefaultTab(tabl);
			if (defaultTab == null) {
				tabPan.getSelectionModel().select(0);
			} else {
				tabPan.getSelectionModel().select(defaultTab);
			}
		}

		// Action on close
		stage.setOnCloseRequest(e -> {
			e.consume();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle(MessagesUtil.getString("dajlab.exit_confirm_title"));
			alert.setHeaderText(MessagesUtil.getString("dajlab.exit_confirm_text", appTitle));
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				// Disconnecting plugin controllers
				for (DajlabControllerExtensionInterface<DajlabModelInterface> controller : controllers) {
					controller.disconnect();
				}
				Platform.exit();
			}

		});

		box.getChildren().add(new DajlabMenuBar(menuPlugins, scene, appTitle));
		box.getChildren().add(tabPan);

		stage.show();
	}

	/**
	 * Select the default tab for the application.
	 * 
	 * @param tabsList
	 *            list of the available tabs
	 * @return the selected tab among the list. May return null (the first tab
	 *         will then be selected).
	 */
	public abstract AbstractDajlabTab selectDefaultTab(final AbstractDajlabTab[] tabsList);

	/**
	 * Overriding with final the init method to prevent other overriding.
	 */
	@Override
	public final void init() {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {

	}

	/**
	 * Register an extension. Should be called in the constructor method of the
	 * implemented class.
	 * 
	 * @param plugin
	 *            the extension to register.
	 */
	public void registerPlugin(final DajlabExtension plugin) {

		if (plugin != null) {
			if (plugin instanceof DajlabControllerExtensionInterface) {
				controllers.add((DajlabControllerExtensionInterface<DajlabModelInterface>) plugin);
			}
			if (plugin instanceof TabExtensionInterface) {
				tabPlugins.add((TabExtensionInterface) plugin);
			}
			if (plugin instanceof MenuExtensionInterface) {
				menuPlugins.add((MenuExtensionInterface) plugin);
			}
		}
	}

	/**
	 * Launch the application. Must be called in the main method.
	 * 
	 * @param appClass
	 *            class of the application
	 * @param appTitle
	 *            application title
	 */
	public static void startApplication(Class<? extends Application> appClass, String appTitle) {
		launch(appClass, appTitle);
	}

}
