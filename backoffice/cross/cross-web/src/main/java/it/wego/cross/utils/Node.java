package it.wego.cross.utils;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
	private List<T> parents;
	private T current;
	private List<T> children;
	
	
	public Node(T current) {
		super();
		this.current = current;
	}

	public Node(T parent, T current) {
		super();
		this.getParents().add(parent);
		this.current = current;
	}
	
	public List<T> getParents() {
		if(parents== null)
			this.parents = new ArrayList<>();
		return parents;
	}

	public void setParents(List<T> parents) {
		this.parents = parents;
	}

	public T getCurrent() {
		return current;
	}
	public void setCurrent(T current) {
		this.current = current;
	}
	public List<T> getChildren() {
		return children;
	}
	public void setChildren(List<T> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Node [current=" + current + "]";
	}
	
	
}
