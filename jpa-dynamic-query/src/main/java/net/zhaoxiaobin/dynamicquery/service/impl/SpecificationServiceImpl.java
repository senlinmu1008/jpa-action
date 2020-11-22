package net.zhaoxiaobin.dynamicquery.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.zhaoxiaobin.dynamicquery.dao.SpecificationRepository;
import net.zhaoxiaobin.dynamicquery.domain.Actor;
import net.zhaoxiaobin.dynamicquery.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoxb
 * @date 2020/11/17 9:33 下午
 */
@Service
@Transactional
@Slf4j
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecificationRepository specificationRepository;

    @Override
    public List<Actor> findBySpecification(Long id, Integer age, Integer page, Integer pageSize) {
        Specification<Actor> specification = (Specification<Actor>) (root, criteriaQuery, criteriaBuilder) -> {
            // 查询条件的集合
            List<Predicate> list = new ArrayList<>();

            // 条件1：id字段需要大于等于指定的id
            list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("id"), (id == null || id < 0) ? 0 : id));
            // 条件2：如果指定了age，则需要相等
            if(age != null && age > 0) {
                // 字段需要和实体类中属性相同，而非表中字段
                list.add(criteriaBuilder.equal(root.get("actorAge"), age));
            }

            // 转数组
            Predicate[] predicates = new Predicate[list.size()];
            list.toArray(predicates);
            return criteriaBuilder.and(predicates);
        };

        // 指定排序和分页
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page < 0 ? 0 : page, pageSize, sort);
        Page<Actor> actorPage = specificationRepository.findAll(specification, pageRequest);
        log.info("分页查询第:[{}]页,pageSize:[{}],共有:[{}]数据,共有:[{}]页", page, pageSize, actorPage.getTotalElements(), actorPage.getTotalPages());
        List<Actor> actorListBySpecification = actorPage.getContent();
        return actorListBySpecification;
    }
}