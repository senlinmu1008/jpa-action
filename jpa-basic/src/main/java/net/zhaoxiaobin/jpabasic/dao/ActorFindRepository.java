package net.zhaoxiaobin.jpabasic.dao;

import net.zhaoxiaobin.jpabasic.domain.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author zhaoxb
 * @date 2020/11/17 8:00 下午
 */
public interface ActorFindRepository extends JpaRepository<Actor, Long> {
    /**
     * 使用方法名映射成sql查询单条数据，如果查询到多条数据则会报错
     * 等价于 where actor_name = ? and actor_email = ?
     *
     * @param name
     * @param email
     * @return
     */
    Actor findByActorNameAndActorEmail(String name, String email);

    /**
     * 使用方法名映射成sql查询多条数据并排序
     * 等价于 where actor_name like 'xxx%' and id >= ? order by actor_age
     *
     * @param name
     * @param id
     * @return
     */
    List<Actor> findByActorNameStartingWithAndIdGreaterThanEqualOrderByActorAge(String name, Long id);

    /**
     * 使用方法名映射成sql，带条件查询并排序
     *
     * @param id
     * @param sort
     * @return
     */
    List<Actor> findByIdGreaterThanEqual(Long id, Sort sort);

    /**
     * 使用方法名映射成sql，带条件查询分页并排序
     *
     * @param id
     * @param pageable
     * @return
     */
    Page<Actor> findByIdGreaterThanEqual(Long id, Pageable pageable);

    /**
     * 使用JPQL分页查询
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from Actor a where a.id >= ?1")
    Page<Actor> findByPaginationWithJPQL(Long id, Pageable pageable);

    /**
     * 使用原生sql分页查询
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query(value = "select * from actor where id >= ?1", nativeQuery = true)
    Page<Actor> findByPaginationWithSql(Long id, Pageable pageable);
}
