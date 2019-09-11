package com.freemarker.lpex.freemarker;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.freemarker.lpex.LPEXFreeMarkerPlugin;
import com.ibm.etools.iseries.services.qsys.api.IQSYSDatabaseField;
import com.ibm.etools.iseries.services.qsys.api.IQSYSFileField;
import com.ibm.etools.iseries.services.qsys.api.IQSYSFileRecordFormat;
import com.ibm.etools.iseries.services.qsys.api.IQSYSObject;
import com.ibm.etools.iseries.subsystems.qsys.api.IBMiConnection;
import com.ibm.etools.iseries.subsystems.qsys.objects.QSYSRemoteDatabaseFile;
import com.ibm.etools.iseries.subsystems.qsys.objects.QSYSRemoteDisplayFile;
import com.ibm.etools.iseries.subsystems.qsys.objects.QSYSRemoteFile;
import com.ibm.etools.iseries.subsystems.qsys.objects.QSYSRemotePrinterFile;

import freemarker.core.Environment;
import freemarker.template.SimpleHash;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * FreeMarker user-defined directive that retrieves a field list from a given
 * file.
 * <p>
 * <b>Directive info</b>
 * </p>
 * <p>
 * Parameters:
 * <ul>
 * <li><code>file</code>: Name of the file whose key list is retrieved.
 * <li><code>lib</code>: Name of the library that is searched for the file.
 * Special values: *CURLIB, *LIBL. Default: *LIBL.
 * <li><code>rcdFmt</code>: Name of the record format the key list is retrieved
 * from. Special values: *FIRST. Default: *FIRST.
 * <li><code>prefix</code>: Prefix, which is used to qualify the body variables.
 * Default: no prefix.
 * </ul>
 * <p>
 * Loop variables: 1
 * <p>
 * Directive nested content: Yes
 * <p>
 * Directive body variables:
 * <ul>
 * <li><code>file</code> - File name used.
 * <li><code>lib</code> - File library name used.
 * <li><code>rcdFmt</code> - Record format name used.
 * <li><code>name</code> - Field name.
 * <li><code>text</code> - Description.
 * <li><code>type</code> - Data type: A, B, P, S, L, T, Z, F, G.
 * <li><code>length</code> - Field length.
 * <li><code>decPos</code> - Number of decimal positions.
 * <li><code>refFile</code> - Reference file.
 * <li><code>refLib</code> - Reference file library name.
 * <li><code>refRcdFmt</code> - Reference file record format name.
 * <li><code>refFld</code> - Reference field name.
 * </ul>
 * <p>
 * Example:
 * 
 * <pre>
 *  ...+... 1 ...+... 2 ...+... 3 ...+... 4 ...+... 5 ...+... 6 ...+... 7 ...+... 8 ...+... 9 ...+... 0 ...+... 1 ...+... 2 ...+... 3
 * &lt;@fieldList file=fields.file lib=fields.library rcdFmt=fields.recordFormat prefix="field" ; cnt>
 * &lt;#if cnt == 1>
 *           //  Datei     : ${field.lib}/${field.file}
 *           //  Satzformat: ${field.rcdFmt}
 * &lt;/#if>
 *           //  ${cnt?string?left_pad(3)}.  ${field.name?right_pad(10)}  ${field.type}(${field.length?left_pad(5)},${field.decPos?left_pad(2)}) Ref: ${field.refLib}/${field.refFile}(${field.refRcdFmt}).${field.refFld}
 * &lt;/@fieldList>
 * </pre>
 */
public class FieldListDirective extends AbstractDirective {

    /**
     * Input parameters of the directive
     */
    private static final String PARAM_NAME_FILE = "file";

    private static final String PARAM_NAME_LIBRARY = "lib";

    private static final String PARAM_NAME_RECORD_FORMAT = "rcdFmt";

    private static final String PARAM_NAME_PREFIX = "prefix";

    /**
     * Body variables of the directive
     */
    private static final String BODY_VARIABLE_FILE = "file";

    private static final String BODY_VARIABLE_LIBRARY = "lib";

    private static final String BODY_VARIABLE_RECORD_FORMAT = "rcdFmt";

    private static final String BODY_VARIABLE_FIELD_NAME = "name";

    private static final String BODY_VARIABLE_FIELD_TEXT = "text";

    private static final String BODY_VARIABLE_FIELD_TYPE = "type";

    private static final String BODY_VARIABLE_FIELD_LENGTH = "length";

    private static final String BODY_VARIABLE_FIELD_DECIMAL_POSITION = "decPos";

