package net.zhaoxiaobin.jpabasic.service.impl;

import cn.hutool.core.util.RandomUtil;
import net.zhaoxiaobin.jpabasic.dao.ActorSaveAndDeleteRepository;
import net.zhaoxiaobin.jpabasic.domain.Actor;
import net.zhaoxiaobin.jpabasic.service.ActorSaveAndDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoxb
 * @date 2020/11/17 7:49 下午
 */
@Service
@Transactional
public class ActorSaveAndDeleteServiceImpl implements ActorSaveAndDeleteService {
    @Autowired
    private ActorSaveAndDeleteRepository actorSaveAndDeleteRepository;

    /**
     * 新增1条数据
     *
     * @param actorName
     * @param actorAge
     * @param actorEmail
     * @return
     */
    @Override
    public Actor save(String actorName, int actorAge, String actorEmail) {
        Actor actor = new Actor();
        actor.setActorName(actorName);
        actor.setActorAge(actorAge);
        actor.setActorEmail(actorEmail);
        Actor storeObj = actorSaveAndDeleteRepository.save(actor);
        return storeObj;
    }

    /**
     * 批量新增
     */
    @Override
    public void batchSave() {
        List<Actor> actors = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Actor actor = new Actor();
            actor.setActorName("name:" + RandomUtil.randomString(5));
            actor.setActorAge(RandomUtil.randomInt(1, 100));
            actor.setActorEmail("email:" + RandomUtil.randomString(5));
            actors.add(actor);
        }
        actorSaveAndDeleteRepository.saveAll(actors);
    }

    /**
     * 删除指定id的一条数据
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        actorSaveAndDeleteRepository.deleteById(id);
    }
}