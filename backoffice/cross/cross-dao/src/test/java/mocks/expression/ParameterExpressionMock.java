package mocks.expression;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;

public class ParameterExpressionMock<T> implements ParameterExpression<T>{

	int index = 0;
	Class<T> clazz;
	String name;
	Object value;
	
	public ParameterExpressionMock(int index, Class<T> clazz){
		this.index = index;
		this.clazz = clazz;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return this.value;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String toString() {
		return (this.name==null?"TName: " + this.name:"") + this.value == null ? "TIdx: " + index : this.value.toString();
	}

	//************************************************************************
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<T> getParameterType() {
		return this.clazz;
	}

	@Override
	public Integer getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression as(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate in(Object... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate in(Expression... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate in(Collection arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate in(Expression arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate isNotNull() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate isNull() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Selection alias(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getCompoundSelectionItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCompoundSelection() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public Class getJavaType() {
		// TODO Auto-generated method stub
		return null;
	}

}
