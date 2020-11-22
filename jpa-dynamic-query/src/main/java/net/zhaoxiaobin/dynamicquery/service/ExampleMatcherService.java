package net.zhaoxiaobin.dynamicquery.service;

import net.zhaoxiaobin.dynamicquery.domain.Actor;

import java.util.List;

/**
 * @author zhaoxb
 * @date 2020/11/17 10:33 下午
 */
public interface ExampleMatcherService {
    List<Actor> findByExampleMatcher(String actorEmailPre, String actorNamePre, Integer page, Integer pageSize);
}
