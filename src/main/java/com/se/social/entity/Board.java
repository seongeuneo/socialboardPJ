package com.se.social.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@IdClass(OauthId.class)
public class Board {

	@Id
	private int board_id;

	@Column(name = "useremail", nullable = false)
	private String useremail;

	@Column(name = "board_regdate", nullable = false)
	private String board_regdate;

	@Column(name = "board_title", nullable = false)
	private String board_title;

	@Column(name = "board_content", nullable = false)
	private String board_content;

	@Column(name = "board_moddate", nullable = true)
	private String board_moddate;

	@Column(name = "board_deldate", nullable = true)
	private String board_deldate;

	@Column(name = "board_delyn", nullable = false)
	@ColumnDefault("'N'")
	private char board_delyn;

	@Column(name = "board_likes", nullable = false)
	@ColumnDefault("0")
	private int board_likes;

	@Column(name = "board_views", nullable = false)
	@ColumnDefault("0")
	private int board_views;
}