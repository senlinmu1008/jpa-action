package net.zhaoxiaobin.jpabasic.dao;

import net.zhaoxiaobin.jpabasic.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhaoxb
 * @date 2020/11/17 8:00 下午
 */
public interface ActorSaveAndDeleteRepository extends JpaRepository<Actor, Long> {
}
