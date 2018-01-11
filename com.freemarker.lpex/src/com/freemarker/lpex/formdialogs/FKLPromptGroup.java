package com.freemarker.lpex.formdialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.freemarker.lpex.utils.PluginLogger;
import com.freemarker.lpex.utils.XmlUtil;

public class FKLPromptGroup {

    private String name;

    private boolean repeatable = false;

    private int maxRepeats = 0;

    private ArrayList<FKLAbstractPrompt> prompts = new ArrayList<FKLAbstractPrompt>();

    public FKLPromptGroup() {
    }

    public String getName() {
        return name;
    }

    public void setName(String aName) {
        this.name = aName;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean aRepeatable) {
        this.repeatable = aRepeatable;
    }

    public void setRepeatable(String aRepeatable) {
        setRepeatable(XmlUtil.parseBoolean(aRepeatable));
    }

    public int getMaxRepeats() {
        return maxRepeats;
    }

    public void setMaxRepeats(int aMaxRepeats) {
        this.maxRepeats = aMaxRepeats;
    }

    public void setMaxRepeats(String aMaxRepeats) {
        if (aMaxRepeats != null) {
            try {
                setMaxRepeats(Integer.parseInt(aMaxRepeats));
            } catch (Exception e) {
                setMaxRepeats(0);
            }
        }
    }

    public ArrayList<FKLAbstractPrompt> getPrompts() {
        return prompts;
    }

    public void addPrompt(FKLAbstractPrompt prompt) throws Exception {
        if ((this.name == null) || (this.name == "")) {
            throw new Exception("Prompt group must have a name.");
        }
        prompts.add(prompt);
    }

    public Map<String, Object> getInitializedMap() {
        Map<String, Object> tInitializedMap = new HashMap<String, Object>();
        for (FKLAbstractPrompt tPrompt : getPrompts()) {
            if (tPrompt instanceof FKLPromptUserDefined) {
                addUserDefinedType((FKLPromptUserDefined)tPrompt, tInitializedMap);
            }
        }
        return tInitializedMap;
    }

    public Map<String, Object> getUserDefinedTypes() {
        Map<String, Object> tUserDefinedTypes = new HashMap<String, Object>();
        for (FKLAbstractPrompt tPrompt : getPrompts()) {
            if (tPrompt instanceof FKLPromptUserDefined) {
                addUserDefinedType((FKLPromptUserDefined)tPrompt, tUserDefinedTypes);
            }
        }
        return tUserDefinedTypes;
    }

    public void addUserDefinedType(FKLPromptUserDefined aPrompt, Map<String, Object> aMap) {
        String tClassName = null;
        try {
            tClassName = aPrompt.getClassName();
            Class<?> tClass = Class.forName(aPrompt.getClassName());
            Object tObject = tClass.newInstance();
            aMap.put(aPrompt.getName(), tObject);
        } catch (ClassNotFoundException e) {
            PluginLogger.logger.severe("Class not found: " + tClassName);
        } catch (IllegalAccessException e) {
            PluginLogger.logger.severe("Illegal access to class: " + tClassName);
        } catch (InstantiationException e) {
            PluginLogger.logger.severe("Could not instantiate object from class: " + tClassName);
        }
    }

    public static class Factory {

        public static FKLPromptGroup parse(Element anXmlElement) throws Exception {

            String tName = XmlUtil.getRequiredAttribute(anXmlElement, FKLTemplate.XML_NAME);
            String tRepeatable = XmlUtil.getRequiredAttribute(anXmlElement, FKLTemplate.XML_REPEATABLE);
            String tMaxRepeats = XmlUtil.getOptionalAttribute(anXmlElement, FKLTemplate.XML_MAX_REPEATS, "");

            FKLPromptGroup tPromptGroup = new FKLPromptGroup();
            tPromptGroup.setName(tName);
            tPromptGroup.setRepeatable(tRepeatable);
            tPromptGroup.setMaxRepeats(tMaxRepeats);

            // Process prompts
            NodeList tNodes = anXmlElement.getElementsByTagName(FKLTemplate.XML_PROMPT);
            if (tNodes == null) {
                return tPromptGroup;
            }

            for (int i = 0; i < tNodes.getLength(); i++) {
                Node tPromptGroupNode = tNodes.item(i);
                if (tPromptGroupNode instanceof Element) {
                    FKLAbstractPrompt tPrompt = FKLAbstractPrompt.Factory.parse((Element)tPromptGroupNode);
                    tPromptGroup.addPrompt(tPrompt);
                }
            }

            return tPromptGroup;
        }
    }

}