package it.people.fsl.servizi.test.etnoteam.demo.model;

import it.people.core.PeopleContext;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.util.XmlObjectWrapper;
import it.people.vsl.PipelineData;
import it.people.vsl.PipelineDataImpl;
import org.apache.xmlbeans.XmlObject;

public class ProcessData
  extends AbstractData
{
  private XmlObject finalData;
  
  public ProcessData()
  {
    this.m_clazz = ProcessData.class;
  }
  
  public void exportToPipeline(PipelineData pd)
  {
    try
    {
      if (this.finalData != null) {
        pd.setAttribute(PipelineDataImpl.XML_PROCESSDATA_PARAMNAME, XmlObjectWrapper.generateXml(this.finalData));
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  public void doDefineValidators() {}
  
  public void initialize(PeopleContext context, AbstractPplProcess pplProcess) {}
  
  public boolean validate()
  {
    return true;
  }
  
  public XmlObject getFinalData()
  {
    if (this.finalData != null) {
      return this.finalData;
    }
    return null;
  }
}
