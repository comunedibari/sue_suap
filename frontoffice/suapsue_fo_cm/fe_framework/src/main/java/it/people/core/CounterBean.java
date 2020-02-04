/**
 * 
 */
package it.people.core;

import java.io.Serializable;


/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         12/nov/2012 22:02:46
 */
public class CounterBean implements Serializable {

    private int count;

    public int getCount() {
	return count;
    }

    public void setCount(int count) {
	this.count = count;
    }

    public void increment() {
	count++;
    }

}
