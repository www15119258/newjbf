package com.cangzhitao.jbf.cms.repository;

import org.springframework.stereotype.Repository;

import com.cangzhitao.jbf.cms.domain.Category;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {

}
