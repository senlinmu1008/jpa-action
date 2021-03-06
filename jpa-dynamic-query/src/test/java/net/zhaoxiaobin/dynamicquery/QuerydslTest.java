package net.zhaoxiaobin.dynamicquery;

import cn.hutool.json.JSONUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import net.zhaoxiaobin.dynamicquery.dao.QuerydslRepository;
import net.zhaoxiaobin.dynamicquery.domain.Actor;
import net.zhaoxiaobin.dynamicquery.domain.QActor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private QuerydslRepository querydslRepository;

    //    ==================原生dsl查询==================

    /**
     * 直接根据条件查询
     */
    @Test
    public void testFindByActorNameAndActorEmail() {
        QActor qActor = QActor.actor;
        Actor actor = jpaQueryFactory.selectFrom(qActor)
                .where(
                        qActor.actorName.eq("高庸涵"),
                        qActor.actorEmail.eq("123456789@qq.com")
                )
                .fetchOne();
        log.info(JSONUtil.toJsonPrettyStr(actor));
    }

    /**
     * 查询所有并根据字段排序
     */
    @Test
    public void testFindAll() {
        QActor qActor = QActor.actor;
        List<Actor> actorList = jpaQueryFactory.selectFrom(qActor)
                .orderBy(
                        qActor.actorAge.asc()
                )
                .fetch();
        log.info(JSONUtil.toJsonPrettyStr(actorList));
    }

    /**
     * 分页查询，并根据字段排序
     */
    @Test
    public void testFindByPagination() {
        int page = 0; // 第几页
        int pageSize = 10; // 每页大小

        QActor qActor = QActor.actor;
        QueryResults<Actor> actorQueryResults = jpaQueryFactory.selectFrom(qActor)
                .orderBy(
                        qActor.actorAge.asc()
                )
                .offset(page)
                .limit(pageSize)
                .fetchResults();
        // 获取分页参数
        long total = actorQueryResults.getTotal();
        long totalPage = (total % pageSize == 0) ? (total / pageSize) : (total / pageSize + 1);
        log.info("分页查询第:[{}]页,pageSize:[{}],共有:[{}]数据,共有:[{}]页", page, pageSize, total, totalPage);
        List<Actor> actorListByPagination = actorQueryResults.getResults();
        log.info(JSONUtil.toJsonPrettyStr(actorListByPagination));
    }

    /**
     * 根据条件模糊查询，并指定某个字段的范围
     */
    @Test
    public void testFindByLikeNameAndEmailAndBetweenAgeOrderById() {
        QActor qActor = QActor.actor;
        List<Actor> actorList = jpaQueryFactory.selectFrom(qActor)
                .where(
                        qActor.actorName.like("name%"),
                        qActor.actorEmail.like("email%"),
                        qActor.actorAge.between(20, 50)
                )
                .orderBy(
                        qActor.id.asc()
                )
                .fetch();
        log.info(JSONUtil.toJsonPrettyStr(actorList));
    }

    //    ==================jpa整合dsl==================

    /**
     * 模糊查询并分页排序
     */
    @Test
    public void testFindByActorNameAndActorEmailPagination() {
        int page = 0; // 第几页
        int pageSize = 10; // 每页大小

        QActor qActor = QActor.actor;
        // 模糊查询条件
        BooleanExpression expression = qActor.actorName.like("name%").and(qActor.actorEmail.like("email%"));
        // 排序、分页参数
        Sort sort = new Sort(Sort.Direction.DESC, "actorAge");
        PageRequest pageRequest = PageRequest.of(page < 0 ? 0 : page, pageSize, sort);
        Page<Actor> actorPage = querydslRepository.findAll(expression, pageRequest);
        log.info("分页查询第:[{}]页,pageSize:[{}],共有:[{}]数据,共有:[{}]页", page, pageSize, actorPage.getTotalElements(), actorPage.getTotalPages());
        List<Actor> actorListByPagination = actorPage.getContent();
        log.info(JSONUtil.toJsonPrettyStr(actorListByPagination));
    }

    /**
     * 动态查询并分页排序
     */
    @Test
    public void testFindByDynamicQuery() {
        Integer actorAge = 45;
        String actorEmail = "email";
        String actorName = null;
        String createTime = "2020-11-21";

        int page = 0; // 第几页
        int pageSize = 10; // 每页大小

        QActor qActor = QActor.actor;
        // 初始化组装条件(类似where 1=1)
        Predicate predicate = qActor.isNotNull().or(qActor.isNull());

        //执行动态条件拼装
        // 相等
        predicate = actorAge == null ? predicate : ExpressionUtils.and(predicate, qActor.actorAge.eq(actorAge));
        // like 模糊匹配
        predicate = actorEmail == null ? predicate : ExpressionUtils.and(predicate, qActor.actorEmail.like(actorEmail + "%"));
        predicate = actorName == null ? predicate : ExpressionUtils.and(predicate, qActor.actorName.like(actorName + "%"));
        // 包含，相当于like %xxx%
        predicate = createTime == null ? predicate : ExpressionUtils.and(predicate, qActor.createTime.contains(createTime));

        // 排序、分页参数
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page < 0 ? 0 : page, pageSize, sort);
        Page<Actor> actorPage = querydslRepository.findAll(predicate, pageRequest);
        log.info("分页查询第:[{}]页,pageSize:[{}],共有:[{}]数据,共有:[{}]页", page, pageSize, actorPage.getTotalElements(), actorPage.getTotalPages());
        List<Actor> actorListByPagination = actorPage.getContent();
        log.info(JSONUtil.toJsonPrettyStr(actorListByPagination));
    }
}