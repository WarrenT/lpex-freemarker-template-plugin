package com.freemarker.lpex;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Version;

import com.freemarker.lpex.preferences.Preferences;
import com.freemarker.lpex.utils.PluginLogger;

/**
 * The activator class controls the plug-in life cycle
 */
public class LPEXFreeMarkerPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.freemarker.lpex";

    // The shared instance
    private static LPEXFreeMarkerPlugin plugin;

    // The Lpex menu extension
    private MenuExtension menuExtension;

    // The plugins version number
    private static Version version = null;

    /**
     * The constructor
     */
    public LPEXFreeMarkerPlugin() {
        plugin = this;
    }

    @Override
    public void start(BundleContext aContext) throws Exception {
        super.start(aContext);
        Preferences tPreferences = Preferences.getInstance();
        PluginLogger.setup(tPreferences.getLogPath(), tPreferences.getLogLevel());
        version = getDefault().getBundle().getVersion();
    }

    @Override
    public void stop(BundleContext aContext) throws Exception {

        if (menuExtension != null) {
            menuExtension.uninstall();
        }

        plugin = null;
        super.stop(aContext);
    }

    public void setLpexMenuExtension(MenuExtension menuExtension) {
        this.menuExtension = menuExtension;
    }

    public static LPEXFreeMarkerPlugin getDefault() {
        return plugin;
    }

    public static Version getVersion() {
        return version;
    }

}