    private static final String BODY_VARIABLE_FIELD_REFERENCED_FILE = "refFile";

    private static final String BODY_VARIABLE_FIELD_REFERENCED_LIBRARY = "refLib";

    private static final String BODY_VARIABLE_FIELD_REFERENCED_RECORD_FORMAT = "refRcdFmt";

    private static final String BODY_VARIABLE_FIELD_REFERENCED_FIELD = "refFld";

    /**
     * Default values
     */
    private static final String DEFAULT_LIBRARY = "*LIBL";

    private static final String DEFAULT_RECORD_FORMAT = "*FIRST";

    @Override
    @SuppressWarnings("all")
    public void execute(Environment anEnvironment, Map aParameterList, TemplateModel[] aLoopVarList, TemplateDirectiveBody aBody)
        throws TemplateException, IOException {

        String tFile = null;
        String tLibrary = DEFAULT_LIBRARY;
        String tRecordFormat = DEFAULT_RECORD_FORMAT;
        String tPrefix = null;

        Iterator<Map.Entry<String, TemplateModel>> tParameterIterator = aParameterList.entrySet().iterator();
        while (tParameterIterator.hasNext()) {

            Map.Entry<String, TemplateModel> tParameter = tParameterIterator.next();
            String tParamName = tParameter.getKey();
            TemplateModel tParamValue = tParameter.getValue();

            if (tParamName.equals(PARAM_NAME_FILE)) {
                if (!(tParamValue instanceof SimpleScalar)) {
                    throw new TemplateModelException(produceMessage("The \"" + PARAM_NAME_FILE + "\" parameter must be a string."));
                }
                tFile = ((SimpleScalar)tParamValue).getAsString();
            } else if (tParamName.equals(PARAM_NAME_LIBRARY)) {
                if (!(tParamValue instanceof SimpleScalar)) {
                    throw new TemplateModelException(produceMessage("The \"" + PARAM_NAME_LIBRARY + "\" parameter must be a string."));
                }
                if (((SimpleScalar)tParamValue).getAsString().length() > 0) {
                    tLibrary = ((SimpleScalar)tParamValue).getAsString();
                }
            } else if (tParamName.equals(PARAM_NAME_RECORD_FORMAT)) {
                if (!(tParamValue instanceof SimpleScalar)) {
                    throw new TemplateModelException(produceMessage("The \"" + PARAM_NAME_RECORD_FORMAT + "\" parameter must be a string."));
                }
                if (((SimpleScalar)tParamValue).getAsString().length() > 0) {
                    tRecordFormat = ((SimpleScalar)tParamValue).getAsString();
                }
            } else if (tParamName.equals(PARAM_NAME_PREFIX)) {
                if (tParamValue instanceof SimpleScalar) {
                    tPrefix = ((SimpleScalar)tParamValue).getAsString();
                }
            } else {
                throw new TemplateModelException(produceMessage("Unsupported parameter: " + tParamName));
            }
        }

        if (tFile == null) {
            throw new TemplateModelException(produceMessage("The required \"" + PARAM_NAME_FILE + "\" paramter is missing."));
        }

        if (tLibrary == null) {
            throw new TemplateModelException(produceMessage("The required \"" + PARAM_NAME_LIBRARY + "\" paramter is missing."));
        }

        if (tRecordFormat == null) {
            throw new TemplateModelException(produceMessage("The required \"" + PARAM_NAME_RECORD_FORMAT + "\" paramter is missing."));
        }

        if (aLoopVarList.length > 1) {
            throw new TemplateModelException(produceMessage("At most one loop variable is allowed."));
        }

        // Retrieve the key field list
        FieldList tKeyList = new FieldList(tFile, tLibrary, tRecordFormat);

        // If there is non-empty nested content:
        int i = 0;
        if (aBody != null && tKeyList.getFields().size() > 0) {
            for (Field tField : tKeyList.getFields()) {
                i++;

                // Set the loop variable, if there is one:
                if (aLoopVarList.length > 0) {
                    aLoopVarList[0] = new SimpleNumber(i);
                }

                // Produce data model:
                Map<String, TemplateModel> tLocalVars = new HashMap<String, TemplateModel>();

                tLocalVars.put(BODY_VARIABLE_FILE, new SimpleScalar(tKeyList.getFile()));
                tLocalVars.put(BODY_VARIABLE_LIBRARY, new SimpleScalar(tKeyList.getLibrary()));
                tLocalVars.put(BODY_VARIABLE_RECORD_FORMAT, new SimpleScalar(tKeyList.getRecordFormat()));

                tLocalVars.put(BODY_VARIABLE_FIELD_NAME, new SimpleScalar(tField.getName()));
                tLocalVars.put(BODY_VARIABLE_FIELD_TEXT, new SimpleScalar(tField.getText()));
                tLocalVars.put(BODY_VARIABLE_FIELD_TYPE, new SimpleScalar(tField.getType()));
                tLocalVars.put(BODY_VARIABLE_FIELD_LENGTH, new SimpleScalar(tField.getLength().toString()));
                if (tField.isNumeric()) {
                    tLocalVars.put(BODY_VARIABLE_FIELD_DECIMAL_POSITION, new SimpleScalar(tField.getDecimalPosition().toString()));
                } else {
                    tLocalVars.put(BODY_VARIABLE_FIELD_DECIMAL_POSITION, new SimpleScalar(""));
                }
                tLocalVars.put(BODY_VARIABLE_FIELD_REFERENCED_FILE, new SimpleScalar(tField.getReferencedFile()));
                tLocalVars.put(BODY_VARIABLE_FIELD_REFERENCED_LIBRARY, new SimpleScalar(tField.getReferencedLibrary()));
                tLocalVars.put(BODY_VARIABLE_FIELD_REFERENCED_RECORD_FORMAT, new SimpleScalar(tField.getReferencedRecordFormat()));
                tLocalVars.put(BODY_VARIABLE_FIELD_REFERENCED_FIELD, new SimpleScalar(tField.getReferencedField()));

                if (tPrefix != null && tPrefix.length() > 0) {
                    anEnvironment.setVariable(tPrefix, new SimpleHash(tLocalVars));
                } else {
                    for (String tKey : tLocalVars.keySet()) {
                        TemplateModel tValue = tLocalVars.get(tKey);
                        anEnvironment.setVariable(tKey, tValue);
                    }
                }

                aBody.render(anEnvironment.getOut());
            }
        } else {
            throw new RuntimeException(produceMessage("missing body / empty field list"));
        }

    }

