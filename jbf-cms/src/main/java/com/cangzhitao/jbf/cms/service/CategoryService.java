package com.cangzhitao.jbf.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.cms.domain.Category;
import com.cangzhitao.jbf.cms.domain.PostCategoryRelation;
import com.cangzhitao.jbf.cms.repository.CategoryRepository;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;

@Service
public class CategoryService extends BaseService<Category, Long> implements ICategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public BaseRepository<Category, Long> getRepository() {
		return categoryRepository;
	}

	@Override
	public Category getDefault(List<Category> categorys) {
		if(categorys==null||categorys.size()==0) {
			return null;
		}
		Category category = new Category().setNull().setProperty("deleteFlag", false);
		List<Category> list = findAll(category);
		Map<Long, Category> map = new HashMap<Long, Category>();
		for(int i=0;i<list.size();i++) {
			Category t = list.get(i);
			map.put(t.getId(), t);
		}
		for(int i=0;i<categorys.size();i++) {
			Category c = categorys.get(i);
			Category parent = map.get(c.getParent());
			while(parent!=null) {
				c.setDeep(c.getDeep()+1);
				parent = map.get(parent.getParent());
			}
		}
		int max = 0;
		Category defaultCategory = categorys.get(0);
		for(int i=0;i<categorys.size();i++) {
			if(categorys.get(i).getDeep()>max) {
				max = categorys.get(i).getDeep();
				defaultCategory = categorys.get(i);
			}
		}
		return defaultCategory;
	}

	@Override
	public List<Category> getCategoryByPostId(Long postid) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
		EntityType<PostCategoryRelation> postCategoryEntity = getEntityManager().getMetamodel().entity(PostCategoryRelation.class);
		Root<Category> categoryRoot = criteriaQuery.from(Category.class);
		Subquery<PostCategoryRelation> subQuery = criteriaQuery.subquery(PostCategoryRelation.class);
		Root<PostCategoryRelation> postCategoryRoot = subQuery.from(PostCategoryRelation.class);
		subQuery.where(criteriaBuilder.equal(postCategoryRoot.get(postCategoryEntity.getSingularAttribute("postid")), postid));
		criteriaQuery.where(criteriaBuilder.equal(categoryRoot.get("deleteFlag"), false), categoryRoot.get("id").in(subQuery.select(postCategoryRoot.get("categoryid"))));
		TypedQuery<Category> typedQuery = getEntityManager().createQuery(criteriaQuery);
		List<Category> list = typedQuery.getResultList();
		return list;
	}
	
}
