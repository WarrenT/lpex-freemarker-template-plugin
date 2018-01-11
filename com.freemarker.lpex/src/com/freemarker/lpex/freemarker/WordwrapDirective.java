package com.freemarker.lpex.freemarker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.freemarker.lpex.utils.StringUtil;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

/**
 * FreeMarker user-defined directive that wraps a given text honoring the
 * specified width.
 * <p>
 * <b>Directive info</b>
 * </p>
 * <p>
 * Parameters:
 * <ul>
 * <li><code>text</code>: Text that is wrapped honoring the width specified at
 * <em>width</em>.
 * <li><code>width</code>: Text width.
 * <li><code>return</code>: Name of the list containing the formatted text.
 * </ul>
 * <p>
 * Loop variables: None
 * <p>
 * Directive nested content: No
 * <p>
 * Example:
 * 
 * <pre>
 *  ...+... 1 ...+... 2 ...+... 3 ...+... 4 ...+... 5 ...+... 6 ...+... 7 ...+... 8
 * &lt;@wordwrap text=subroutine.description width=56 return="lines" />
 *          //===========================================================
 * &lt;#list lines as line&gt;
 *          // ${line}
 * &lt;/#list&gt;
 *          //===========================================================
 * </pre>
 */
public class WordwrapDirective implements TemplateDirectiveModel {

    private static final String PARAM_NAME_TEXT = "text";

    private static final String PARAM_NAME_WIDTH = "width";

    private static final String PARAM_NAME_RETURN = "return";

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void execute(Environment anEnvironment, Map aParameterList, TemplateModel[] aLoopVarList, TemplateDirectiveBody aBody)
        throws TemplateException, IOException {

        // Ensure that there is no loop variable given
        if (aLoopVarList.length != 0) {
            throw new TemplateModelException("This directive doesn't allow loop variables.");
        }

        String tText = null;
        Integer tWidth = null;
        String tReturn = null;

        Iterator<Map.Entry<String, TemplateModel>> tParamIterator = aParameterList.entrySet().iterator();
        while (tParamIterator.hasNext()) {

            Map.Entry<String, TemplateModel> tParameter = tParamIterator.next();
            String tParamName = tParameter.getKey();
            TemplateModel tParamValue = tParameter.getValue();

            if (tParamName.equals(PARAM_NAME_TEXT)) {
                if (!(tParamValue instanceof SimpleScalar)) {
                    throw new TemplateModelException(produceMessage("The \"" + PARAM_NAME_TEXT + "\" parameter must be a string."));
                }
                tText = ((SimpleScalar)tParamValue).getAsString();
            } else if (tParamName.equals(PARAM_NAME_WIDTH)) {
                if (!(tParamValue instanceof TemplateNumberModel)) {
                    throw new TemplateModelException(produceMessage("The \"" + PARAM_NAME_WIDTH + "\" parameter must be a number."));
                }
                tWidth = new Integer(((TemplateNumberModel)tParamValue).getAsNumber().intValue());
            } else if (tParamName.equals(PARAM_NAME_RETURN)) {
                if (!(tParamValue instanceof SimpleScalar)) {
                    throw new TemplateModelException(produceMessage("The \"" + PARAM_NAME_RETURN + "\" parameter must be a string."));
                }
                tReturn = ((SimpleScalar)tParamValue).getAsString();
            } else {
                throw new TemplateModelException(produceMessage("Unsupported parameter: " + tParamName));
            }
        }

        if (tText == null) {
            throw new TemplateModelException(produceMessage("The required \"" + PARAM_NAME_TEXT + "\" paramter is missing."));
        }

        if (tWidth == null) {
            throw new TemplateModelException(produceMessage("The required \"" + PARAM_NAME_WIDTH + "\" paramter is missing."));
        }

        if (aLoopVarList.length != 0) {
            throw new TemplateModelException(produceMessage("Loop variables are not allowed."));
        }

        Collection<String> tLines = StringUtil.wordWrap(tText, tWidth, new Locale("de"));

        List<SimpleScalar> tReturnVars = new ArrayList<SimpleScalar>();
        for (String line : tLines) {
            tReturnVars.add(new SimpleScalar(line));
        }
        anEnvironment.setVariable(tReturn, new SimpleSequence(tReturnVars));
    }

    private String produceMessage(String aText) {
        return getClass().getSimpleName() + ": " + aText;
    }
}