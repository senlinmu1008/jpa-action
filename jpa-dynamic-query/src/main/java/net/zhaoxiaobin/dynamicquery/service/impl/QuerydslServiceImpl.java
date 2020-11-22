package net.zhaoxiaobin.dynamicquery.service.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import net.zhaoxiaobin.dynamicquery.dao.QuerydslRepository;
import net.zhaoxiaobin.dynamicquery.domain.Actor;
import net.zhaoxiaobin.dynamicquery.domain.QActor;
import net.zhaoxiaobin.dynamicquery.service.QuerydslService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhaoxb
 * @date 2020/11/18 8:21 下午
 */
@Service
@Transactional
@Slf4j
public class QuerydslServiceImpl implements QuerydslService {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private QuerydslRepository querydslRepository;

    //    ==================原生dsl查询==================

    /**
     * 直接根据条件查询
     *
     * @param actorName
     * @param actorEmail
     * @return
     */
    @Override
    public Actor findByActorNameAndActorEmail(String actorName, String actorEmail) {
        QActor qActor = QActor.actor;
        Actor actor = jpaQueryFactory.selectFrom(qActor)
                .where(
                        qActor.actorName.eq(actorName),
                        qActor.actorEmail.eq(actorEmail)
                )
                .fetchOne();
        return actor;
    }

    /**
     * 查询所有并根据字段排序
     *
     * @return
     */
    @Override
    public List<Actor> findAll() {
        QActor qActor = QActor.actor;
        List<Actor> actorList = jpaQueryFactory.selectFrom(qActor)
                .orderBy(
                        qActor.actorAge.asc()
                )
                .fetch();
        return actorList;
    }

    /**
     * 分页查询，并根据字段排序
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<Actor> findByPagination(Integer page, Integer pageSize) {
        QActor qActor = QActor.actor;
        QueryResults<Actor> actorQueryResults = jpaQueryFactory.selectFrom(qActor)
                .orderBy(
                        qActor.actorAge.asc()
                )
                .offset(page) // 第几页
                .limit(pageSize) // 每页大小
                .fetchResults();
        // 获取分页参数
        long total = actorQueryResults.getTotal();
        long totalPage = (total % pageSize == 0) ? (total / pageSize) : (total / pageSize + 1);
        log.info("分页查询第:[{}]页,pageSize:[{}],共有:[{}]数据,共有:[{}]页", page, pageSize, total, totalPage);
        List<Actor> actorListByPagination = actorQueryResults.getResults();
        return actorListByPagination;
    }

    /**
     * 根据条件模糊查询，并指定某个字段的范围
     *
     * @param beginAge
     * @param endAge
     * @return
     */
    @Override
    public List<Actor> findByLikeNameAndEmailAndBetweenAgeOrderById(String actorName, String actorEmail, Integer beginAge, Integer endAge) {
        QActor qActor = QActor.actor;
        List<Actor> actorList = jpaQueryFactory.selectFrom(qActor)
                .where(
                        qActor.actorName.like(actorName + "%"),
                        qActor.actorEmail.like(actorEmail + "%"),
                        qActor.actorAge.between(beginAge, endAge)
                )
                .orderBy(
                        qActor.id.asc()
                )
                .fetch();
        return actorList;
    }



    //    ==================jpa整合dsl==================

    /**
     * 模糊查询并分页排序
     *
     * @param actorName
     * @param actorEmail
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<Actor> findByActorNameAndActorEmailPagination(String actorName, String actorEmail, Integer page, Integer pageSize) {
        QActor qActor = QActor.actor;
        // 模糊查询条件
        BooleanExpression expression = qActor.actorName.like(actorName + "%").and(qActor.actorEmail.like(actorEmail + "%"));
        // 排序、分页参数
        Sort sort = new Sort(Sort.Direction.DESC, "actorAge");
        PageRequest pageRequest = PageRequest.of(page < 0 ? 0 : page, pageSize, sort);
        Page<Actor> actorPage = querydslRepository.findAll(expression, pageRequest);
        log.info("分页查询第:[{}]页,pageSize:[{}],共有:[{}]数据,共有:[{}]页", page, pageSize, actorPage.getTotalElements(), actorPage.getTotalPages());
        List<Actor> actorListByPagination = actorPage.getContent();
        return actorListByPagination;
    }

    /**
     * 动态查询并分页排序
     *
     * @param actorAge
     * @param actorEmail
     * @param actorName
     * @param createTime
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<Actor> findByDynamicQuery(Integer actorAge, String actorEmail, String actorName, String createTime, Integer page, Integer pageSize) {
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
        return actorListByPagination;
    }
}