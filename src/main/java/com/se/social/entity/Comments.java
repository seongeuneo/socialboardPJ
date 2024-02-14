package com.se.social.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@IdClass(OauthId.class)
public class Comments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int comment_id;

	@Column(nullable = false)
	private int board_id;

	@Column(nullable = false)
	private String useremail;

	@Column(nullable = false)
	private int comment_root;

	@Column(nullable = false)
	private String comment_regdate;

	@Column(nullable = false)
	private String comment_content;
	private String comment_moddate;
	private String comment_deldate;

	@Column(nullable = false)
	@ColumnDefault("'N'")
	private String comment_delyn;

	@Column(nullable = false)
	private int comment_steps;

}