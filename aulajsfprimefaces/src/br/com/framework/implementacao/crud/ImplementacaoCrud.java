package br.com.framework.implementacao.crud;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sun.faces.taglib.jsf_core.ValidateDoubleRangeTag;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.framework.interfac.crud.InterfaceCrud;

@Component
@Transactional
public class ImplementacaoCrud<T> implements InterfaceCrud<T> {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private static SessionFactory SessionFactory = HibernateUtil.getSessionFactory();

	@Autowired
	private JdbcTemplateImpl jdbcTemplate;

	@Autowired
	private SimpleJdbcTemplateImpl simpleJdbcTemplate;

	@Autowired
	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	private SimpleJdbcClassImpl simpleJdbcClass;

	public JdbcTemplateImpl getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public void save(T obj) throws Exception {
		validaSessionFactory();
		SessionFactory.getCurrentSession().save(obj);
		executeFlushSession();

	}

	@Override
	public void persist(T obj) throws Exception {
		validaSessionFactory();
		SessionFactory.getCurrentSession().persist(obj);
		executeFlushSession();

	}

	@Override
	public void saveOrUpdate(T obj) throws Exception {
		validaSessionFactory();
		SessionFactory.getCurrentSession().saveOrUpdate(obj);
		executeFlushSession();

	}

	@Override
	public void update(T obj) throws Exception {
		validaSessionFactory();
		SessionFactory.getCurrentSession().update(obj);
		executeFlushSession();

	}

	@Override
	public void delete(T obj) throws Exception {
		validaSessionFactory();
		SessionFactory.getCurrentSession().delete(obj);
		executeFlushSession();

	}

	@Override
	public T merge(T obj) throws Exception {
		validaSessionFactory();
		obj = (T) SessionFactory.getCurrentSession().merge(obj);
		executeFlushSession();
		return obj;

	}

	@Override
	public List<T> fyndList(Class<T> obj) throws Exception {
		validaSessionFactory();

		StringBuilder query = new StringBuilder();
		query.append(" select distinct (entity) from ").append(obj.getSimpleName()).append("entity");

		List<T> lista = SessionFactory.getCurrentSession().createQuery(query.toString()).list();

		return lista;
	}

	@Override
	public Object findByID(Class<T> entidade, Long id) throws Exception {
		validaSessionFactory();
		Object obj = SessionFactory.getCurrentSession().load(getClass(), id);
		return obj;
	}

	@Override
	public T findPorID(Class<T> entidade, Long id) throws Exception {
		validaSessionFactory();
		T obj = (T) SessionFactory.getCurrentSession().load(getClass(), id);
		return obj;
	}

	@Override
	public List<T> fundListByQueryDinamica(String s) throws Exception {
		validaSessionFactory();
		List<T> lista = new ArrayList<T>();
		lista  = SessionFactory.getCurrentSession().createQuery(s).list();
		return lista;
	}

	@Override
	public void executeUpdateQueryDinamica(String s) throws Exception {
		validaSessionFactory();
		SessionFactory.getCurrentSession().createQuery(s).executeUpdate();
		executeFlushSession();
	}

	@Override
	public void executeUpdateSQLDinamica(String s) throws Exception {
		validaSessionFactory();
		SessionFactory.getCurrentSession().createSQLQuery(s).executeUpdate();
		executeFlushSession();
		

	}

	@Override
	public void clearSession() throws Exception {
		validaSessionFactory();
		SessionFactory.getCurrentSession().close();

	}

	@Override
	public void evict(T objs) throws Exception {
		validaSessionFactory();
		SessionFactory.getCurrentSession().evict(objs);

	}

	@Override
	public Session getSession() throws Exception {
		validaSessionFactory();
		return SessionFactory.getCurrentSession();
	}

	@Override
	public List<?> getListSQLDinamica(String s) throws Exception {
		validaSessionFactory();
		List<?> lista = SessionFactory.getCurrentSession().createSQLQuery(s).list();
		return null;
	}

	@Override
	public JdbcTemplate getJdbctemplate() {
		return null;
	}

	@Override
	public SimpleJdbcOperations getSimpleJdbcTemplate() {
		return null;
	}

	@Override
	public SimpleJdbcInsert getSimpleJdbcInsert() {
			return simpleJdbcInsert;
	}

	@Override
	public Long totalRegistro(String table) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("selec count(1) from ").append(table);
		return jdbcTemplate.queryForLong(sql.toString());
	}

	@Override
	public Query obterQuery(String query) throws Exception {
		validaSessionFactory();
		Query queryReturn = SessionFactory.getCurrentSession().createQuery(query.toString());
		return queryReturn;
	}

	
	/**
	 * Realiza consulta no banco de dados, iniciando o carregamento a partir
	 * registro passado no parametro - > iniciaNoRegistro e obtendo  maximo de 
	 * resultados passados em -> maximoResultado.
	 * 
	 * @param query
	 * @param iniciaNoRegistro
	 * @param maximoResultado
	 * @return List<T>
	 * @throws Exception
	 * 
	 * */
	@Override
	public List<T> findListByQueryDinamica(String query, int iniciaNoRegistro, int maximoResultado) throws Exception {
		validaSessionFactory();
		List<T> lista  =  new ArrayList<T>();
		lista  = SessionFactory.getCurrentSession().createQuery(query).setFirstResult(iniciaNoRegistro).setMaxResults(maximoResultado).list();
		return lista;
	}

	private void validaTransaction() {
		if (SessionFactory.getCurrentSession().getTransaction().isActive()) {
			SessionFactory.getCurrentSession().beginTransaction();
		}

	}

	private void validaSessionFactory() {
		if (SessionFactory == null) {
			SessionFactory = HibernateUtil.getSessionFactory();
		}
	}

	private void commitProcessoAjax() {
		SessionFactory.getCurrentSession().beginTransaction().commit();
	}

	private void rollBackProcessoAjax() {
		SessionFactory.getCurrentSession().beginTransaction().commit();
	}

	/*
	 * Roda instantaneamente o SQL no banco de dados
	 */
	private void executeFlushSession() {
		SessionFactory.getCurrentSession().flush();
	}
	
	
	public List<Object[]> getListSQLDinamicaArray(String sql) throws Exception {
		validaSessionFactory();
		List<Object[]> lista = (List<Object[]>) SessionFactory.getCurrentSession().createSQLQuery(sql).list();
		executeFlushSession();
		return lista;
	}

}
