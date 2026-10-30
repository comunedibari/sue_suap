package mocks;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Parameter;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.jpa.JpaQuery;

import it.wego.cross.entity.LkComuni;
import mocks.expression.ParameterExpressionMock;
import mocks.predicates.PredicatesManager;

public class TypedQueryMock implements TypedQuery{

	private PredicatesManager predicateManager;
	private JpaQuery jpaQuery;
	
	public TypedQueryMock(PredicatesManager predicateManager, JpaQuery jpaQuery) {
		super();
		this.predicateManager = predicateManager;
		this.jpaQuery = jpaQuery;
	}

	@Override
	public int executeUpdate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxResults() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFirstResult() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, Object> getHints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Parameter<?>> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Parameter<?> getParameter(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Parameter<T> getParameter(String name, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Parameter<?> getParameter(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Parameter<T> getParameter(int position, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBound(Parameter<?> param) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T getParameterValue(Parameter<T> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getParameterValue(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getParameterValue(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlushModeType getFlushMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LockModeType getLockMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> cls) {
		// TODO Auto-generated method stub
		return (T) this.jpaQuery;
	}

	@Override
	public List getResultList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getSingleResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypedQuery setFirstResult(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypedQuery setFlushMode(FlushModeType arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypedQuery setHint(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypedQuery setLockMode(LockModeType arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypedQuery setMaxResults(int arg0) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public TypedQuery setParameter(Parameter arg0, Object arg1) {
		String value = arg1.toString();
		if(arg1 instanceof LkComuni)
			value = ((LkComuni)arg1).getIdComune().toString();
		predicateManager.addParameter((ParameterExpressionMock) arg0, value);
		return this;
	}

	@Override
	public TypedQuery setParameter(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public TypedQuery setParameter(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public TypedQuery setParameter(Parameter arg0, Calendar arg1, TemporalType arg2) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public TypedQuery setParameter(Parameter arg0, Date arg1, TemporalType arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypedQuery setParameter(String arg0, Calendar arg1, TemporalType arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypedQuery setParameter(String arg0, Date arg1, TemporalType arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypedQuery setParameter(int arg0, Calendar arg1, TemporalType arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypedQuery setParameter(int arg0, Date arg1, TemporalType arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
