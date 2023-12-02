package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.QArticle;
import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>, // Article 필드에 대한 기본 검색기능 추가 (부분검색 기능은 불가)
        QuerydslBinderCustomizer<QArticle>{


    @Override
    default void customize(QuerydslBindings bindings, QArticle root){
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy); // 원하는 필드만 검색 범위에 추가하기
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // Like '${v}'
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // Like '%${v}%'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // Like '%${v}%'
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
//        bindings.bind(root.createdAt).first(DateExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}