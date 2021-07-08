package com.freemarker.lpex.freemarker;

import freemarker.template.TemplateDirectiveModel;

public abstract class AbstractDirective implements TemplateDirectiveModel {

    protected String produceMessage(String aText) {
        return getClass().getSimpleName() + ": " + aText;
    }

}
