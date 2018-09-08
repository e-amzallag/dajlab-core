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

import org.dajlab.core.DaJLabConstants;
import org.dajlab.gui.simple.SimpleModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;

/**
 * Junit Test class for ModelManagerTest.
 * 
 * @author Erik Amzallag
 *
 */
@RunWith(JfxRunner.class)
public class ModelManagerTest {

	/**
	 * 
	 */
	@Test
	public void testLoadModel() {

		// Correct file
		try {
			File correctFile = new File(this.getClass().getResource("/correct.json").getFile());
			DajlabModel model = ModelManager.loadModel(correctFile);
			Assert.assertNotNull("Model is null", model);
			SimpleModel simmo = model.get(SimpleModel.class);
			Assert.assertNotNull("SimpleModel is null", simmo);
			Assert.assertTrue("SimpleCheck is incorrect", simmo.isSimpleCheck());
			Assert.assertEquals("TabTitle is incorrect", simmo.getTabTitle(), "A title");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		// Empty model
		try {
			File correctFile = new File(this.getClass().getResource("/empty.json").getFile());
			DajlabModel model = ModelManager.loadModel(correctFile);
			Assert.assertNotNull("Model is null", model);

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		// Incorrect file
		try {
			File syntaxfile = new File(this.getClass().getResource("/syntaxerror.json").getFile());
			ModelManager.loadModel(syntaxfile);
		} catch (Exception e) {
			Assert.assertEquals("Incorrect exception", DaJLabConstants.ERR_LOAD_MODEL, e.getMessage());
		}

		// With unknown model
		try {
			File correctFile = new File(this.getClass().getResource("/unknownclass.json").getFile());
			DajlabModel model = ModelManager.loadModel(correctFile);
			Assert.assertNotNull("Model is null", model);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * 
	 */
	@Test
	@TestInJfxThread
	public void testSaveModel() {

		DajlabModel dajlabModel = new DajlabModel();
		File file = new File("create.json");
		try {
			ModelManager.saveModel(file, dajlabModel);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} finally {
			file.delete();
		}

	}
}
