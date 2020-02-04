package it.wego.cross.dto;

import it.wego.cross.utils.Utils;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class TaskDTO extends AbstractDTO implements Serializable {

    private String taskId;
    private String instanceId;
    private String taskKey;
    private String name;
    private String taskDate;
    private Map<String, Object> variables;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = Utils.dateTimeItalianFormat(taskDate);
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

}
