package br.com.framework.interfac.crud;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public interface InterfaceCrud<T> extends Serializable {

	// Salva os Dados
	void save(T obj) throws Exception;

	void persist(T obj) throws Exception;

	// Salva ou Atualiza
	void saveOrUpdate(T obj) throws Exception;

	// Realizada o update / atualização de dados
	void update(T obj) throws Exception;

	// Realiza o delete de dados
	void delete(T obj) throws Exception;

	// salva ou atualiza e retorna o objeto em estado persistente
	T merge (T obj) throws Exception;

	// Carrega a lista de dados de determinada classe
	List<T> fyndList(Class<T> objs) throws Exception;

	// Busca por Id
	Object findByID(Class<T> entidade, Long id) throws Exception;

	//
	T findPorID(Class<T> entidade, Long id) throws Exception;

	List<T> fundListByQueryDinamica(String s) throws Exception;
	
	//executar update com HQL
	void executeUpdateQueryDinamica(String s) throws Exception;
	
	//Executar update com SQL
	void executeUpdateSQLDinamica(String s) throws Exception;
	
	void clearSession() throws Exception;
	
	//Retira um objeto da sessão do hibernate.
	void evict (T objs) throws Exception;
	
	Session getSession() throws Exception;
	
	List<?> getListSQLDinamica(String sql) throws Exception;
	
	
	//JDBC do Spring
	JdbcTemplate getJdbctemplate();
	
	SimpleJdbcOperations getSimpleJdbcTemplate();
	
	SimpleJdbcInsert getSimpleJdbcInsert();
	
	
	Long totalRegistro(String table) throws Exception;
	
	Query obterQuery(String query) throws Exception;
	
	//Carregamento dinamico com JSF e PrimeFaces
	List<T> findListByQueryDinamica (String query, int iniciaNoRegistro, int maximoResultado) throws Exception;
	
	

}
