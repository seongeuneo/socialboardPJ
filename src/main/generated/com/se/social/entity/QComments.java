package com.se.social.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QComments is a Querydsl query type for Comments
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComments extends EntityPathBase<Comments> {

    private static final long serialVersionUID = 1359256621L;

    public static final QComments comments = new QComments("comments");

    public final NumberPath<Integer> board_id = createNumber("board_id", Integer.class);

    public final StringPath comment_content = createString("comment_content");

    public final StringPath comment_deldate = createString("comment_deldate");

    public final StringPath comment_delyn = createString("comment_delyn");

    public final NumberPath<Integer> comment_id = createNumber("comment_id", Integer.class);

    public final StringPath comment_moddate = createString("comment_moddate");

    public final StringPath comment_regdate = createString("comment_regdate");

    public final NumberPath<Integer> comment_root = createNumber("comment_root", Integer.class);

    public final NumberPath<Integer> comment_steps = createNumber("comment_steps", Integer.class);

    public final StringPath useremail = createString("useremail");

    public QComments(String variable) {
        super(Comments.class, forVariable(variable));
    }

    public QComments(Path<? extends Comments> path) {
        super(path.getType(), path.getMetadata());
    }

    public QComments(PathMetadata metadata) {
        super(Comments.class, metadata);
    }

}

