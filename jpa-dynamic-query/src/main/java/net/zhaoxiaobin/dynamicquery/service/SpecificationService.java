package net.zhaoxiaobin.dynamicquery.service;

import net.zhaoxiaobin.dynamicquery.domain.Actor;

import java.util.List;

/**
 * @author zhaoxb
 * @date 2020/11/17 9:33 下午
 */
public interface SpecificationService {
    List<Actor> findBySpecification(Long id, Integer age, Integer page, Integer pageSize);
}
