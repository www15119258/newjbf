package com.cangzhitao.jbf.security.repository;

import org.springframework.stereotype.Repository;

import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.security.entities.SysPerm;

@Repository
public interface SysPermRepository extends BaseRepository<SysPerm, Long> {

}
