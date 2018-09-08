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
package org.dajlab.core;

/**
 * daJLab runtime exception.
 * 
 * @author Erik Amzallag
 * 
 */
public class DaJLabRuntimeException extends RuntimeException {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = -7239667661683958029L;

	/**
	 * Constructor.
	 * 
	 */
	public DaJLabRuntimeException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            a message
	 */
	public DaJLabRuntimeException(String message) {
		super(message);
	}

}
