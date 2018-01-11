package com.freemarker.lpex.freemarker;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ibm.etools.iseries.comm.interfaces.IISeriesHostKeyField;
import com.ibm.etools.iseries.services.qsys.api.IQSYSObject;
import com.ibm.etools.iseries.subsystems.qsys.api.IBMiConnection;
import com.ibm.etools.iseries.subsystems.qsys.objects.QSYSRemoteDatabaseFile;

import freemarker.core.Environment;
import freemarker.template.SimpleHash;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

/**
 * FreeMarker user-defined directive that retrieves a key list from a given
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
 * <li><code>numFields</code>: Maximum number of key fields that are returned.
 * -1 = all fields. Default: -1.
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
 * <li><code>name</code> - Name of the key field.
 * <li><code>orderBy</code> - OderBy clause of the key field: A=ascending,
 * D=descending.
 * </ul>
 * <p>
 * Example:
 * 
 * <pre>
 *  ...+... 1 ...+... 2 ...+... 3 ...+... 4 ...+... 5 ...+... 6 ...+... 7 ...+... 8 ...+... 9 ...+... 0 ...+... 1 ...+... 2 ...+... 3
 * &lt;@keyList file=fields.file lib=fields.library rcdFmt=fields.recordFormat prefix="keyField" numFields=fields.numFields ; cnt>
 * &lt;#if cnt == 1>
 *           //  Datei     : ${keyField.lib}/${keyField.file}
 *           //  Satzformat: ${keyField.rcdFmt}
 * &lt;/#if>
 *           //  ${cnt?string?left_pad(3)}.  ${keyField.name?right_pad(10)}  -  (${keyField.orderBy})
 * &lt;/@keyList>
 * </pre>
 */
public class KeyListDirective implements TemplateDirectiveModel {

    /**
     * Input parameters of the directive
     */
    private static final String PARAM_NAME_FILE = "file";

    private static final String PARAM_NAME_LIBRARY = "lib";

    private static final String PARAM_NAME_RECORD_FORMAT = "rcdFmt";

    private static final String PARAM_NAME_NUM_FIELDS = "numFields";

    private static final String PARAM_NAME_PREFIX = "prefix";

    /**
     * Body variables of the directive
     */
    private static final String BODY_VARIABLE_FILE = "file";

    private static final String BODY_VARIABLE_LIBRARY = "lib";

    private static final String BODY_VARIABLE_RECORD_FORMAT = "rcdFmt";

    private static final String BODY_VARIABLE_FIELD_NAME = "name";

    private static final String BODY_VARIABLE_FIELD_ORDER_BY = "orderBy";

    /**
     * Default values
     */
    private static final String DEFAULT_LIBRARY = "*LIBL";

    private static final String DEFAULT_RECORD_FORMAT = "*FIRST";

    private static final int DEFAULT_NUM_KEYS_ALL = -1;

