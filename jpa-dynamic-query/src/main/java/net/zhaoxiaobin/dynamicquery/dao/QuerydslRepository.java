package net.zhaoxiaobin.dynamicquery.dao;

import net.zhaoxiaobin.dynamicquery.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author zhaoxb
 * @date 2020/11/18 8:18 下午
 */
public interface QuerydslRepository extends JpaRepository<Actor, Long>, QuerydslPredicateExecutor<Actor> {
}
