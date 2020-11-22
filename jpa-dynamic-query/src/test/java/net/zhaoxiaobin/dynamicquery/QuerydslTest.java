package net.zhaoxiaobin.dynamicquery;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import net.zhaoxiaobin.dynamicquery.domain.Actor;
import net.zhaoxiaobin.dynamicquery.service.QuerydslService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author zhaoxb
 * @date 2020/11/18 9:13 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class QuerydslTest {
    @Autowired
    private QuerydslService querydslService;

    /**
     * dsl原生查询测试
     */
    @Test
    public void testFindBydslNativeQuery() {
        // 直接根据条件查询
        Actor actor = querydslService.findByActorNameAndActorEmail("默提", "13486627323@163.com");
        log.info("==========findByActorNameAndActorEmail=========={}", JSONUtil.toJsonPrettyStr(actor));

        // 查询所有并根据字段排序
        List<Actor> actorAll = querydslService.findAll();
        log.info("==========findAll=========={}", JSONUtil.toJsonPrettyStr(actorAll));

        // 分页查询，并根据字段排序
        List<Actor> actorListByPagination = querydslService.findByPagination(0, 10);
        log.info("==========actorListByPagination=========={}", JSONUtil.toJsonPrettyStr(actorListByPagination));

        // 根据条件模糊查询，并指定某个字段的范围
        List<Actor> actorList = querydslService.findByLikeNameAndEmailAndBetweenAgeOrderById("name", "email", 20, 50);
        log.info("==========findByLikeNameAndEmailAndBetweenAgeOrderById=========={}", JSONUtil.toJsonPrettyStr(actorList));
    }

    @Test
    public void testFindByDynamicQuery() {
        // 模糊查询并分页排序
        List<Actor> actorList1 = querydslService.findByActorNameAndActorEmailPagination("name", "email", 0, 10);
        log.info("==========findByActorNameAndActorEmailPagination=========={}", JSONUtil.toJsonPrettyStr(actorList1));

        // 动态查询并分页排序
        List<Actor> actorList2 = querydslService.findByDynamicQuery(45, "email", null, "2020-11-17", 0, 10);
        log.info("==========findByDynamicQuery=========={}", JSONUtil.toJsonPrettyStr(actorList2));
    }
}