    @Override
    @SuppressWarnings("all")
    public void execute(Environment anEnvironment, Map aParameterList, TemplateModel[] aLoopVarList, TemplateDirectiveBody aBody)
        throws TemplateException, IOException {

        String tFile = null;
        String tLibrary = DEFAULT_LIBRARY;
        String tRecordFormat = DEFAULT_RECORD_FORMAT;
        int tNumFields = DEFAULT_NUM_KEYS_ALL;
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
            } else if (tParamName.equals(PARAM_NAME_NUM_FIELDS)) {
                if (tParamValue instanceof TemplateNumberModel) {
                    tNumFields = new Integer(((TemplateNumberModel)tParamValue).getAsNumber().intValue());
                } else if (tParamValue instanceof SimpleScalar) {
                    try {
                        if (((SimpleScalar)tParamValue).getAsString().length() > 0) {
                            tNumFields = Integer.parseInt(((SimpleScalar)tParamValue).getAsString());
                        }
                    } catch (NumberFormatException e) {
                        throw new TemplateModelException(produceMessage("Invalid number format: " + tParamValue));
                    }
                } else {
                    throw new TemplateModelException(produceMessage("The \"" + PARAM_NAME_NUM_FIELDS + "\" parameter must be a number."));
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
        KeyList tKeyList = new KeyList(tFile, tLibrary, tRecordFormat);

        // If there is non-empty nested content:
        int i = 0;
        if (aBody != null && tKeyList.getFields().size() > 0) {
            for (KeyField tField : tKeyList.getFields()) {
                i++;
                if (tNumFields > 0 && i > tNumFields) {
                    break;
                }

                // Set the loop variable, if there is one:
                if (aLoopVarList.length > 0) {
                    aLoopVarList[0] = new SimpleNumber(i);
                }

                // Produce data model:
                Map<String, SimpleScalar> tLocalVars = new HashMap<String, SimpleScalar>();

                tLocalVars.put(BODY_VARIABLE_FILE, new SimpleScalar(tKeyList.getFile()));
                tLocalVars.put(BODY_VARIABLE_LIBRARY, new SimpleScalar(tKeyList.getLibrary()));
                tLocalVars.put(BODY_VARIABLE_RECORD_FORMAT, new SimpleScalar(tKeyList.getRecordFormat()));

                tLocalVars.put(BODY_VARIABLE_FIELD_NAME, new SimpleScalar(tField.getName()));
                tLocalVars.put(BODY_VARIABLE_FIELD_ORDER_BY, new SimpleScalar(tField.getOrderBy()));

                if (tPrefix != null && tPrefix.length() > 0) {
                    anEnvironment.setVariable(tPrefix, new SimpleHash(tLocalVars));
                } else {
                    for (String tKey : tLocalVars.keySet()) {
                        SimpleScalar tValue = tLocalVars.get(tKey);
                        anEnvironment.setVariable(tKey, tValue);
                    }
                }

                aBody.render(anEnvironment.getOut());
            }
        } else {
            throw new RuntimeException(produceMessage("missing body / key fields"));
        }

    }

    private String produceMessage(String aText) {
        return getClass().getSimpleName() + ": " + aText;
    }

    /**
     * Represents the key list of a keyed physical file.
     * 
     * @author Thomas Raddatz
     */
    private class KeyList {
        private static final int FILE_KEY_DESCENDING = 0x80;

        private String file = null;

        private String library = null;

        private String recordFormat = null;

        private List<KeyField> keyList = null;

        public KeyList(String aFile, String aLibrary, String aRecordFormat) {
            file = aFile;
            library = aLibrary;
            recordFormat = aRecordFormat;
            keyList = new ArrayList<KeyField>();

            try {
                initialize();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
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

        public void addField(KeyField aField) {
            keyList.add(aField);
        }

        public List<KeyField> getFields() {
            return keyList;
        }

        private void initialize() throws Exception {
            IBMiConnection tConnection = FreeMarkerConfig.getInstance().getCurrentConnection();

            IQSYSObject tObject = tConnection.getObject(getLibrary(), getFile(), "*FILE", null);
            if (!(tObject instanceof QSYSRemoteDatabaseFile)) {
                throw new SQLException("File not found: " + getLibrary() + "/" + getFile());
            }

            QSYSRemoteDatabaseFile tFile = (QSYSRemoteDatabaseFile)tObject;
            setLibrary(tFile.getLibrary());
            setRecordFormat(tFile.getRecordFormat(null).getName());

            IISeriesHostKeyField tQSYSKeyFields[] = tConnection.getQSYSObjectSubSystem().listKeyFields(tFile, getRecordFormat());
            for (IISeriesHostKeyField tQSYSKeyField : tQSYSKeyFields) {
                String tOrderBy;
                if ((tQSYSKeyField.getAttributes() & FILE_KEY_DESCENDING) == FILE_KEY_DESCENDING) {
                    tOrderBy = "D";
                } else {
                    tOrderBy = "A";
                }
                KeyField tKeyField = new KeyField(tQSYSKeyField.getName(), tOrderBy);
                addField(tKeyField);
            }
        }
    }

    /**
     * Represents a key field of a key list of a keyed file.
     * 
     * @author Thomas Raddatz
     */
    private class KeyField {
        private String name = null;

        private String orderBy = null;

        public KeyField(String aName, String anOrderBy) {
            name = aName;
            orderBy = anOrderBy;
        }

        @SuppressWarnings("unused")
        public void setName(String aName) {
            name = aName;
        }

        public String getName() {
            return name;
        }

        @SuppressWarnings("unused")
        public void setOrderBy(String anOrderBy) {
            orderBy = anOrderBy;
        }

        public String getOrderBy() {
            return orderBy;
        }

    }

}
