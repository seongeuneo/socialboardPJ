package com.se.social.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLikes is a Querydsl query type for Likes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikes extends EntityPathBase<Likes> {

    private static final long serialVersionUID = 537712675L;

    public static final QLikes likes = new QLikes("likes");

    public final NumberPath<Integer> board_id = createNumber("board_id", Integer.class);

    public final StringPath useremail = createString("useremail");

    public QLikes(String variable) {
        super(Likes.class, forVariable(variable));
    }

    public QLikes(Path<? extends Likes> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLikes(PathMetadata metadata) {
        super(Likes.class, metadata);
    }

}

