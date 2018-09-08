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
import java.util.List;

import org.dajlab.gui.extension.DajlabControllerExtensionInterface;
import org.dajlab.gui.extension.DajlabExtension;
import org.dajlab.gui.extension.DajlabModelInterface;
import org.dajlab.gui.simple.SimpleModel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Jnuit test class for MessagesUti.
 * 
 * @author Erik Amzallag
 *
 */
public class MessagesUtilTest {

	@Test
	public void testGetString() {

		// test with no controller. Only the default dajlab is loaded.
		MessagesUtil.initializeMessages(null);

		Assert.assertNotNull("No message for dajlab.item_File", MessagesUtil.getString("dajlab.item_File"));
		Assert.assertNotEquals("No message for dajlab.item_File", "!dajlab.item_File!",
				MessagesUtil.getString("dajlab.item_File"));
		Assert.assertNotEquals("No message for dajlab.item_File", "!item_File!",
				MessagesUtil.getString("dajlab.item_File"));
		Assert.assertEquals("Error for item_File", "!item_File!", MessagesUtil.getString("item_File"));
		Assert.assertEquals("Error for notaproject.item_File", "!notaproject.item_File!",
				MessagesUtil.getString("notaproject.item_File"));

		// Test with test controller (there is test_messages.properties)
		DajlabExtension testController = new DajlabControllerExtensionInterface<SimpleModel>() {
			@Override
			public void connect() {
			}

			@Override
			public void disconnect() {
			}

			@Override
			public void updateModel(SimpleModel model) {
			}

			@Override
			public SimpleModel getModel() {
				return null;
			}

			@Override
			public String getLocalization() {
				return "test";
			}

		};
		Assert.assertEquals("Incorrect return", "!test.key1!", MessagesUtil.getString("test.key1"));
		List<DajlabControllerExtensionInterface<DajlabModelInterface>> controllers = new ArrayList<>(1);
		controllers.add((DajlabControllerExtensionInterface<DajlabModelInterface>) testController);
		MessagesUtil.initializeMessages(controllers);
		Assert.assertEquals("Incorrect message", "message1", MessagesUtil.getString("test.key1"));
		Assert.assertEquals("Incorrect return", "!test.key2!", MessagesUtil.getString("test.key2"));

		// Test with a controller and a not found properties
		DajlabExtension notFoundController = new DajlabControllerExtensionInterface<SimpleModel>() {
			@Override
			public void connect() {
			}

			@Override
			public void disconnect() {
			}

			@Override
			public void updateModel(SimpleModel model) {
			}

			@Override
			public SimpleModel getModel() {
				return null;
			}

			@Override
			public String getLocalization() {
				return "notFound";
			}

		};
		controllers = new ArrayList<>(1);
		controllers.add((DajlabControllerExtensionInterface<DajlabModelInterface>) notFoundController);
		MessagesUtil.initializeMessages(controllers);
		Assert.assertEquals("Incorrect message", "!notfound.key1!", MessagesUtil.getString("notfound.key1"));

	}

}
