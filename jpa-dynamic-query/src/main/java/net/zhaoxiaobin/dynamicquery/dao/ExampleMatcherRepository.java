package net.zhaoxiaobin.dynamicquery.dao;

import net.zhaoxiaobin.dynamicquery.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhaoxb
 * @date 2020/11/17 10:33 下午
 */
public interface ExampleMatcherRepository extends JpaRepository<Actor, Long> {

}
