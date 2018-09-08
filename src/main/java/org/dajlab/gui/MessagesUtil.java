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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dajlab.gui.extension.DajlabControllerExtensionInterface;
import org.dajlab.gui.extension.DajlabModelInterface;

/**
 * Localization utility class.
 * <p>
 * Properties file must:
 * <ul>
 * <li>be located in a "<i>messages</i>" directory in your classpath</li>
 * <li>be named "<i>projectname</i>_messages.properties" (default) or
 * "<i>projectname</i>_messages_<i>language</i>.properties"</li>
 * </ul>
 * where <i>projectname</i> is the string returned by
 * DajlabControllerExtensionInterface.getLocalization(). <i>projectname</i> must
 * not contain a dot '.'.
 * <p>
 * To acces values, two methods are available : getString(key) and
 * getString(key, parameters...). The key must be "<i>projectname</i>.mykey" and
 * the properties file must contain only "mykey" without the prefix
 * "<i>projectname</i>".
 * </p>
 * 
 * @author Erik Amzallag
 *
 */
public final class MessagesUtil {

	/**
	 * Path to the directory.
	 */
	private static final String MESSAGES_PATH = "messages/";

	/**
	 * Suffix for the properties file name.
	 */
	private static final String MESSAGES_EXT = "_messages";

	/**
	 * Private instance.
	 */
	private static MessagesUtil messages = null;

	/**
	 * Rsourcesbundles map.
	 */
	private Map<String, ResourceBundle> resources;

	/**
	 * Logger.
	 */
	private static final Logger logger = LogManager.getLogger(MessagesUtil.class);

	/**
	 * Private constructor.
	 */
	private MessagesUtil() {
		resources = new HashMap<>();
	}

	/**
	 * 
	 * @return an unique instance
	 */
	private static synchronized MessagesUtil getInstance() {
		if (messages == null) {
			messages = new MessagesUtil();
		}
		return messages;
	}

	/**
	 * Initialize messages from the controllers.
	 * 
	 * @param controllers
	 *            a list of controllers.
	 */
	public static void initializeMessages(List<DajlabControllerExtensionInterface<DajlabModelInterface>> controllers) {

		// Global daJLab resource
		ResourceBundle resourceD = ResourceBundle.getBundle(MESSAGES_PATH + "dajlab" + MESSAGES_EXT);
		getInstance().resources.put("dajlab", resourceD);
		if (controllers != null) {
			// Resource for each plugin
			for (DajlabControllerExtensionInterface<DajlabModelInterface> controller : controllers) {
				String resourceName = controller.getLocalization();
				if (resourceName != null) {
					String path = MESSAGES_PATH + resourceName + MESSAGES_EXT;
					try {
						ResourceBundle resource = ResourceBundle.getBundle(path);
						getInstance().resources.put(resourceName, resource);
					} catch (MissingResourceException e) {
						logger.error("Unable to find the resource file [{}]", path + ".properties");
					}
				}
			}
		}

	}

	/**
	 * Get message from the key.
	 * 
	 * @param key
	 *            a key, formatted like <i>projectname</i>.myrealkey
	 * @return the corresponding message, or !key! if not found
	 */
	public static String getString(final String key) {

		String ret = '!' + key + '!';
		if (key != null) {
			String[] els = key.split("\\.", 2);
			if (els.length == 2) {
				String resourceName = els[0];
				ResourceBundle resource = getInstance().resources.get(resourceName);
				if (resource != null) {
					try {
						ret = resource.getString(els[1]);
					} catch (MissingResourceException e) {
						logger.debug("No resource for the key [{}]", key);
					}
				} else {
					logger.debug("No resource for [{}]", resource);
				}
			}
		}
		return ret;
	}

	/**
	 * Get message with valued parameters
	 * 
	 * @param key
	 *            a key, formatted like <i>projectname</i>.myrealkey
	 * @param params
	 *            parameters
	 * @return the corresponding message, or !key! if not found
	 */
	public static String getString(final String key, final Object... params) {

		String ret = '!' + key + '!';
		if (key != null) {
			String[] els = key.split("\\.", 2);
			if (els.length == 2) {
				String resourceName = els[0];
				ResourceBundle resource = getInstance().resources.get(resourceName);
				if (resource != null) {
					try {
						ret = MessageFormat.format(resource.getString(els[1]), params);
					} catch (MissingResourceException e) {
						logger.debug("No resource for the key [{}]", key);
					}
				} else {
					logger.debug("No resource for [{}]", resource);
				}
			}
		}
		return ret;
	}

}