    /**
     * Represents the key list of a keyed physical file.
     * 
     * @author Thomas Raddatz
     */
    private class FieldList {

        private String file = null;

        private String library = null;

        private String recordFormat = null;

        private List<Field> fieldList = null;

        public FieldList(String aFile, String aLibrary, String aRecordFormat) {
            file = aFile;
            library = aLibrary;
            recordFormat = aRecordFormat;
            fieldList = new ArrayList<Field>();

            try {
                initialize();
            } catch (Exception e) {
                LPEXFreeMarkerPlugin.logError("FieldListDirective: Could not retrieve field list", e);
            }
        }

        @SuppressWarnings("unused")
        public void setFile(String aFile) {
            file = aFile;
        }

        public String getFile() {
            return file;
        }

        public void setLibrary(String aLibrary) {
            library = aLibrary;
        }

        public String getLibrary() {
            return library;
        }

        public void setRecordFormat(String aRecordFormat) {
            recordFormat = aRecordFormat;
        }

        public String getRecordFormat() {
            return recordFormat;
        }

        public void addField(Field aField) {
            fieldList.add(aField);
        }

        public List<Field> getFields() {
            return fieldList;
        }

        private void initialize() throws Exception {
            IBMiConnection tConnection = FreeMarkerConfig.getInstance().getCurrentConnection();

            IQSYSObject tObject = tConnection.getObject(getLibrary(), getFile(), "*FILE", null);
            if (tObject instanceof QSYSRemoteDatabaseFile) {
                QSYSRemoteDatabaseFile tDatabaseFile = (QSYSRemoteDatabaseFile)tObject;
                loadDatabaseFileFields(tDatabaseFile);
            } else if (tObject instanceof QSYSRemoteDisplayFile) {
                QSYSRemoteFile tDisplayFile = (QSYSRemoteFile)tObject;
                loadDisplayAndPrinterFileFields(tDisplayFile);
            } else if (tObject instanceof QSYSRemotePrinterFile) {
                QSYSRemoteFile tPrinterFile = (QSYSRemoteFile)tObject;
                loadDisplayAndPrinterFileFields(tPrinterFile);
            } else {
                throw new SQLException("File not found: " + getLibrary() + "/" + getFile());
            }
        }

