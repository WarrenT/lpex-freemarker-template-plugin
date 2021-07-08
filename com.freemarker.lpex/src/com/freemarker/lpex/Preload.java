package com.freemarker.lpex;

import com.ibm.lpex.alef.LpexPreload;

public class Preload implements LpexPreload {

    public Preload() {
        return;
    }

    public void preload() {

        MenuExtension menuExtension = new MenuExtension();
        menuExtension.initializeLpexEditor();

        return;
    }
}