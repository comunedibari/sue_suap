/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.layout;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author CS
 */
@Component
public class Message {

    private Boolean error = true;
    private Boolean warning = false;
    private List<String> messages = new ArrayList<String>();

    public Message() {
    }

    public Message(List<String> messages) {
        this.messages = messages;
    }

    public Message(Boolean error, List<String> messages) {
        this.error = error;
        this.messages = messages;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public Boolean getWarning() {
        return warning;
    }

    public void setWarning(Boolean warning) {
        this.warning = warning;
    }

	@Override
	public String toString() {
		return "Message [error=" + error + ", warning=" + warning + ", messages=" + messages + "]";
	}

}
