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

import java.util.HashMap;
import java.util.Map;

import org.dajlab.gui.extension.DajlabModelInterface;

/**
 * Global model which will contain all models from extensions.
 * 
 * @author Erik Amzallag
 *
 */
public final class DajlabModel {

	/**
	 * Map containing models.
	 */
	private Map<String, DajlabModelInterface> dajlab;

	/**
	 * Constructor.
	 */
	public DajlabModel() {
		dajlab = new HashMap<>();
	}

	/**
	 * Add a model to the map.
	 * 
	 * @param model
	 *            a model
	 */
	public void put(DajlabModelInterface model) {
		if (model != null) {
			String name = model.getClass().getName();
			dajlab.put(name, model);
		}
	}

	/**
	 * Get a model from the map using the class.
	 * 
	 * @param clazz
	 *            a class
	 * @param <T>
	 *            the class must extend DajlabModelInterface
	 * @return the model
	 */
	public <T extends DajlabModelInterface> T get(Class<T> clazz) {

		T ret = null;
		try {
			ret = (T) dajlab.get(clazz.getName());
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
