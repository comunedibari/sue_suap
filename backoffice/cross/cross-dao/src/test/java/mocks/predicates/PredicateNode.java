package mocks.predicates;

import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;

import mocks.expression.ParameterExpressionMock;

public class PredicateNode extends PredicateMock {
	
	private String operationName;
	private List<Predicate> predicates;
	private ParameterExpressionMock<Integer> parameterExpression;

	public PredicateNode(String operationName, ParameterExpressionMock<Integer> value) {
		this(operationName, null, value);
	}
	
	public PredicateNode(String operationName, Predicate...predicates) {
		this(operationName, (List)Arrays.asList(predicates), null);
	}
	
	public PredicateNode(String operationName, List<Predicate> predicates, ParameterExpressionMock<Integer> value) {
		super();
		this.operationName = operationName;
		this.predicates = predicates;
		this.parameterExpression = value;
	}

	public String getName() {
		return operationName;
	}
	
	public void setName(String name) {
		this.operationName = name;
	}
	
	public List<Predicate> getPredicates() {
		return predicates;
	}
	
	public void setPredicates(List<Predicate> predicates) {
		this.predicates = predicates;
	}
	
	public ParameterExpression<Integer> getValue() {
		return parameterExpression;
	}
	
	public void setValue(ParameterExpressionMock<Integer> value) {
		this.parameterExpression = value;
	}
	
	public String toString() {
		StringBuilder ret = new StringBuilder("");
		if(this.parameterExpression != null) {
			ret.append(parameterExpression.getAlias());
			ret.append(" ");
			ret.append(operationName);
			ret.append(parameterExpression.getValue() == null? "" : " ");
			ret.append(parameterExpression.getValue() == null? "": parameterExpression.getValue().toString());
		}
		else {
			ret.append(predicates.stream().map(a->a.toString()).reduce((a,b)-> "(" + a + " " + operationName + " " + b + ")").orElse(""));
		}
		return ret.toString();
	}
}