package net.zhaoxiaobin.jpabasic;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import net.zhaoxiaobin.jpabasic.dao.ActorFindRepository;
import net.zhaoxiaobin.jpabasic.domain.Actor;
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
 * @date 2020/11/21 11:36 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ActorFindTest {
    @Autowired
    private ActorFindRepository actorFindRepository;

    /**
     * 测试使用方法名映射成sql查询单条数据
     */
    @Test
    public void testFindByActorNameAndActorEmail() {
        Actor actor = actorFindRepository.findByActorNameAndActorEmail("高庸涵", "123456789@qq.com");
        log.info(JSONUtil.toJsonPrettyStr(actor));
    }

    /**
     * 测试使用方法名映射成sql查询多条数据并排序
     */
    @Test
    public void testFindByLikeActorName() {
        List<Actor> actorList = actorFindRepository.findByActorNameStartingWithAndIdGreaterThanEqualOrderByActorAge("name", 50L);
        log.info(JSONUtil.toJsonPrettyStr(actorList));
    }

    /**
     * 测试带条件查询并排序
     */
    @Test
    public void testFindBySort() {
        Sort sort = new Sort(Sort.Direction.DESC, "actorAge");
        List<Actor> actorList = actorFindRepository.findByIdGreaterThanEqual(90L, sort);
        log.info(JSONUtil.toJsonPrettyStr(actorList));
    }

    /**
     * 测试带条件分页查询并排序
     */
    @Test
    public void testFindByPagination() {
        // 指定排序，等价于 order by actor_age,create_time
        Sort sort = new Sort(Sort.Direction.ASC, "actorAge", "createTime");
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
//        Page<Actor> actorPage = actorRepository.findAll(pageRequest); // 不带条件分页查询
        Page<Actor> actorPage = actorFindRepository.findByIdGreaterThanEqual(1L, pageRequest); // 带条件分页查询
        log.info("共有:[{}]数据,共有:[{}]页", actorPage.getTotalElements(), actorPage.getTotalPages());
        List<Actor> actorListByPagination = actorPage.getContent();
        log.info(JSONUtil.toJsonPrettyStr(actorListByPagination));
    }

    /**
     * 测试带条件分页查询并排序，用JPQL方式
     */
    @Test
    public void testFindByPaginationWithJPQL() {
        // 指定的字段需要和实体类中属性相同，而非表中字段
        Sort sort = new Sort(Sort.Direction.ASC, "actorAge", "createTime");
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        Page<Actor> actorPage = actorFindRepository.findByPaginationWithJPQL(1L, pageRequest);
        log.info("共有:[{}]数据,共有:[{}]页", actorPage.getTotalElements(), actorPage.getTotalPages());
        List<Actor> actorListByPagination = actorPage.getContent();
        log.info(JSONUtil.toJsonPrettyStr(actorListByPagination));
    }

    /**
     * 测试带条件分页查询并排序，用原生sql方式
     */
    @Test
    public void testFindByPaginationWithSql() {
        // 如果使用原生sql，指定的字段就需要和表中字段相同
        Sort sort = new Sort(Sort.Direction.ASC, "actor_age", "create_time");
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        Page<Actor> actorPage = actorFindRepository.findByPaginationWithSql(1L, pageRequest);
        log.info("共有:[{}]数据,共有:[{}]页", actorPage.getTotalElements(), actorPage.getTotalPages());
        List<Actor> actorListByPagination = actorPage.getContent();
        log.info(JSONUtil.toJsonPrettyStr(actorListByPagination));
    }
}