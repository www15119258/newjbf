package com.cangzhitao.jbf.cms.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.cms.domain.Post;
import com.cangzhitao.jbf.cms.domain.PostCategoryRelation;
import com.cangzhitao.jbf.cms.domain.PostTagcloudRelation;
import com.cangzhitao.jbf.cms.domain.Tagcloud;
import com.cangzhitao.jbf.cms.repository.PostRepository;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;

@Service
public class PostService extends BaseService<Post, Long> implements IPostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	@Lazy
	private ITagcloudService tagcloudService;
	
	@Autowired
	@Lazy
	private IPostCategoryRelationService postCategoryRelationService;
	
	@Autowired
	@Lazy
	private IPostTagcloudRelationService postTagcloudRelationService;
	
	@Override
	public BaseRepository<Post, Long> getRepository() {
		return postRepository;
	}

	@Override
	@Transient
	public Post addPost(Post post) {
		String[] tagclouds = post.getTagclouds();
		post = this.save(post);
		List<Tagcloud> tagcloudList = new ArrayList<Tagcloud>();
		List<PostTagcloudRelation> postTagcloudRelations = new ArrayList<PostTagcloudRelation>();
		if(tagclouds!=null&&tagclouds.length>0) {
			for(int i=0;i<tagclouds.length;i++) {
				Tagcloud tagcloud = new Tagcloud();
				tagcloud.setName(tagclouds[i]);
				tagcloudList.add(tagcloud);
			}
			tagcloudList = tagcloudService.saveTagclouds(tagcloudList);
		}
		if(tagcloudList!=null&&tagcloudList.size()>0) {
			for(int i=0;i<tagcloudList.size();i++) {
				Tagcloud tagcloud = tagcloudList.get(i);
				PostTagcloudRelation postTagcloudRelation = new PostTagcloudRelation();
				postTagcloudRelation.setPostid(post.getId());
				postTagcloudRelation.setTagcloudid(tagcloud.getId());
				postTagcloudRelation.setCreateInfo();
				postTagcloudRelations.add(postTagcloudRelation);
			}
			postTagcloudRelationService.save(postTagcloudRelations);
		}
		
		List<PostCategoryRelation> postCategoryRelations = new ArrayList<PostCategoryRelation>();
		Long[] categorys = post.getCategorys();
		if(categorys!=null&&categorys.length>0) {
			for(int i=0;i<categorys.length;i++) {
				PostCategoryRelation postCategoryRelation = new PostCategoryRelation();
				postCategoryRelation.setPostid(post.getId());
				postCategoryRelation.setCategoryid(categorys[i]);
				postCategoryRelation.setCreateInfo();
				postCategoryRelations.add(postCategoryRelation);
			}
			postCategoryRelationService.save(postCategoryRelations);
		}
		return post;
	}
	
	@Override
	@Transient
	public Post updatePost(Post post) {
		String[] tagclouds = post.getTagclouds();
		Long[] categorys = post.getCategorys();
		post = this.save(post);
		List<Tagcloud> tagcloudList = new ArrayList<Tagcloud>();
		List<PostTagcloudRelation> postTagcloudRelations = new ArrayList<PostTagcloudRelation>();
		if(tagclouds!=null&&tagclouds.length>0) {
			for(int i=0;i<tagclouds.length;i++) {
				Tagcloud tagcloud = new Tagcloud();
				tagcloud.setName(tagclouds[i]);
				tagcloudList.add(tagcloud);
			}
			tagcloudList = tagcloudService.saveTagclouds(tagcloudList);
		}
		PostTagcloudRelation postTagcloudRelationExample = new PostTagcloudRelation().setNull().setProperty("postid", post.getId());
		List<PostTagcloudRelation> oldPostTagcloudRelation = postTagcloudRelationService.findAll(postTagcloudRelationExample);
		postTagcloudRelationService.deleteInBatch(oldPostTagcloudRelation);
		if(tagcloudList!=null&&tagcloudList.size()>0) {
			for(int i=0;i<tagcloudList.size();i++) {
				Tagcloud tagcloud = tagcloudList.get(i);
				PostTagcloudRelation postTagcloudRelation = new PostTagcloudRelation();
				postTagcloudRelation.setPostid(post.getId());
				postTagcloudRelation.setTagcloudid(tagcloud.getId());
				postTagcloudRelation.setCreateInfo();
				postTagcloudRelations.add(postTagcloudRelation);
			}
			postTagcloudRelationService.save(postTagcloudRelations);
		}
		PostCategoryRelation postCategoryRelationExample = new PostCategoryRelation().setNull().setProperty("postid", post.getId());
		List<PostCategoryRelation> oldPostCategoryRelation = postCategoryRelationService.findAll(postCategoryRelationExample);
		postCategoryRelationService.deleteInBatch(oldPostCategoryRelation);
		List<PostCategoryRelation> postCategoryRelations = new ArrayList<PostCategoryRelation>();
		if(categorys!=null&&categorys.length>0) {
			for(int i=0;i<categorys.length;i++) {
				PostCategoryRelation postCategoryRelation = new PostCategoryRelation();
				postCategoryRelation.setPostid(post.getId());
				postCategoryRelation.setCategoryid(categorys[i]);
				postCategoryRelation.setCreateInfo();
				postCategoryRelations.add(postCategoryRelation);
			}
			postCategoryRelationService.save(postCategoryRelations);
		}
		return post;
	}

	@Override
	public Page<Post> findPost(Map<String, Object> paramMap, Long category, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
		EntityType<PostCategoryRelation> postCategoryEntity = getEntityManager().getMetamodel().entity(PostCategoryRelation.class);
		Root<Post> postRoot = criteriaQuery.from(Post.class);
		Subquery<PostCategoryRelation> subQuery = criteriaQuery.subquery(PostCategoryRelation.class);
		Root<PostCategoryRelation> postCategoryroot = subQuery.from(PostCategoryRelation.class);
		subQuery.where(criteriaBuilder.equal(postCategoryroot.get(postCategoryEntity.getSingularAttribute("categoryid")), category));
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(criteriaBuilder.equal(postRoot.get("deleteFlag"), false));
		if(paramMap!=null&&paramMap.size()>0) {
			Iterator<String> it = paramMap.keySet().iterator();
			while(it.hasNext()) {
				String prop = it.next();
				Object value = paramMap.get(prop);
				predicates.add(criteriaBuilder.equal(postRoot.get(prop), value));
			}
		}
		predicates.add(postRoot.get("id").in(subQuery.select(postCategoryroot.get("postid"))));
		criteriaQuery.where(predicates.toArray(new Predicate[]{}));
		if(pageable.getSort()!=null) {
			Iterator<Order> it = pageable.getSort().iterator();
			while(it.hasNext()) {
				Order order = it.next();
				if(order.isDescending()) {
					criteriaQuery.orderBy(criteriaBuilder.desc(postRoot.get(order.getProperty())));
				} else {
					criteriaQuery.orderBy(criteriaBuilder.asc(postRoot.get(order.getProperty())));
				}
			}
		}
		TypedQuery<Post> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(pageable.getPageNumber()*pageable.getPageSize());
		typedQuery.setMaxResults(pageable.getPageSize());
		List<Post> list = typedQuery.getResultList();
		String sql = "select count(1) from t_cms_post t3 where t3.id in (select t2.id from t_cms_post_category t1,t_cms_category t2 where t1.categoryid=t2.id and t2.delete_flag=0 and t2.id=?)";
		long count = getJdbcTemplate().queryForObject(sql, Long.class, category);
		PageImpl<Post> page = new PageImpl<Post>(list, pageable, count);
		return page;
	}
	
}
