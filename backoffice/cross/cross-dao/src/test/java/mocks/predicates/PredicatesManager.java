package mocks.predicates;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import mocks.expression.ParameterExpressionMock;

public class PredicatesManager {
	
	private List<Predicate> operations = new LinkedList<>();
	private Map<Integer, ParameterExpressionMock> values = new HashMap();
	private List<Object> parameters = new LinkedList<>();
	
	public PredicatesManager() {}
	
	public void addOperation(PredicateNode operation) {
		switch(operation.getName()) {
		case "or":
		case "and":
			List<Predicate> predicateNodes = operation.getPredicates();
			for(Predicate predicateNode: predicateNodes )
				operations.remove(predicateNode);
		}
		
		operations.add(operation);
	}
	
	public List<Predicate> getOperations(){
		return this.operations;
	}
	
	public String getOperations(String concatenation){
		return this.operations.stream().map(predicate -> ((PredicateNode)predicate).toString()).reduce((a,b)-> a + concatenation + b).orElse("");
	}
	
	public void addParameter(ParameterExpressionMock parameterExpression, Object parameter) {
		parameterExpression.setValue(parameter);
		parameters.add(parameter);
	}
	
	public List<Object> getParameters(){
		return this.parameters;
	}

}
