package com.cangzhitao.jbf.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.cms.domain.FriendLink;
import com.cangzhitao.jbf.cms.repository.FriendLinkRepository;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;

@Service
public class FriendLinkService extends BaseService<FriendLink, Long> implements IFriendLinkService {

	@Autowired
	private FriendLinkRepository friendLinkRepository;
	
	@Override
	public BaseRepository<FriendLink, Long> getRepository() {
		return friendLinkRepository;
	}
	
}
