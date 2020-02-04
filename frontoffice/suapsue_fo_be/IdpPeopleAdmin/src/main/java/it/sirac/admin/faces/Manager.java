package it.sirac.admin.faces;

import it.sirac.admin.common.TableDAO;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.security.Provider;
import java.security.Security;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.faces.application.Application;
import javax.faces.component.UIColumn;
import javax.faces.component.UICommand;
import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.myfaces.component.html.ext.HtmlCommandLink;
import org.apache.myfaces.component.html.ext.HtmlDataTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Manager
{
	private static Logger logger = LoggerFactory.getLogger(Manager.class);
  private HashMap headers;
  private HtmlDataTable dynamicDataTable;
  private List list = null;
  private DataModel tableDataModel = null;
  private Object current = null;
  private TableDAO tableAction = null;
  private Class daoClass = null;
  private Class modelClass = null;
  private Paging paging = null;
  private static Properties properties = new Properties();
  private boolean insert = false;
  private boolean enableFilter = false;
  private int view;
  
  public Manager()
  {
    boolean exist = false;
    Provider[] providers = Security.getProviders();
    for (int i = 0; i < providers.length; i++)
    {
      Provider tempProvider = providers[i];
      if (tempProvider.getClass().equals(BouncyCastleProvider.class))
      {
        exist = true;
        break;
      }
    }
    if (!exist) {
      Security.addProvider(new BouncyCastleProvider());
    }
    this.paging = new Paging();
    
    loadProperties();
    
    loadView();
  }
  
  public static void loadProperties()
  {
    URL url = Manager.class.getResource("/properties/app.properties");
    try
    {
      properties.load(url.openStream());
    }
    catch (IOException e)
    {
      logger.error("", e);
    }
  }
  
  public void createClassInstance()
  {
    try
    {
      this.daoClass = Class.forName(properties.getProperty("daoClassName" + this.view));
      this.modelClass = Class.forName(properties.getProperty("modelClassName" + this.view));
      this.tableAction = ((TableDAO)this.daoClass.newInstance());
      this.headers = this.tableAction.getHeaders();
    }
    catch (Exception e)
    {
      logger.error("", e);
    }
  }
  
  public void loadView()
  {
    Object viewObj = queryString("view");
    if (viewObj != null)
    {
      int tempView = Integer.parseInt((String)viewObj);
      if (this.view != tempView)
      {
        this.view = tempView;
        if (this.view < 1) {
          this.view = 1;
        }
        int maxview = Integer.parseInt(properties.getProperty("viewCount"));
        if (this.view > maxview) {
          this.view = maxview;
        }
        this.tableDataModel = new ListDataModel();
        this.dynamicDataTable = new HtmlDataTable();
        this.paging = new Paging();
        
        createClassInstance();
      }
    }
  }
  
  public static Object queryString(String key)
  {
    try
    {
      HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
      
      return request.getParameter(key);
    }
    catch (Exception e)
    {
      logger.error("", e);
    }
    return null;
  }
  
  public List getList()
  {
    loadView();
    this.list = this.tableAction.getPaginatedList(this.paging.getFirstRowIndex(), this.paging.getrowsPerPage());
    this.paging.setRowsCount(this.tableAction.getRowsCount());
    populateDynamicDataTable();
    return this.list;
  }
  
  public DataModel getTableDataModel()
  {
    this.tableDataModel = new ListDataModel();
    this.tableDataModel.setWrappedData(this.list);
    setCurrent(null);
    return this.tableDataModel;
  }
  
  public void populateDynamicDataTable()
  {
    if ((this.list != null) && (this.list.size() > 0))
    {
      this.dynamicDataTable = new HtmlDataTable();
      try
      {
        this.headers = this.tableAction.getHeaders();
        Field[] f = this.modelClass.getDeclaredFields();
        for (int i = 0; i < f.length; i++) {
          if (this.headers.containsKey(f[i].getName()))
          {
            UIOutput header = new UIOutput();
            header.setValue(this.headers.get(f[i].getName()));
            


            HtmlCommandLink link = new HtmlCommandLink();
            MethodBinding cmdlink = FacesContext.getCurrentInstance().getApplication().createMethodBinding("#{tableManager.select}", null);
            


            link.setAction(cmdlink);
            




            ValueBinding Item = FacesContext.getCurrentInstance().getApplication().createValueBinding("#{Item." + f[i].getName() + "}");
            

            link.setValueBinding("value", Item);
            

            UIColumn column = new UIColumn();
            column.setHeader(header);
            column.getChildren().add(link);
            
            this.dynamicDataTable.getChildren().add(column);
          }
        }
        UIColumn lastColumn = new UIColumn();
        UIOutput headerLastColumn = new UIOutput();
        headerLastColumn.setValue(" ");
        

        lastColumn.setHeader(headerLastColumn);
        

        UICommand commandSelect = new UICommand();
        MethodBinding commandSelectMethod = FacesContext.getCurrentInstance().getApplication().createMethodBinding("#{tableManager.select}", null);
        

        commandSelect.setAction(commandSelectMethod);
        commandSelect.getAttributes().put("image", properties.getProperty("selectImage"));
        

        UICommand commandDelete = new UICommand();
        MethodBinding commandDeleteMethod = FacesContext.getCurrentInstance().getApplication().createMethodBinding("#{tableManager.delete}", null);
        

        commandDelete.setAction(commandDeleteMethod);
        commandDelete.getAttributes().put("image", properties.getProperty("deleteImage"));
        commandDelete.getAttributes().put("onclick", "if (!confirm('Confermare la cancellazione?')) return false");
        

        lastColumn.getChildren().add(commandSelect);
        lastColumn.getChildren().add(commandDelete);
        this.dynamicDataTable.getChildren().add(lastColumn);
      }
      catch (Exception e)
      {
        logger.error("", e);
      }
    }
  }
  
  public HtmlDataTable getDynamicDataTable()
  {
    return this.dynamicDataTable;
  }
  
  public String select()
  {
    Object selected = this.tableDataModel.getRowData();
    setCurrent(selected);
    this.insert = false;
    return "selected";
  }
  
  public String delete()
  {
    Object selected = this.tableDataModel.getRowData();
    setCurrent(selected);
    return this.tableAction.delete(this.current);
  }
  
  public String setInsert()
  {
    try
    {
      Object selected = this.modelClass.newInstance();
      setCurrent(selected);
      this.insert = true;
    }
    catch (Exception e)
    {
      return "error";
    }
    return "insert";
  }
  
  public Object getCurrent()
  {
    return this.current;
  }
  
  public void setCurrent(Object current)
  {
    this.current = current;
  }
  
  public Paging getPaging()
  {
    return this.paging;
  }
  
  public void setPaging(Paging paging)
  {
    this.paging = paging;
  }
  
  public TableDAO getTableAction()
  {
    return this.tableAction;
  }
  
  public String getButtonLabel()
  {
    if (this.insert) {
      return "Inserisci";
    }
    return "Aggiorna";
  }
  
  public String buttonAction()
  {
    String result = "failed";
    if (this.insert) {
      result = this.tableAction.insert(this.current);
    } else {
      result = this.tableAction.update(this.current);
    }
    return result;
  }
  
  public String getDetailsPage()
  {
    loadView();
    return properties.getProperty("details" + this.view);
  }
  
  public static String PortalID()
  {
    if (properties == null) {
      loadProperties();
    }
    return properties.getProperty("idcomune");
  }
  
  public String getPortalID()
  {
    return properties.getProperty("idcomune");
  }
  
  public String getFilterPage()
  {
    loadView();
    return properties.getProperty("filter" + this.view);
  }
  
  public boolean isAscending()
  {
    return this.tableAction.isAscending();
  }
  
  public void setAscending(boolean ascending)
  {
    this.tableAction.setAscending(ascending);
  }
  
  public String getSortColumn()
  {
    return this.tableAction.getSortColumn();
  }
  
  public void setSortColumn(String sortColumn)
  {
    this.tableAction.setSortColumn(sortColumn);
  }
  
  public boolean isEnableFilter()
  {
    return this.enableFilter;
  }
  
  public void switchFilter()
  {
    if (this.enableFilter) {
      this.enableFilter = false;
    } else {
      this.enableFilter = true;
    }
  }
  
  public int getView()
  {
    return this.view;
  }
  
  public void setView(int view)
  {
    this.view = view;
  }
  
  public boolean isInsert()
  {
    return this.insert;
  }
}
