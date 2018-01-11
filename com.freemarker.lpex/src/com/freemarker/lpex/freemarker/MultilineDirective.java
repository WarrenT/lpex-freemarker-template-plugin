package com.freemarker.lpex.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import com.freemarker.lpex.utils.StringUtil;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

/**
 * FreeMarker user-defined directive that causes the content to wrap honoring
 * the maximum width and prefix string for each new line created.
 * <p>
 * <b>Directive info</b>
 * </p>
 * <p>
 * Parameters:
 * <ul>
 * <li><code>text</code>: Text that is inserted respecting the maximum column
 * specified at <em>width</em>.
 * <li><code>width</code>: Text width.
 * <li><code>bullet</code>: (optional) Character and an optional delimiter to
 * prefix each new line.
 * </ul>
 * <p>
 * Loop variables: None
 * <p>
 * Directive nested content: Yes
 * <p>
 * Additional information: Text starts at the <em>%{text}</em> tag and the loop
 * counter starts at the <em>%{i}</em> tag.
 * <p>
 * Example 1:
 * 
 * <pre>
 *  ...+... 1 ...+... 2 ...+... 3 ...+... 4 ...+... 5 ...+... 6 ...+... 7 ...+... 8
 *          //===========================================================
 * &lt;@multiline text=subroutine.description width=45>
 *          // %{i}. %{text}
 * &lt;/@multiline>
 *          //===========================================================
 * </pre>
 * <p>
 * Example 2:
 * 
 * <pre>
 *  ...+... 1 ...+... 2 ...+... 3 ...+... 4 ...+... 5 ...+... 6 ...+... 7 ...+... 8
 *          //===========================================================
 * &lt;@multiline text=subroutine.description width=45 bullet=subroutine.bullet>
 *          // %{i}. %{text}
 * &lt;/@multiline>
 *          //===========================================================
 * </pre>
 * 
 * Example values and results for various values of <i>subroutine.bullet</i>:
 * <p>
 * Template:
 * 
 * <pre>
 *          //===========================================================
 * &lt;@multiline text=subroutine.description width=45 bullet=subroutine.bullet>
 *          // %{i} %{text}
 * &lt;/@multiline>
 *          //===========================================================
 * </pre>
 * 
 * Result for bullet="1.":
 * 
 * <pre>
 * //===========================================================
 * //    1. First line
 * //    2. Second line
 * //    3. Third line
 * // ===========================================================
 * </pre>
 * 
 * Result for bullet="a)":
 * 
 * <pre>
 * //===========================================================
 * //    a) First line
 * //    b) Second line
 * //    c) Third line
 * // ===========================================================
 * </pre>
 * 
 * Result for bullet="*":
 * 
 * <pre>
 * //===========================================================
 * //    * First line
 * //    * Second line
 * //    * Third line
 * // ===========================================================
 * </pre>
 */
public class MultilineDirective implements TemplateDirectiveModel {

    private static final String PARAM_NAME_TEXT = "text";

    private static final String PARAM_NAME_WIDTH = "width";

    private static final String PARAM_NAME_BULLET = "bullet";

    private static final String VAR_COUNT = "%{i}";

    private static final String VAR_TEXT = "%{text}";

    private static final String[] chars = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
        "v", "w", "x", "y", "z" };

    private static final int BULLET_NUMBER = 1;
    private static final int BULLET_CHAR = 2;
    private static final int BULLET_SYMBOL = 3;

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
        String tBullet = "1";
        int tBulletType = -1;
        String tBulletDelimiter = "";

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
            } else if (tParamName.equals(PARAM_NAME_BULLET)) {
                if (!(tParamValue instanceof SimpleScalar)) {
                    throw new TemplateModelException(produceMessage("The \"" + PARAM_NAME_BULLET + "\" parameter must be a string."));
                }
                tBullet = ((SimpleScalar)tParamValue).getAsString().trim();
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

        if (tBullet == null) {
            tBullet = "1";
        }

        if (tBullet.length() > 1) {
            tBulletDelimiter = tBullet.substring(1);
            tBullet = tBullet.substring(0, 1);
        }

        tBulletType = getBulletType(tBullet);

        if (aLoopVarList.length > 1) {
            throw new TemplateModelException(produceMessage("At most one loop variable is allowed."));
        }

        // If there is non-empty nested content:
        if (aBody != null) {

            StringWriter tWriter = new StringWriter();
            aBody.render(tWriter);
            String tBody = tWriter.getBuffer().toString();

            int tStartPos = tBody.indexOf(VAR_TEXT);
            if (tStartPos == -1) {
                throw new TemplateModelException(produceMessage("The required \"" + VAR_TEXT + "\" tag is missing."));
            }

            String tIndent = tBody.substring(0, tStartPos);
            Collection<String> tLines = StringUtil.wordWrap(tText, tWidth, new Locale("de"));

            Writer tOut = anEnvironment.getOut();
            int i = getBulletIndex(tBullet, tBulletType) - 1;
            for (String tLine : tLines) {
                i++;

                // Set the loop variable, if there is one:
                NumberFormat tFormatter = new DecimalFormat("###0");

                StringBuffer tCount = null;
                if (tBulletType == BULLET_CHAR) {
                    tCount = new StringBuffer(chars[i]);
                    if (i > chars.length - 1) {
                        i = 0;
                    }
                } else if (tBulletType == BULLET_NUMBER) {
                    tCount = new StringBuffer(tFormatter.format(i));
                } else {
                    tCount = new StringBuffer(tBullet);
                }

                while (tCount.length() < VAR_COUNT.length()) {
                    tCount.insert(0, " ");
                }

                // Write body:
                tOut.write(tIndent.replace(VAR_COUNT, tCount + tBulletDelimiter) + tLine + StringUtil.newLineChar());
            }

        } else {
            throw new RuntimeException("missing body");
        }
    }

    private int getBulletIndex(String aBullet, int aBulletType) {

        if (aBulletType == BULLET_CHAR) {
            return findCharIndex(aBullet);
        } else if (aBulletType == BULLET_NUMBER) {
            return Integer.parseInt(aBullet);
        }

        return 0;
    }

    private int getBulletType(String aBullet) {

        if (isNumber(aBullet)) {
            return BULLET_NUMBER;
        } else if (isCharacter(aBullet)) {
            return BULLET_CHAR;
        } else {
            return BULLET_SYMBOL;
        }
    }

    private boolean isNumber(String aBullet) {
        try {
            Integer.parseInt(aBullet);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isCharacter(String aBullet) {

        for (int i = 0; i < chars.length; i++) {
            if (chars[i].equalsIgnoreCase(aBullet)) {
                return true;
            }
        }

        return false;
    }

    private int findCharIndex(String aChar) {

        for (int i = 0; i < chars.length; i++) {
            if (chars[i].equalsIgnoreCase(aChar)) {
                return i;
            }
        }

        return 0;
    }

    private String produceMessage(String aText) {
        return getClass().getSimpleName() + ": " + aText;
    }
}