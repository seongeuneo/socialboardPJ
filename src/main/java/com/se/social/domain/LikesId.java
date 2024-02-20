package com.se.social.domain;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikesId implements Serializable {
	private static final long serialVersionUID = 1L; // 기본값(초기화)
	
	private String useremail;

	private int board_id;
}
