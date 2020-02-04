/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.aurigasync.scheduler;

import org.quartz.SchedulerContext;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Thanks to http://sloanseaman.com/wordpress/2011/06/06/spring-and-quartz-and-persistence/
 * @author giuseppe
 */
public class CrossSchedulerJobFactory extends org.springframework.scheduling.quartz.SpringBeanJobFactory {

    private String[] ignoredUnknownProperties;
    private SchedulerContext schedulerContext;

    @Override
    public void setIgnoredUnknownProperties(String[] ignoredUnknownProperties) {
        super.setIgnoredUnknownProperties(ignoredUnknownProperties);
        this.ignoredUnknownProperties = ignoredUnknownProperties;
    }

    @Override
    public void setSchedulerContext(SchedulerContext schedulerContext) {
        super.setSchedulerContext(schedulerContext);
        this.schedulerContext = schedulerContext;
    }

    /**
     * An implementation of SpringBeanJobFactory that retrieves the bean from
     * the Spring context so that autowiring and transactions work
     *
     * This method is overriden.
     *
     * @see
     * org.springframework.scheduling.quartz.SpringBeanJobFactory#createJobInstance(org.quartz.spi.TriggerFiredBundle)
     */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        //Recupero il contesto 
        XmlWebApplicationContext ctx = ((XmlWebApplicationContext) schedulerContext.get("applicationContext"));
        Object job = ctx.getBean(bundle.getJobDetail().getKey().getName());
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(job);
        if (isEligibleForPropertyPopulation(wrapper.getWrappedInstance())) {
            MutablePropertyValues propertyValues = new MutablePropertyValues();
            if (this.schedulerContext != null) {
                propertyValues.addPropertyValues(this.schedulerContext);
            }
            propertyValues.addPropertyValues(bundle.getJobDetail().getJobDataMap());
            propertyValues.addPropertyValues(bundle.getTrigger().getJobDataMap());
            if (this.ignoredUnknownProperties != null) {
                for (String propName : this.ignoredUnknownProperties) {
                    if (propertyValues.contains(propName) && !wrapper.isWritableProperty(propName)) {
                        propertyValues.removePropertyValue(propName);
                    }
                }
                wrapper.setPropertyValues(propertyValues);
            } else {
                wrapper.setPropertyValues(propertyValues, true);
            }
        }
        return job;
    }
}
