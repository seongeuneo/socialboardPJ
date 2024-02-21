package com.se.social.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.se.social.domain.LikesId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(LikesId.class)
public class Likes implements Serializable {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id", referencedColumnName = "board_id", insertable = false, updatable = false)
    private Board board;

    @Id
    private String useremail;

    @Id
    @Column(name = "board_id")
    private int board_id;
}
