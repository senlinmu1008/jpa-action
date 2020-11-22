package net.zhaoxiaobin.dynamicquery.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.zhaoxiaobin.dynamicquery.dao.ExampleMatcherRepository;
import net.zhaoxiaobin.dynamicquery.domain.Actor;
import net.zhaoxiaobin.dynamicquery.service.ExampleMatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhaoxb
 * @date 2020/11/17 10:34 下午
 */
@Service
@Transactional
@Slf4j
public class ExampleMatcherServiceImpl implements ExampleMatcherService {
    @Autowired
    private ExampleMatcherRepository exampleMatcherRepository;

    @Override
    public List<Actor> findByExampleMatcher(String actorEmailPre, String actorNamePre, Integer page, Integer pageSize) {
        Actor actor = new Actor();
        actor.setActorEmail(actorEmailPre);
        actor.setActorName(actorNamePre);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("actorEmail", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("actorName", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withIgnoreCase() // 忽略大小写，MYSQL模糊查询默认也会忽略大小写
                .withIgnoreNullValues() // 忽略null值
                // 默认会匹配所有字段作为查询条件，所以需要设置忽略哪些字段不作为条件匹配，即这些字段可以任意值
                .withIgnorePaths("actorAge", "createTime");

        Example<Actor> actorExample = Example.of(actor, matcher);

        // 指定排序和分页
        Sort sort = new Sort(Sort.Direction.ASC, "actorAge");
        PageRequest pageRequest = PageRequest.of(page < 0 ? 0 : page, pageSize, sort);
        Page<Actor> actorPage = exampleMatcherRepository.findAll(actorExample, pageRequest);
        log.info("分页查询第:[{}]页,pageSize:[{}],共有:[{}]数据,共有:[{}]页", page, pageSize, actorPage.getTotalElements(), actorPage.getTotalPages());
        List<Actor> actorListByExampleMatcher = actorPage.getContent();
        return actorListByExampleMatcher;
    }
}