        private void loadDisplayAndPrinterFileFields(QSYSRemoteFile tFile) throws Exception {
            setLibrary(tFile.getLibrary());
            setRecordFormat(resolveRecordFormat(tFile, getRecordFormat()));

            IBMiConnection tConnection = FreeMarkerConfig.getInstance().getCurrentConnection();
            IQSYSFileField[] tQSYSFields = tConnection.listFields(getLibrary(), getFile(), getRecordFormat(), null);
            for (IQSYSFileField tQSYSField : tQSYSFields) {
                Field tField = new Field(tQSYSField);
                addField(tField);
            }
        }

        private void loadDatabaseFileFields(QSYSRemoteDatabaseFile tFile) throws Exception {
            setLibrary(tFile.getLibrary());
            setRecordFormat(resolveRecordFormat(tFile, getRecordFormat()));

            IBMiConnection tConnection = FreeMarkerConfig.getInstance().getCurrentConnection();
            IQSYSDatabaseField tQSYSFields[] = tConnection.getQSYSObjectSubSystem().listDatabaseFields(tFile.getRecordFormat(null), null);
            for (IQSYSDatabaseField tQSYSField : tQSYSFields) {
                Field tField = new Field(tQSYSField);
                addField(tField);
            }
        }

        private String resolveRecordFormat(QSYSRemoteFile aFile, String aFormat) {
            try {
                IQSYSFileRecordFormat tRecordFormat = aFile.getRecordFormat(aFormat, null);
                return tRecordFormat.getName();
            } catch (Exception e) {
                throw new RuntimeException(
                    produceMessage("Record format '" + aFormat + "' of file '" + aFile.getLibrary() + "/" + aFile.getName() + "' not found."));
            }
        }
    }

    /**
     * Represents a field of a file.
     * 
     * @author Thomas Raddatz
     */
    private class Field {
        private String name = null;

        private String text = null;

        private String type = null;

        private Integer length = null;

        private Integer decimalPosition = null;

        private String referencedField = null;

        private String referencedFile = null;

        private String referencedLibrary = null;

        private String referencedRecordFormat = null;

        public Field(IQSYSFileField tField) {
            setName(tField.getName());
            setText(tField.getDescription());
            setType(tField.getDataType());
            setLength(tField.getLength());
            setDecimalPosition(tField.getDecimalPosition());
            if (tField instanceof IQSYSDatabaseField) {
                IQSYSDatabaseField tDatabaseField = (IQSYSDatabaseField)tField;
                setAbsoluteReferencedField(tDatabaseField.getReferencedField());
            }
        }

        public void setName(String aName) {
            name = aName;
        }

        public String getName() {
            return name;
        }

        public void setText(String aText) {
            text = aText;
        }

        public String getText() {
            return text;
        }

        public void setType(char aType) {
            type = String.valueOf(aType);
        }

        public String getType() {
            return type;
        }

        public void setLength(Integer aLength) {
            length = aLength;
        }

        public Integer getLength() {
            return length;
        }

        public void setDecimalPosition(Integer aDecimalPosition) {
            decimalPosition = aDecimalPosition;
        }

        public Integer getDecimalPosition() {
            return decimalPosition;
        }

        public void setAbsoluteReferencedField(String aReferencedField) {
            if (aReferencedField == null) {
                referencedLibrary = null;
                referencedFile = null;
                referencedRecordFormat = null;
                referencedField = null;
                return;
            }
            String[] tParts = aReferencedField.split("[ ]+");
            if (tParts.length >= 1) {
                referencedLibrary = tParts[0];
            }
            if (tParts.length >= 2) {
                referencedFile = tParts[1];
            }
            if (tParts.length >= 3) {
                referencedRecordFormat = tParts[2];
            }
            if (tParts.length >= 4) {
                referencedField = tParts[3];
            }
        }

        @SuppressWarnings("unused")
        public void setReferencedField(String aReferencedField) {
            referencedField = aReferencedField;
        }

        public String getReferencedField() {
            return referencedField;
        }

        @SuppressWarnings("unused")
        public void setReferencedFile(String aReferencedFile) {
            referencedFile = aReferencedFile;
        }

        public String getReferencedFile() {
            return referencedFile;
        }

        @SuppressWarnings("unused")
        public void setReferencedLibrary(String aReferencedLibrary) {
            referencedLibrary = aReferencedLibrary;
        }

        public String getReferencedLibrary() {
            return referencedLibrary;
        }

        @SuppressWarnings("unused")
        public void setReferencedRecordFormat(String aReferencedRecordFormat) {
            referencedRecordFormat = aReferencedRecordFormat;
        }

        public String getReferencedRecordFormat() {
            return referencedRecordFormat;
        }

        public boolean isNumeric() {
            return "S".equals(getType()) || "P".equals(getType());
        }

    }

}
