/*******************************************************************************
 * Copyright (c) 2015 Pivotal Software, Inc. 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution. 
 * 
 * The Eclipse Public License is available at 
 * 
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * and the Apache License v2.0 is available at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * You may elect to redistribute this code under either of these licenses.
 *  
 *  Contributors:
 *     IBM - initial API and implementation
 ********************************************************************************/
package org.eclipse.cft.server.verify.ui.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.cft.server.verify.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	public static final String PREF_IS_CHECK_JAVA_VERSION = PLUGIN_ID + ".check.java.version"; //$NON-NLS-1$
	public static final String PREF_LAST_PLUGIN_JAVA_VERSION_CHECK= PLUGIN_ID + ".last.plugin.java.version.check"; //$NON-NLS-1$
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	public synchronized boolean getIsCheckJavaVersion() {
		return getPreferences().getBoolean(PREF_IS_CHECK_JAVA_VERSION, true);
	}

	public synchronized String getLastPluginJavaVersionCheck() {
		return getPreferences().get(PREF_LAST_PLUGIN_JAVA_VERSION_CHECK, "");
	}

	public IEclipsePreferences getPreferences() {
		return InstanceScope.INSTANCE.getNode(PLUGIN_ID);
	}

	public synchronized void setIsCheckJavaVersion(boolean isCheckJavaVersion) {
		IEclipsePreferences prefs = getPreferences();
		prefs.putBoolean(PREF_IS_CHECK_JAVA_VERSION, isCheckJavaVersion);
		try {
			prefs.flush();
		}
		catch (BackingStoreException e) {
			logError(e);
		}
	}
	
	public synchronized void setLastPluginJavaVersionCheck(String lastPluginVersion) {
		IEclipsePreferences prefs = getPreferences();
		prefs.put(PREF_LAST_PLUGIN_JAVA_VERSION_CHECK, lastPluginVersion);
		try {
			prefs.flush();
		}
		catch (BackingStoreException e) {
			logError(e);
		}
	}
	
	public static void logError(Throwable t) {
		log(getErrorStatus(t));
	}
	
	public static IStatus getErrorStatus(Throwable t) {
		return new Status(IStatus.ERROR, PLUGIN_ID, t.getMessage(), t);
	}

	public static void log(IStatus status) {
		if (plugin != null) {
			plugin.getLog().log(status);
		}
	}
}
