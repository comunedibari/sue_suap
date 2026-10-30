package mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.EntityType;

import mocks.expression.ExpressionMock;

public class CriteriaQueryMock<T> implements CriteriaQuery{

	protected Root<T> root;
	
	private boolean distinct;
	
	private List<ExpressionMock> mocks = new ArrayList<>();
	
	public CriteriaQueryMock(Root<T> root) {
		super();
		this.root = root;
	}

	public List<ExpressionMock> getMocks(){
		return this.mocks;
	}
	//*****
	@Override
	public Root from(Class arg0) {
		// TODO Auto-generated method stub
		return root;
	}

	@Override
	public Root from(EntityType arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getGroupList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate getGroupRestriction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate getRestriction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getResultType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getRoots() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Selection getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDistinct() {
		// TODO Auto-generated method stub
		return distinct;
	}

	@Override
	public Subquery subquery(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CriteriaQuery distinct(boolean arg0) {
		this.distinct = arg0;
		return this;
	}

	@Override
	public List getOrderList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CriteriaQuery groupBy(Expression... arg0) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public CriteriaQuery groupBy(List arg0) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public CriteriaQuery having(Expression arg0) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public CriteriaQuery having(Predicate... arg0) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public CriteriaQuery multiselect(Selection... arg0) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public CriteriaQuery multiselect(List arg0) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public CriteriaQuery orderBy(Order... arg0) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public CriteriaQuery orderBy(List arg0) {
		if(arg0 instanceof ExpressionMock)
			this.mocks.add((ExpressionMock)arg0);
		return this;
	}

	@Override
	public CriteriaQuery select(Selection arg0) {
		if(arg0 instanceof ExpressionMock)
			this.mocks.add((ExpressionMock)arg0);
		return this;
	}

	@Override
	public CriteriaQuery where(Expression arg0) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public CriteriaQuery where(Predicate... arg0) {
		// TODO Auto-generated method stub
		return this;
	}

}
