/*******************************************************************************
 * Copyright (c) 2012-2016 iSphere Project Owners
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/

package com.freemarker.lpex;

import java.util.ArrayList;
import java.util.List;

import com.ibm.lpex.core.LpexView;

/**
 * This class extends the popup menue of the Lpex editor. It adds the following
 * options:
 * <ul>
 * <li>Edit STRPREPRC header</li>
 * <li>Remove STRPREPRC header</li>
 * </ul>
 */
public class MenuExtension {

    private static final String MENU_NAME = "LPEX-FREEMARKER-PLUGIN";
    private static final String BEGIN_SUB_MENU = "beginSubmenu"; //$NON-NLS-1$
    private static final String END_SUB_MENU = "endSubmenu"; //$NON-NLS-1$
    private static final String SEPARATOR = "separator"; //$NON-NLS-1$
    private static final String DOUBLE_QUOTES = "\""; //$NON-NLS-1$
    private static final String SPACE = " "; //$NON-NLS-1$

    public void initializeLpexEditor() {

        LPEXFreeMarkerPlugin.getDefault().setLpexMenuExtension(this);

        LpexView.doGlobalCommand("set default.updateProfile.userActions "
            + getLPEXEditorUserActions(LpexView.globalQuery("current.updateProfile.userActions")));
        LpexView.doGlobalCommand("set default.updateProfile.userKeyActions "
            + getLPEXEditorUserKeyActions(LpexView.globalQuery("current.updateProfile.userKeyActions")));
        LpexView.doGlobalCommand("set default.popup " + getLPEXEditorPopupMenu(LpexView.globalQuery("current.popup")));
    }

    public void uninstall() {

        removeUserActions();
        removeUserKeyActions();
        removePopupMenu();
    }

    private void removeUserActions() {

        StringBuilder existingActions = new StringBuilder(LpexView.globalQuery("current.updateProfile.userActions"));
        ArrayList<String> actions = getUserActions();

        int start;
        for (String action : actions) {
            if ((start = existingActions.indexOf(action)) >= 0) {
                int end = start + action.length();
                existingActions.replace(start, end, "");
            }
        }

        LpexView.doGlobalCommand("set default.updateProfile.userActions " + existingActions.toString().trim());
    }

    private void removeUserKeyActions() {

        StringBuilder existingActions = new StringBuilder(LpexView.globalQuery("current.updateProfile.userKeyActions"));
        ArrayList<String> actions = getUserKeyActions();

        int start;
        for (String action : actions) {
            if ((start = existingActions.indexOf(action)) >= 0) {
                int end = start + action.length();
                existingActions.replace(start, end, "");
            }
        }

        LpexView.doGlobalCommand("set default.updateProfile.userKeyActions " + existingActions.toString().trim());
    }

    private void removePopupMenu() {

        String popupMenu = LpexView.globalQuery("current.popup");
        popupMenu = removeSubMenu(MENU_NAME, popupMenu);

        LpexView.doGlobalCommand("set default.popup " + popupMenu.trim());
    }

    private String getLPEXEditorUserActions(String existingActions) {

        ArrayList<String> actions = getUserActions();

        StringBuilder newUserActions = new StringBuilder();

        if ((existingActions == null) || (existingActions.equalsIgnoreCase("null"))) {
            for (String action : actions) {
                newUserActions.append(action + " ");
            }
        }

        else {
            newUserActions.append(existingActions + " ");
            for (String action : actions) {
                if (existingActions.indexOf(action) < 0) {
                    newUserActions.append(action + " ");
                }
            }
        }

        return newUserActions.toString();
    }

    private ArrayList<String> getUserActions() {

        ArrayList<String> actions = new ArrayList<String>();
        actions.add(Actions.InsertTemplate.ID + " " + Actions.InsertTemplate.class.getName());

        return actions;
    }

    private String getLPEXEditorUserKeyActions(String existingUserKeyActions) {

        ArrayList<String> actions = getUserKeyActions();

        StringBuilder newUserKeyActions = new StringBuilder();

        if ((existingUserKeyActions == null) || (existingUserKeyActions.equalsIgnoreCase("null"))) {
            for (String action : actions) {
                newUserKeyActions.append(action + " ");
            }
        }

        else {
            newUserKeyActions.append(existingUserKeyActions + " ");
            for (String action : actions) {
                if (existingUserKeyActions.indexOf(action) < 0) {
                    newUserKeyActions.append(action + " ");
                }
            }
        }

        return newUserKeyActions.toString();
    }

    private ArrayList<String> getUserKeyActions() {

        ArrayList<String> actions = new ArrayList<String>();
        actions.add("c-enter " + Actions.InsertTemplate.ID);
        actions.add("c-numpadEnter " + Actions.InsertTemplate.ID);

        return actions;
    }

    private StringBuilder appendKeyAction(StringBuilder newKeyActions, String keyAction) {

        if (newKeyActions.indexOf(keyAction) < 0) {
            if (newKeyActions.length() != 0) {
                newKeyActions.append(" ");
            }
            newKeyActions.append(keyAction);
        }

        return newKeyActions;
    }

    private String getLPEXEditorPopupMenu(String popupMenu) {

        ArrayList<String> menuActions = new ArrayList<String>();

        popupMenu = removeSubMenu(MENU_NAME, popupMenu);
        String newMenu = createSubMenu(MENU_NAME, menuActions);

        if (popupMenu != null && popupMenu.contains(newMenu)) {
            return popupMenu;
        }

        if (popupMenu != null) {
            return newMenu + SPACE + popupMenu;
        }

        return newMenu;
    }

    private String removeSubMenu(String subMenu, String menu) {

        int start = menu.indexOf(createStartMenuTag(subMenu));
        if (start < 0) {
            return menu;
        }

        String endSubMenu = createEndMenuTag();
        int end = menu.indexOf(endSubMenu, start);
        if (end < 0) {
            return menu;
        }

        StringBuilder newMenu = new StringBuilder();
        newMenu.append(menu.substring(0, start));
        newMenu.append(menu.substring(end + endSubMenu.length()));

        return newMenu.toString();
    }

    private String createSubMenu(String menu, List<String> menuActions) {

        String startMenu = createStartMenuTag(menu);

        StringBuilder newMenu = new StringBuilder();
        newMenu.append(startMenu);
        newMenu.append(SPACE);
        for (String action : menuActions) {
            if (action == null) {
                newMenu.append(createMenuItem(SEPARATOR));
            } else {
                newMenu.append(createMenuItem(action));
            }
        }

        newMenu.append(createEndMenuTag());

        return newMenu.toString();
    }

    private String createStartMenuTag(String subMenu) {
        return BEGIN_SUB_MENU + SPACE + DOUBLE_QUOTES + subMenu + DOUBLE_QUOTES;
    }

    private String createMenuItem(String action) {
        return action + SPACE;
    }

    private String createEndMenuTag() {
        return END_SUB_MENU + SPACE + createMenuItem(SEPARATOR);
    }
}
