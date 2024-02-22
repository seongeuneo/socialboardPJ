package com.se.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.se.social.domain.LikesId;
import com.se.social.entity.Likes;
import com.se.social.entity.QLikes;
import com.querydsl.jpa.impl.JPAQueryFactory;

public interface LikesRepository extends JpaRepository<Likes, LikesId> {

}
