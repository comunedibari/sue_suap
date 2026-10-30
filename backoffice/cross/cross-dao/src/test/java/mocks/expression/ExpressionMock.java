package mocks.expression;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

public class ExpressionMock<T>  implements Path<T>, Expression<T>{
	
	Object name;
	
	public ExpressionMock(Object args){
		this.name = args;
	}
	
	public String toString() {
		return name.toString();
	}
	
	//*************************************************************************************

	@Override
	public Selection<T> alias(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Selection<?>> getCompoundSelectionItems() {
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
		return name.toString();
	}

	@Override
	public Class<? extends T> getJavaType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <X> Expression<X> as(Class<X> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate in(Object... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate in(Expression<?>... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate in(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate in(Expression<Collection<?>> arg0) {
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
	public <Y> Path<Y> get(SingularAttribute<? super T, Y> arg0) {
		return new ExpressionMock<Y>(arg0);
	}

	@Override
	public <E, C extends Collection<E>> Expression<C> get(PluralAttribute<T, C, E> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V, M extends Map<K, V>> Expression<M> get(MapAttribute<T, K, V> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <Y> Path<Y> get(String arg0) {
		return new ExpressionMock<Y>(arg0);
	}

	@Override
	public Bindable<T> getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path<?> getParentPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<Class<? extends T>> type() {
		// TODO Auto-generated method stub
		return null;
	}
}
