import org.activiti.engine.ProcessEngine
import org.activiti.engine.RuntimeService
import org.activiti.engine.ProcessEngineConfiguration


String instanceId = "<insert instance id>";
System.out.println("Deleting Process with instance id " + instanceId);
ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
RuntimeService runtimeService = processEngine.getRuntimeService();
runtimeService.deleteProcessInstance(instanceId, "Istanza cancellata da amministratore");
System.out.println("Success!!!");