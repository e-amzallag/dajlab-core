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
 * Describe a DaJLabModule module. A module is launched in his own thread.
 * 
 * @author Erik Amzallag
 * 
 */
public abstract class DaJLabModule implements Runnable {
	/**
	 * Current state of the module.
	 */
	private int state = STOP;

	/**
	 * Running state.
	 */
	private static int RUN = 1;

	/**
	 * Stopped state.
	 */
	private static int STOP = 0;

	/**
	 * Method called when the module is running.
	 * 
	 */
	abstract public void codeThread();

	/**
	 * {@inheritDoc}
	 */
	public void run() {

		while (state == RUN) {
			codeThread();
		}
	}

	/**
	 * Launch the module.
	 * 
	 * @return thread
	 */
	public final Thread launch() {

		if (state != RUN) {
			onLaunch();
			state = RUN;
			Thread thread = (new Thread(this));
			thread.start();
			return thread;
		} else {
			return null;
		}
	}

	/**
	 * Method called when the module is launched (just before starting the
	 * thread).
	 * 
	 */
	abstract public void onLaunch();

	/**
	 * Stop the module.
	 * 
	 */
	public final void stop() {

		onStop();
		state = STOP;
	}

	/**
	 * Method called on stop (just before stopping the module).
	 * 
	 */
	abstract public void onStop();

	/**
	 * 
	 * @return true if the module is running.
	 */
	public boolean isRunning() {

		return state == RUN;
	}
}
