package net.zhaoxiaobin.dynamicquery.dao;

import net.zhaoxiaobin.dynamicquery.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author zhaoxb
 * @date 2020/11/17 9:32 下午
 */
public interface SpecificationRepository extends JpaRepository<Actor, Long>, JpaSpecificationExecutor<Actor> {
}
