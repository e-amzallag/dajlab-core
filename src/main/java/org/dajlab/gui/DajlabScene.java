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

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.prefs.Preferences;

import org.dajlab.core.DaJLabRuntimeException;
import org.dajlab.gui.extension.DajlabControllerExtensionInterface;
import org.dajlab.gui.extension.DajlabModelInterface;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;

/**
 * Scene for the primary stage.
 * 
 * @author Erik Amzallag
 *
 */
public final class DajlabScene extends Scene {

	/**
	 * Key for user preferences to keep the last file path.
	 */
	private static final String PREVIOUS_FILE_PATH = "filePath";

	/**
	 * List of controllers.
	 */
	private List<DajlabControllerExtensionInterface<DajlabModelInterface>> controllers;

	/**
	 * Constructor.
	 * 
	 * @param root
	 *            root
	 * @param width
	 *            width
	 * @param height
	 *            height
	 */
	public DajlabScene(Parent root, double width, double height) {

		super(root, width, height);
	}

	/**
	 * @return the controllers
	 */
	public final List<DajlabControllerExtensionInterface<DajlabModelInterface>> getControllers() {
		return controllers;
	}

	/**
	 * @param controllers
	 *            the controllers to set
	 */
	public final void setControllers(final List<DajlabControllerExtensionInterface<DajlabModelInterface>> controllers) {
		this.controllers = controllers;
	}

	/**
	 * Open a file chooser and update the current model with the selected model
	 * by calling the updateModel() from DajlabControllerExtensionInterface.
	 */
	public final void loadModel() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open configuration file");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("daJLab json files", "*.json"));
		Preferences prefs = Preferences.userNodeForPackage(DajlabModel.class);
		String filePath = prefs.get(PREVIOUS_FILE_PATH, null);
		if (filePath != null) {
			fileChooser.setInitialDirectory(new File(filePath));
		}
		File file = fileChooser.showOpenDialog(getWindow());
		if (file != null) {

			DajlabModel model = ModelManager.loadModel(file);
			if (model != null) {

				// Here we have to find which controller matches with model.
				// So we look for the parameterized type M of the interface
				// DajlabControllerExtensionInterface<M>.
				// From this type, we can get the model which matches the
				// controller.
				for (DajlabControllerExtensionInterface<DajlabModelInterface> controller : controllers) {
					String typeNameController = null;
					Type[] types = controller.getClass().getGenericInterfaces();
					for (Type type : types) {
						if (type instanceof ParameterizedType && type.getTypeName()
								.contains(DajlabControllerExtensionInterface.class.getSimpleName())) {
							Type[] itypes = ((ParameterizedType) type).getActualTypeArguments();
							// Getting the type of the interface
							typeNameController = itypes[0].getTypeName();
							break;
						}
					}
					if (typeNameController != null) {
						Class<DajlabModelInterface> clazz = null;
						try {
							clazz = (Class<DajlabModelInterface>) Class.forName(typeNameController);
							controller.updateModel(model.get(clazz));
						} catch (ClassNotFoundException e) {
							// The loaded model already contains only existing
							// classes
						} catch (DaJLabRuntimeException e) {
							// Alert alert = new Alert(AlertType.WARNING);
							// alert.setContentText(MessagesUtil.getString(e.getMessage()));
							// alert.showAndWait();
						}
					}

				}
			}
		}
	}

	/**
	 * Open a file chooser and save the model into the selected file.<br>
	 * The format is json. Be aware of limitations for
	 * serialization/deserialization of your model.
	 */
	public final void saveModel() {

		// File chooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save configuration file");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("daJLab json files", "*.json"));

		// Setting the previous file path
		Preferences prefs = Preferences.userNodeForPackage(DajlabModel.class);
		String filePath = prefs.get(PREVIOUS_FILE_PATH, null);
		if (filePath != null) {
			fileChooser.setInitialDirectory(new File(filePath));
		}
		File file = fileChooser.showSaveDialog(getWindow());
		if (file != null) {
			prefs.put(PREVIOUS_FILE_PATH, file.getParentFile().getPath());
			// Saving the model
			ModelManager.saveModel(file, getModel());
		}
	}

	/**
	 * Create an instance of {@link DajlabModel} containing all models from
	 * controllers.
	 * 
	 * @return a DajlabModel instance
	 */
	private DajlabModel getModel() {

		DajlabModel model = new DajlabModel();
		for (DajlabControllerExtensionInterface<DajlabModelInterface> controller : controllers) {
			DajlabModelInterface subModel = controller.getModel();
			model.put(subModel);
		}
		return model;
	}

}
