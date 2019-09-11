package com.freemarker.lpex;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.UIJob;
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

    /**
     * Convenience method to log error messages to the application log.
     * 
     * @param message Message
     * @param e The exception that has produced the error
     */
    public static void logError(String message, Throwable e) {
        if (plugin == null) {
            System.err.println(message);
            if (e != null) {
                e.printStackTrace();
            }
            return;
        }
        plugin.getLog().log(new Status(Status.ERROR, PLUGIN_ID, Status.ERROR, message, e));
        showErrorLog(false);
    }

    private static void showErrorLog(final boolean logError) {

        if (!Preferences.getInstance().isShowErrorLog()) {
            return;
        }

        UIJob job = new UIJob("") {

            @Override
            public IStatus runInUIThread(IProgressMonitor arg0) {

                try {

                    final String ERROR_LOG_VIEW = "org.eclipse.pde.runtime.LogView"; // $NON-NLS-1$

                    IWorkbenchPage activePage = getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
                    if (activePage != null) {
                        if (activePage.findView(ERROR_LOG_VIEW) == null) {
                            activePage.showView(ERROR_LOG_VIEW);
                        }
                    }

                } catch (Throwable e) {
                    if (logError) {
                        logError("*** Could not open error log view ***", e); //$NON-NLS-1$
                    }
                }

                return Status.OK_STATUS;
            }
        };

        job.schedule();
    }

}