package net.zhaoxiaobin.dynamicquery.service;

import net.zhaoxiaobin.dynamicquery.domain.Actor;

import java.util.List;

/**
 * @author zhaoxb
 * @date 2020/11/18 8:20 下午
 */
public interface QuerydslService {
    //    ==================原生dsl查询==================
    Actor findByActorNameAndActorEmail(String actorName, String actorEmail);
    List<Actor> findAll();
    List<Actor> findByPagination(Integer page, Integer pageSize);
    List<Actor> findByLikeNameAndEmailAndBetweenAgeOrderById(String actorName, String actorEmail, Integer beginAge, Integer endAge);

    //    ==================jpa整合dsl==================
    List<Actor> findByActorNameAndActorEmailPagination(String actorName, String actorEmail, Integer page, Integer pageSize);
    List<Actor> findByDynamicQuery(Integer actorAge, String actorEmail, String actorName, String createTime, Integer page, Integer pageSize);
}
