package com.freemarker.lpex.formdialogs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

import com.freemarker.lpex.freemarker.FreeMarkerConfig;
import com.freemarker.lpex.userdefined.IUserType;
import com.freemarker.lpex.utils.PluginLogger;
import com.freemarker.lpex.utils.ReflectionUtil;
import com.freemarker.lpex.utils.StringUtil;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class LPEXTemplate {
    private FKLTemplate fklTemplate = null;

    private String templateText = null;

    private Map<String, Object> templateData = null;

    private String result = null;

    private File baseTemplateFolder = null;

    private File templateFile = null;

    private static final String KEY_REPEATS = "repeats";

    public LPEXTemplate(File aBaseTemplateFolder, File aFile) throws Exception {
        baseTemplateFolder = aBaseTemplateFolder;
        templateFile = aFile;
        String tTemplateText = readFileAsString(new File(baseTemplateFolder, templateFile.getPath()));
        if (!tTemplateText.endsWith(StringUtil.newLineChar())) {
            this.templateText = tTemplateText + StringUtil.newLineChar();
        } else {
            this.templateText = tTemplateText;
        }

        // Parse the form configuration that will dictate the form
        // dialog structure
        buildTemplate();

        // Get the form data container ready to receive data
        initializeFormData();
    }

    private void initializeFormData() {
        templateData = new HashMap<String, Object>();
        for (FKLPromptGroup tPromptGroup : fklTemplate.getPromptGroups()) {
            // Create a hash for this prompt group level
            Map<String, Object> tMap = new HashMap<String, Object>();

            // Create a list of 1 or more collections of prompt results
            ArrayList<Map<String, Object>> tRepeats = new ArrayList<Map<String, Object>>();

            // Initialize the first in the list
            // repeats.add(promptGroup.getInitializedMap());

            // Create a set of shortcut fields for the first prompt result
            tMap.putAll(tPromptGroup.getInitializedMap());

            // Add the repeat set
            tMap.put(KEY_REPEATS, tRepeats);

            // Attach this new prompt group level to the root
            templateData.put(tPromptGroup.getName(), tMap);
        }
    }

    public void buildTemplate() throws Exception {
        String tFormXml = getDialogXML();
        if ((tFormXml != "") && (tFormXml != null)) {
            try {
                fklTemplate = new FKLTemplate(this);
            } catch (Exception e) {
                fklTemplate = null;
                throw new Exception("Failed parsing the form XML.", e);
            }
        } else {
            throw new Exception("No form XML found.");
        }
    }

    public FKLTemplate getDialog() throws Exception {
        if (fklTemplate == null) {
            throw new Exception("Template not loaded.");
        }
        return fklTemplate;
    }

    public String merge() throws Exception {

        result = null;

        if (fklTemplate == null) {
            throw new Exception("Template form not loaded.");
        }

        if (templateData == null) {
            throw new Exception("Template data not loaded.");
        }

        updateUserDefinedTypes(templateData);

        removeEmptyRepeats(templateData);

        // Update FreeMarker configuration: Base template directory
        Configuration tConfiguration = FreeMarkerConfig.getInstance().getConfiguration();
        File tBaseDirectory = new File(templateFile.getPath()).getParentFile();
        if (tBaseDirectory != null) {
            tConfiguration.setTemplateLoader(new FileTemplateLoader(baseTemplateFolder));
        }

        // Create the FreeMarker template
        Template tTemplate = new Template(templateFile.getPath().replaceAll("\\\\", "/"), new StringReader(templateText), tConfiguration);

        // Merge template with data model
        ByteArrayOutputStream tStream = new ByteArrayOutputStream();
        Writer tWriter = new OutputStreamWriter(tStream);
        tTemplate.process(templateData, tWriter);
        tWriter.flush();

        result = tStream.toString();

        return result;
    }

    @SuppressWarnings("unchecked")
    private void updateUserDefinedTypes(Map<String, Object> aTemplateData) throws NoSuchMethodException, IllegalAccessException,
        InvocationTargetException {
        Set<Entry<String, Object>> tPromptGroups = aTemplateData.entrySet();
        for (Entry<String, Object> tPromptGroup : tPromptGroups) {
            Object tObject = tPromptGroup.getValue();
            if (tObject instanceof Map) {
                Map<String, Object> tPromptGroupEntry = (Map<String, Object>)tObject;
                ArrayList<Map<String, Object>> tRepeats = (ArrayList<Map<String, Object>>)tPromptGroupEntry.get(KEY_REPEATS);
                if (tRepeats.size() > 0) {
                    updateUserDefinedTypesOfRepeatablePromptGroup(tRepeats);
                } else {
                    updateUserDefinedTypesOfPrompts(tPromptGroupEntry);
                }
            }
        }
    }

    private void updateUserDefinedTypesOfRepeatablePromptGroup(ArrayList<Map<String, Object>> tRepeats) throws NoSuchMethodException,
        IllegalAccessException, InvocationTargetException {
        for (Map<String, Object> tRepeat : tRepeats) {
            updateUserDefinedTypesOfPrompts(tRepeat);
        }
    }

    private void updateUserDefinedTypesOfPrompts(Map<String, Object> tRepeat) throws NoSuchMethodException, IllegalAccessException,
        InvocationTargetException {
        Set<Entry<String, Object>> tPrompts = tRepeat.entrySet();
        for (Entry<String, Object> tPrompt : tPrompts) {
            if (tPrompt.getValue() instanceof IUserType) {
                IUserType tUserDefinedType = (IUserType)tPrompt.getValue();
                Object tData = invokeUserDefinedType(tUserDefinedType, tRepeat);
                tPrompt.setValue(tData);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void removeEmptyRepeats(Map<String, Object> aTemplateData) throws IllegalAccessException, InvocationTargetException {
        Set<Entry<String, Object>> tPromptGroups = aTemplateData.entrySet();
        for (Entry<String, Object> tPromptGroup : tPromptGroups) {
            Object tObject = tPromptGroup.getValue();
            if (tObject instanceof Map) {
                Map<String, Object> tPromptGroupEntry = (Map<String, Object>)tObject;
                ArrayList<Map<String, Object>> tRepeats = (ArrayList<Map<String, Object>>)tPromptGroupEntry.get(KEY_REPEATS);
                if (tRepeats.size() > 0) {
                    removeEmptyRepeatsFromRepeatablePromptGroup(tRepeats);
                }
            }
        }
    }

    private void removeEmptyRepeatsFromRepeatablePromptGroup(ArrayList<Map<String, Object>> aRepeats) {
        while (aRepeats.size() > 0) {
            int i = aRepeats.size() - 1;
            Map<String, Object> tMap = aRepeats.get(i);
            if (!isEmptyMap(tMap)) {
                break;
            }
            aRepeats.remove(i);
        }
        return;
    }

    private boolean isEmptyMap(Map<String, Object> aMap) {
        if (aMap == null) {
            return true;
        }
        if (aMap.size() == 0) {
            return true;
        }
        Collection<Object> tValues = aMap.values();
        if (tValues.size() == 0) {
            return true;
        }
        for (Object tValue : tValues) {
            if (tValue instanceof Map) {
                if (!isEmptyMap((Map)tValue)) {
                    return false;
                }
            } else if (tValue instanceof String) {
                if (((String)tValue).length() > 0) {
                    return false;
                }
            } else if (tValue != null) {
                return false;
            }
        }
        return true;
    }

    private Object invokeUserDefinedType(IUserType aUserDefinedType, Map<String, Object> aMap) throws NoSuchMethodException, IllegalAccessException,
        InvocationTargetException {
        ReflectionUtil.setParameterValues(aUserDefinedType, aMap);
        Object tData = aUserDefinedType.getData();
        return tData;
    }

    @SuppressWarnings("unchecked")
    public String getFormDataAsString() {
        StringBuffer tOut = new StringBuffer("\r\n(root)\r\n");
        // Loop through each prompt group data element
        for (Map.Entry<String, Object> tPromptGroupData : templateData.entrySet()) {
            String tPromptGroupName = tPromptGroupData.getKey();
            Map<String, Object> tPromptGroupMap = (Map<String, Object>)tPromptGroupData.getValue();
            // Loop through each collected set of prompt data
            ArrayList<Map<String, Object>> tRepeats = (ArrayList<Map<String, Object>>)tPromptGroupMap.get(KEY_REPEATS);
            int i = 0;
            tOut.append(" |" + "\r\n");
            tOut.append(" +-" + tPromptGroupName + "[" + tRepeats.size() + "]" + "\r\n");
            for (Map<String, Object> tMap : tRepeats) {
                tOut.append("    |" + "\r\n");
                tOut.append("    +-repeats(" + (i + 1) + ")\r\n");
                for (Map.Entry<String, Object> tPromptData : tMap.entrySet()) {
                    String tKey = tPromptData.getKey();
                    String tValue = tPromptData.getValue().toString();
                    tOut.append("       |" + "\r\n");
                    tOut.append("       +-" + tKey + " = \"" + tValue + "\"\r\n");
                }
                i++;
            }
        }
        return tOut.toString();
    }

    public String getResult() throws Exception {
        if (result == null) {
            result = merge();
        }
        return result;
    }

    public String getName() throws Exception {
        if (fklTemplate == null) {
            throw new Exception("Template form not loaded.");
        }
        return fklTemplate.getName();
    }

    public String getDescription() throws Exception {
        if (fklTemplate == null) {
            throw new Exception("Template form not loaded.");
        }
        return fklTemplate.getDescription();
    }

    public String getDialogXML() throws Exception {
        String tFormBlock = "";
        try {
            String tStartingTag = "<#--";
            String tEndingTag = "-->";
            int tStartingPosition = 0;
            int tEndingPosition = 0;
            tStartingPosition = templateText.indexOf(tStartingTag) + tStartingTag.length();
            tEndingPosition = templateText.indexOf(tEndingTag);
            tFormBlock = templateText.substring(tStartingPosition, tEndingPosition);
        } catch (Exception e) {
            tFormBlock = "";
            throw new Exception("Didn't find any XML in the first comment.", e);
        }
        return tFormBlock;
    }

    @SuppressWarnings("unchecked")
    public void updateData(String aPromptGroupName, String aPromptName, Integer aRepeatIndex, String aValue) {

        Map<String, Object> tPromptGroup = (Map<String, Object>)templateData.get(aPromptGroupName);
        if (aRepeatIndex == null) {
            // Update non-repeatable prompt group
            tPromptGroup.put(aPromptName, aValue);
        } else {
            // Update item of a repeatable prompt group
            ArrayList<Map<String, Object>> tRepeats = (ArrayList<Map<String, Object>>)tPromptGroup.get(KEY_REPEATS);
            Map<String, Object> tItem;
            while (tRepeats.size() - 1 < aRepeatIndex.intValue()) {
                tItem = new HashMap<String, Object>();
                addUserDefinedTypes(aPromptGroupName, tItem);
                tRepeats.add(tItem);
            }
            tItem = tRepeats.get(aRepeatIndex);
            tItem.put(aPromptName, aValue);
        }
    }

    private void addUserDefinedTypes(String aPromptGroupName, Map<String, Object> anItem) {
        FKLPromptGroup tPromptGroup = fklTemplate.getPromptGroup(aPromptGroupName);
        Map<String, Object> tUserDefinedTypes = tPromptGroup.getUserDefinedTypes();
        anItem.putAll(tUserDefinedTypes);
    }

    private String readFileAsString(File aFile) throws java.io.IOException {

        char[] tBuffer = new char[(int)aFile.length()];
        InputStreamReader tReader = null;
        int numBytes;

        try {
            tReader = new InputStreamReader(new FileInputStream(aFile), "utf-8");
            numBytes = tReader.read(tBuffer);
        } finally {
            if (tReader != null) {
                try {
                    tReader.close();
                } catch (IOException e) {
                    PluginLogger.logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
                }
            }
        }
        return new String(tBuffer, 0, numBytes);
    }

}