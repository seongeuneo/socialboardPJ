package com.se.social.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 528646989L;

    public static final QBoard board = new QBoard("board");

    public final StringPath board_content = createString("board_content");

    public final StringPath board_deldate = createString("board_deldate");

    public final ComparablePath<Character> board_delyn = createComparable("board_delyn", Character.class);

    public final NumberPath<Integer> board_id = createNumber("board_id", Integer.class);

    public final NumberPath<Integer> board_likes = createNumber("board_likes", Integer.class);

    public final StringPath board_moddate = createString("board_moddate");

    public final StringPath board_regdate = createString("board_regdate");

    public final StringPath board_title = createString("board_title");

    public final NumberPath<Integer> board_views = createNumber("board_views", Integer.class);

    public final StringPath useremail = createString("useremail");

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

