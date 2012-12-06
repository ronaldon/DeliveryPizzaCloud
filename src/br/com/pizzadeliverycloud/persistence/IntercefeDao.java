package br.com.pizzadeliverycloud.persistence;

/**
 * Define a interface de um DAO genérico.
 * 
 * @param <K> Tipo da chave
 * @param <E> Tipo da entidade
 * 
 */
public interface IntercefeDao<K, E> {

    /**
     * Busca a entidade pelo id.
     * 
     * @see EntityManager#find(Class, Object)
     * @param id Chave da entidade
     * @return Entidade
     */
    E findById(final K id);

    /**
     * Remove uma entidade.
     * 
     * @see EntityManager#remove(Object)
     * @param entity
     *            Entidade
     */
    void remove(final E entity);

    /**
     * Persiste, cria a entidade.
     * 
     * @see EntityManager#persist(Object)
     * @param entity
     *            Entidade
     */
    void persist(final E entity);

    /**
     * Atualiza a entidade.
     * 
     * @see EntityManager#merge(Object)
     * @param entity
     *            Entidade
     */
    void update(final E entity);

    /**
     * Atualiza a entidade.
     * 
     * @see EntityManager#merge(Object)
     * @param entity
     *            Entidade
     * @return Entidade atualizada
     */
    E merge(final E entity);

    /**
     * Persiste, cria a entidade.
     * 
     * @see EntityManager#persist(Object)
     * @param entity
     *            Entidade
     */
    void create(final E entity);

    /**
     * Faz flush no banco de dados das alterações pendentes.
     * 
     * @see EntityManager#flush()
     */
    void flush();

}
