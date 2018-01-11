package com.freemarker.lpex.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

/**
 * Class used to initialize default preference values. Is called automatically
 * by the Eclipse framework.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {
    @Override
    public void initializeDefaultPreferences() {
        Preferences.getInstance().initializeDefaultPreferences();
    }
}