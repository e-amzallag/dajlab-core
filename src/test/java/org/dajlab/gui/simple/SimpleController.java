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
import java.util.List;

import org.dajlab.gui.AbstractDajlabTab;
import org.dajlab.gui.extension.DajlabControllerExtensionInterface;
import org.dajlab.gui.extension.TabExtensionInterface;

public class SimpleController implements TabExtensionInterface, DajlabControllerExtensionInterface<SimpleModel> {

	/**
	 * Model.
	 */
	private SimpleModel myModel = new SimpleModel();

	/**
	 * Localization.
	 */
	private static final String LOCALIZATION = "simpleapplication";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<AbstractDajlabTab> getTabs() {

		// Create one simple tab
		SimpleTab simpleTab1 = new SimpleTab(myModel);
		simpleTab1.setClosable(false);
		simpleTab1.enableRenaming(null);
		List<AbstractDajlabTab> list = new ArrayList<>(1);
		list.add(simpleTab1);
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connect() {
		// Nothing to do
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disconnect() {
		// Nothing to disconnect
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SimpleModel getModel() {

		return myModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateModel(SimpleModel model) {

		if (model != null) {
			myModel.setSimpleCheck(model.isSimpleCheck());
			myModel.setTabTitle(model.getTabTitle());
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLocalization() {

		return LOCALIZATION;
	}

}
