package net.zhaoxiaobin.jpabasic.service;

/**
 * @author zhaoxb
 * @date 2020/11/17 7:47 下午
 */
public interface ActorUpdateService {
    int updateActorEmailById(String email, Long id);
    int updateCreateTimeById(Long id);
}
