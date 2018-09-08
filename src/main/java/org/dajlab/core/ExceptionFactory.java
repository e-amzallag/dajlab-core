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
 * Factory for exceptions.
 * 
 * @author Erik Amzallag
 *
 */
public class ExceptionFactory {

	/**
	 * Throw a daJLab exception
	 * 
	 * @param code
	 *            code of the error
	 * @throws DaJLabException
	 *             the created exception
	 */
	public static final void throwDaJLabException(final String code) throws DaJLabException {

		throw new DaJLabException(code);
	}

	/**
	 * Throw a daJLab runtime exception.
	 * 
	 * @param code
	 *            code of the error
	 */
	public static final void throwDaJLabRuntimeException(final String code) {

		throw new DaJLabRuntimeException(code);
	}
}
