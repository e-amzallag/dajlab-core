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
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dajlab.core.DaJLabConstants;
import org.dajlab.core.ExceptionFactory;
import org.dajlab.gui.extension.DajlabModelInterface;
import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import javafx.application.Platform;

/**
 * Utility class to serialize and unserialize the model.<br>
 * Implementation uses Gson (and FxGson for JavaFX properties support).
 * 
 * @author Erik Amzallag
 *
 */
public final class ModelManager {

	/**
	 * Logger.
	 */
	private static final Logger logger = LogManager.getLogger(ModelManager.class);

	/**
	 * Save to the file the model.
	 * 
	 * @param file
	 *            file
	 * @param dajlabModel
	 *            model
	 */
	public static void saveModel(final File file, final DajlabModel dajlabModel) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Gson fxGson = FxGson.fullBuilder().setPrettyPrinting().create();

				JsonElement el = fxGson.toJsonTree(dajlabModel);
				try {
					JsonWriter w = new JsonWriter(new FileWriter(file));
					w.setIndent("\t");
					fxGson.toJson(el, w);
					w.close();
				} catch (Exception e) {
					logger.error("Unable to export model to file [{}]", file.getName());
					ExceptionFactory.throwDaJLabRuntimeException(DaJLabConstants.ERR_SAVE_MODEL);
				}
			}
		});
	}

	/**
	 * Load a model from a file.
	 * 
	 * @param file
	 *            file
	 * @param <T>
	 *            model will implement DajlabModelInterface
	 * @return the model
	 */
	public static <T extends DajlabModelInterface> DajlabModel loadModel(final File file) {

		Gson fxGson = FxGson.fullBuilder().create();
		DajlabModel model = new DajlabModel();
		try {
			JsonParser jsonParser = new JsonParser();
			JsonReader jsonReader = new JsonReader(new FileReader(file));
			jsonReader.beginObject();
			if (jsonReader.hasNext()) {
				jsonReader.nextName();
				JsonObject ob = jsonParser.parse(jsonReader).getAsJsonObject();
				Set<Entry<String, JsonElement>> set = ob.entrySet();
				for (Entry<String, JsonElement> entry : set) {
					String className = entry.getKey();
					JsonElement el = entry.getValue();
					try {
						T obj = (T) fxGson.fromJson(el, Class.forName(className));
						model.put(obj);
					} catch (ClassNotFoundException e) {
						logger.warn("Unable to load model for [{}]", className);
					}
				}
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.error("Unable to load model from file [{}]", file.getName());
			ExceptionFactory.throwDaJLabRuntimeException(DaJLabConstants.ERR_LOAD_MODEL);
		}

		return model;
	}
}
