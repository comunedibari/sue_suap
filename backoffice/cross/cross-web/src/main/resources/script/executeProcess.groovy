import org.activiti.engine.ProcessEngine
import org.activiti.engine.RuntimeService
import org.activiti.engine.ProcessEngineConfiguration


Integer idPratica = 12755;
Integer idPraticaEvento = 67273;
String identificativoPratica = "LPRGCM62C01F158X-AOO547-7810810";
String oggettoPratica = "RTC - Comunicazione attivazione stazione radioelettrica piu' altri endoprocedimenti collegati";
String fascicoloPratica = "";
//Richiedenti separati da <br />
String richiedentiPratica = "H3G S.p.A.<br/>";
Map<String, Object> variabili = new HashMap<String, Object>();
variabili.put("tipoTask", "task");
variabili.put("idPratica", idPratica);
variabili.put("idPraticaEvento", idPraticaEvento);
variabili.put("oggettoPratica", oggettoPratica);
variabili.put("fascicoloPratica", fascicoloPratica);
variabili.put("richiedentiPratica", richiedentiPratica);

ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
RuntimeService runtimeService = processEngine.getRuntimeService();
runtimeService.startProcessInstanceByKey("cross_communication_process", variabili);
System.out.println("Flusso avviato!");