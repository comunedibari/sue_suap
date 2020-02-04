/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.freemarker;

import com.google.common.base.Strings;
import freemarker.core.Environment;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import it.wego.cross.utils.Utils;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import org.joda.time.DateTime;

/**
 *
 * @author Giuseppe
 */
public class DateDirective implements TemplateDirectiveModel {

    private static final String PARAM_NAME_DATE = "date";
    private static final String PARAM_NAME_DAYS = "days";
    private static final String PARAM_NAME_DATE_INPUT_FORMAT = "input_format";
    private static final String PARAM_NAME_DATE_OUTPUT_FORMAT = "output_format";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        int daysParam = 0;
        Date dateParam = null;
        String dateOutputFormatParam = null, dateInputFormatParam = null;
        String dateParamString = null;
        boolean daysParamsSet = false;
        boolean dateParamSet = false;
        if (params.isEmpty()) {
            throw new TemplateModelException("This directive needs 2 parameter: " + PARAM_NAME_DAYS + " and " + PARAM_NAME_DATE);
        }
        Iterator paramIter = params.entrySet().iterator();
        while (paramIter.hasNext()) {
            Map.Entry ent = (Map.Entry) paramIter.next();
            String paramName = (String) ent.getKey();
            TemplateModel paramValue = (TemplateModel) ent.getValue();
            if (paramName.equals(PARAM_NAME_DAYS)) {
                if (!(paramValue instanceof TemplateNumberModel)) {
                    throw new TemplateModelException("The \"" + PARAM_NAME_DAYS + "\" parameter must be a number.");
                }
                daysParam = ((TemplateNumberModel) paramValue).getAsNumber().intValue();
                daysParamsSet = true;
                if (daysParam < 0) {
                    throw new TemplateModelException("The \"" + PARAM_NAME_DAYS + "\" parameter can't be negative.");
                }
            } else if (paramName.equals(PARAM_NAME_DATE)) {
                dateParamString = paramValue.toString();
            } else if (paramName.equals(PARAM_NAME_DATE_INPUT_FORMAT)) {
                dateInputFormatParam = paramValue.toString();
            } else if (paramName.equals(PARAM_NAME_DATE_OUTPUT_FORMAT)) {
                dateOutputFormatParam = paramValue.toString();
            } else {
                throw new TemplateModelException("Unsupported parameter: " + paramName);
            }
        }

        if (Utils.e(dateInputFormatParam)) {
            dateInputFormatParam = Utils.ITALIAN_DATETIME_FORMAT;
        }
        try {
            DateFormat sdf = new SimpleDateFormat(dateInputFormatParam);
            dateParam = sdf.parse(dateParamString);
            dateParamSet = true;
        } catch (ParseException e) {
            throw new TemplateModelException("The required \"" + PARAM_NAME_DATE + "\" paramter has wrong format.");
        }

        if (!daysParamsSet) {
            throw new TemplateModelException("The required \"" + PARAM_NAME_DAYS + "\" paramter is missing.");
        }
        if (!dateParamSet) {
            throw new TemplateModelException("The required \"" + PARAM_NAME_DATE + "\" paramter is missing.");
        }
        if (loopVars.length != 0) {
            throw new TemplateModelException("This directive doesn't allow loop variables.");
        }

        Writer out = env.getOut();
        Date date = new DateTime(dateParam).plusDays(daysParam).toDate();

        if (!Strings.isNullOrEmpty(dateOutputFormatParam)) {
            DateFormat df = new SimpleDateFormat(dateOutputFormatParam);
            out.write(df.format(date));
        } else {
            out.write(Utils.dateItalianFormat(date));
        }

    }
}
