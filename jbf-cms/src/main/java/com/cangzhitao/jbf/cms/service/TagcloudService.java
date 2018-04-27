package com.cangzhitao.jbf.cms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.cms.domain.PostTagcloudRelation;
import com.cangzhitao.jbf.cms.domain.Tagcloud;
import com.cangzhitao.jbf.cms.repository.TagcloudRepository;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;

@Service
public class TagcloudService extends BaseService<Tagcloud, Long> implements ITagcloudService {

	@Autowired
	private TagcloudRepository tagcloudRepository;
	
	@Override
	public BaseRepository<Tagcloud, Long> getRepository() {
		return tagcloudRepository;
	}

	@Override
	@Transient
	public Tagcloud saveTagcloud(Tagcloud tagcloud) {
		Tagcloud example = new Tagcloud().setNull().setProperty("deleteFlag", false).setProperty("name", tagcloud.getName());
		Tagcloud old = this.findOne(example);
		if(old==null) {
			tagcloud.setCreateInfo();
			tagcloud.setLastUse(new Date());
			tagcloud.setRefers(1);
			this.save(tagcloud);
			return tagcloud;
		}
		old.setLastUse(new Date());
		//这种计算方式在并发量大的情况下将不准
		old.setRefers(old.getRefers()+1);
		old.setUpdateInfo();
		tagcloud = this.save(old);
		return tagcloud;
	}

	@Override
	@Transient
	public List<Tagcloud> saveTagclouds(Iterable<Tagcloud> tagclouds) {
		if(tagclouds==null) {
			return new ArrayList<Tagcloud>();
		}
		List<Tagcloud> list = new ArrayList<Tagcloud>();
		Iterator<Tagcloud> it = tagclouds.iterator();
		while(it.hasNext()) {
			Tagcloud tagcloud = it.next();
			tagcloud = this.saveTagcloud(tagcloud);
			list.add(tagcloud);
		}
		return list;
	}

	@Override
	public List<Tagcloud> getTagcloudByPostId(Long postid) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Tagcloud> criteriaQuery = criteriaBuilder.createQuery(Tagcloud.class);
		EntityType<PostTagcloudRelation> postTagcloudEntity = getEntityManager().getMetamodel().entity(PostTagcloudRelation.class);
		Root<Tagcloud> tagcloudRoot = criteriaQuery.from(Tagcloud.class);
		Subquery<PostTagcloudRelation> subQuery = criteriaQuery.subquery(PostTagcloudRelation.class);
		Root<PostTagcloudRelation> postTagcloudRoot = subQuery.from(PostTagcloudRelation.class);
		subQuery.where(criteriaBuilder.equal(postTagcloudRoot.get(postTagcloudEntity.getSingularAttribute("postid")), postid));
		criteriaQuery.where(criteriaBuilder.equal(tagcloudRoot.get("deleteFlag"), false), tagcloudRoot.get("id").in(subQuery.select(postTagcloudRoot.get("tagcloudid"))));
		TypedQuery<Tagcloud> typedQuery = getEntityManager().createQuery(criteriaQuery);
		List<Tagcloud> list = typedQuery.getResultList();
		return list;
	}
	
}
