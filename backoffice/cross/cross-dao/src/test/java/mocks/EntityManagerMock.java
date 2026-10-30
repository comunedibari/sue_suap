package mocks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.jpa.JpaQuery;
import org.eclipse.persistence.queries.DatabaseQuery;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import it.wego.cross.dao.LookupDao;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import mocks.expression.ExpressionMock;
import mocks.predicates.PredicatesManager;

public class EntityManagerMock {

	protected PredicatesManager predicateManager;

	@Mock
	protected EntityManager entityManager;

	@Mock
	protected LookupDao lookupDao;
	@Mock
	protected CriteriaBuilderMock criteriaBuilder;

	@Mock
	protected CriteriaQueryMock criteriaQuery;

	@Mock
	protected Root<Pratica> root;

	@Mock
	protected Join join;

	@Mock
	protected Path path;

	@Mock
	protected TypedQueryMock query;

	@Mock
	protected JpaQuery jpaQuery;

	@Mock
	protected DatabaseQuery databaseQuery;

	@Mock
	protected Predicate predicate;

	protected int index;

	public EntityManagerMock() {
		predicateManager = new PredicatesManager();
		index = 0;
		root = mock(Root.class);
		
		lookupDao = mock(LookupDao.class);
		entityManager = mock(EntityManager.class);
		criteriaQuery = new CriteriaQueryMock(root);
		criteriaBuilder = new CriteriaBuilderMock(predicateManager, criteriaQuery);
		
		join = mock(Join.class);
		path = mock(Path.class);
		jpaQuery = mock(JpaQuery.class);
		query = new TypedQueryMock(predicateManager, jpaQuery);
		databaseQuery = mock(DatabaseQuery.class);
		predicate = mock(Predicate.class);

		when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

		when(root.join("datiCatastaliList", JoinType.LEFT)).thenReturn(join);
		when(root.join(Mockito.anyString())).thenReturn(join);
		when(join.join(Mockito.anyString())).thenReturn(join);
//		when(root.get(Mockito.anyString())).thenReturn(path);
		when(path.get(Mockito.anyString())).thenReturn(path);
		when(entityManager.createQuery(criteriaQuery)).thenReturn(query);
//		when(query.unwrap(JpaQuery.class)).thenReturn(jpaQuery);
		when(jpaQuery.getDatabaseQuery()).thenReturn(databaseQuery);

		//***********************

		when(root.get(Mockito.anyString())).then(new Answer<Path<String>>() {
			@Override 
			public Path<String> answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				return new ExpressionMock(args[0]);
			}
		});
		
		when(join.get(Mockito.anyString())).then(new Answer<Path<String>>() {
			@Override 
			public Path<String> answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				return new ExpressionMock(args[0]);
			}
		});
		
		when(path.get(Mockito.anyString())).then(new Answer<Path<String>>() {
			@Override 
			public Path<String> answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				return new ExpressionMock(args[0]);
			}
		});
		
		when(path.get(Mockito.anyString())).then(new Answer<Expression<String>>() {
			@Override 
			public Path<String> answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				return new ExpressionMock(args[0]);
			}
		});

		//***********************		
		
		when(this.lookupDao.findComuneById(Mockito.anyInt())).then(new Answer<LkComuni>() {
			@Override 
			public LkComuni answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				LkComuni comuni = new LkComuni();
				comuni.setIdComune((Integer) args[0]);
				return comuni;
			}
		});
	}

	public PredicatesManager getPredicateManager() {
		return predicateManager;
	}

	public void setPredicateManager(PredicatesManager predicateManager) {
		this.predicateManager = predicateManager;
	}

	public EntityManager getEntityManger() {
		return entityManager;
	}
	
	public LookupDao getLookUpDao() {
		return lookupDao;
	}
	
	public boolean isDistinct() {
		return criteriaQuery.isDistinct();
	}
	
	public List<ExpressionMock> getAddictionalExpression() {
		return this.criteriaQuery.getMocks();
	}
}
