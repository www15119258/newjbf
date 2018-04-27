package com.cangzhitao.jbf.statistics.repository;

import org.springframework.stereotype.Repository;

import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.statistics.entities.VisitLog;

@Repository
public interface VisitLogRepository extends BaseRepository<VisitLog, Long> {

}
