package net.zhaoxiaobin.jpabasic.service;

import net.zhaoxiaobin.jpabasic.domain.Actor;

/**
 * @author zhaoxb
 * @date 2020/11/17 7:47 下午
 */
public interface ActorSaveAndDeleteService {
    Actor save(String actorName, int actorAge, String actorEmail);
    void batchSave();
    void delete(Long id);
}
