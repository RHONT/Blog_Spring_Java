package com.rhontprogect.blog.Repositories;

import com.rhontprogect.blog.Model.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article,Long> {
}
