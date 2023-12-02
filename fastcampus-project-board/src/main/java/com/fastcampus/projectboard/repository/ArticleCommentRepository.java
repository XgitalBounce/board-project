package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.domain.ArticleComment;
import com.fastcampus.projectboard.domain.QArticle;
import com.fastcampus.projectboard.domain.QArticleComment;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment,Long>,
        QuerydslPredicateExecutor<ArticleComment>,
        QuerydslBinderCustomizer<QArticleComment>
        {

        @Override
        default void customize(QuerydslBindings bindings, QArticleComment root){
            bindings.excludeUnlistedProperties(true);
            bindings.including(root.content, root.createdAt, root.createdBy); // 원하는 필드만 검색 범위에 추가하기
            bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // Like '%${v}%'
//        bindings.bind(root.createdAt).first(DateExpression::eq);
            bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
        }

}
