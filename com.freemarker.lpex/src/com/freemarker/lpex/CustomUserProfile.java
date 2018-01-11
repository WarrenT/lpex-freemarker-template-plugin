package com.freemarker.lpex;

import org.osgi.framework.Version;

import com.freemarker.lpex.utils.PluginLogger;
import com.freemarker.lpex.utils.TemplateDirectorySyncHelper;
import com.ibm.lpex.core.LpexView;

// TODO: delete obsolete class
@Deprecated
public final class CustomUserProfile {

    private CustomUserProfile() {
    }

    public static void userProfile(LpexView aView) {

        CustomUserProfile tUserProfile = new CustomUserProfile();
        tUserProfile.initialize(aView);

        return;
    }

    private void initialize(LpexView aView) {

        synchronizeTemplates(aView);
        configureKeyBindings(aView);

        aView.doDefaultCommand("set messageText LPEX FreeMarker Actions available with CTRL+Enter" + getVersion());
        PluginLogger.logger.info("LPEX FreeMarker Actions available with CTRL+Enter " + getVersion());
    }

    private void synchronizeTemplates(LpexView aView) {
        PluginLogger.logger.info("Attempting sync of templates...");
        aView.doDefaultCommand("set messageText Attempting sync of templates...");
        TemplateDirectorySyncHelper.getInstance().syncLocalTemplates();
    }

    private void configureKeyBindings(LpexView aView) {
        PluginLogger.logger.info("Configuring key binding...");
        aView.defineAction(Actions.InsertTemplate.ID, new Actions.InsertTemplate());
        aView.doDefaultCommand("set keyAction.c-enter " + Actions.InsertTemplate.ID);
        aView.doDefaultCommand("set keyAction.c-numpadEnter " + Actions.InsertTemplate.ID);
    }

    private String getVersion() {
        Version tVersion = LPEXFreeMarkerPlugin.getVersion();
        return "(" + tVersion.toString() + ")";
    }

}