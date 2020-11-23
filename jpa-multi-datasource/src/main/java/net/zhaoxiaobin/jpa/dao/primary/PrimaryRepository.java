package net.zhaoxiaobin.jpa.dao.primary;

import net.zhaoxiaobin.jpa.domain.primary.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhaoxb
 * @date 2020/11/23 4:34 下午
 */
public interface PrimaryRepository extends JpaRepository<Actor,Long> {
}