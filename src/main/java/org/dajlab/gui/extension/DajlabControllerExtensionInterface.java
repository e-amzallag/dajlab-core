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

import org.dajlab.gui.MessagesUtil;

/**
 * Controller extension interface.
 * 
 * @author Erik Amzallag
 *
 * @param <M>
 *            model
 */
public interface DajlabControllerExtensionInterface<M extends DajlabModelInterface> extends DajlabExtension {

	/**
	 * Connect the devices.
	 */
	public void connect();

	/**
	 * Disconnect the devices.
	 */
	public void disconnect();

	/**
	 * Update the current model with the loaded model.
	 * 
	 * @param model
	 *            loaded model.
	 */
	public void updateModel(M model);

	/**
	 * Return the model. Model must implement {@link DajlabModelInterface}
	 * 
	 * @return the model
	 */
	public M getModel();

	/**
	 * See {@link MessagesUtil} for localization rules.
	 * 
	 * @return string identifier for localization.
	 * @see MessagesUtil
	 */
	public String getLocalization();
}
