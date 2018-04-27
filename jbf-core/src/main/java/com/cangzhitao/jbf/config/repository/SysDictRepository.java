package com.cangzhitao.jbf.config.repository;

import org.springframework.stereotype.Repository;

import com.cangzhitao.jbf.config.entities.SysDict;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;

@Repository
public interface SysDictRepository extends BaseRepository<SysDict, Long>  {

}
