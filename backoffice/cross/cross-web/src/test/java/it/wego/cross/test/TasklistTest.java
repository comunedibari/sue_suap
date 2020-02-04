/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import com.google.common.base.Preconditions;
import it.wego.cross.constants.Workflow;
import java.util.List;
import java.util.Map;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author Giuseppe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/test/application-context-test.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class TasklistTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;

    private static final String UTENTE_PROVA = "B708520";
    private static final String UTENTE_CTT = "CTTTCN57P51F205W";

    @Test
    public void testGetTaskByAssignee() {
        List<Task> taskList = taskService.createTaskQuery().taskInvolvedUser(UTENTE_CTT).orderByTaskCreateTime().asc().list();
        if (taskList != null) {
            System.out.println("******************************");
            System.out.println("TASK ASSEGNATI");
            for (Task task : taskList) {
                System.out.println(task.getId() + ": " + task.getName());
                Map<String, Object> variables = taskService.getVariables(task.getId());
                System.out.println("- Variabili");
                for (Map.Entry<String, Object> entry : variables.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    System.out.println("* " + key + ":" + value);
                }
                System.out.println("--------------------------");
            }
            System.out.println("******************************");
        }
    }

    @Test
    public void testSearchByVariable() {
        List<Task> list = taskService.createTaskQuery().processVariableValueEquals(Workflow.TIPO_TASK, Workflow.TIPO_TASK_NOTIFICA).list();
        List<Task> list2 = taskService.createTaskQuery().processDefinitionId("cross_communication_process").list();
        Preconditions.checkArgument(list != null && !list.isEmpty());
        System.out.println("--------------------------");
    }
}