package net.zhaoxiaobin.jpabasic.dao;

import net.zhaoxiaobin.jpabasic.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author zhaoxb
 * @date 2020/11/17 8:00 下午
 */
public interface ActorUpdateRepository extends JpaRepository<Actor, Long> {
    /**
     * JPQL更新数据
     *
     * @param email
     * @param id
     * @return
     */
    @Modifying
    @Query("update Actor a set a.actorEmail = ?1 where a.id = ?2")
    int updateActorEmailById(String email, Long id);

    /**
     * 使用原生sql批量更新
     *
     * @return
     */
    @Modifying
    @Query(value = "update actor a set a.create_time = ?1 where a.id >= ?2", nativeQuery = true)
    int updateCreateTimeById(String createTime, Long id);
}
