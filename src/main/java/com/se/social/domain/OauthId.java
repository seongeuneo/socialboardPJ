package com.se.social.domain;

import java.io.Serializable;

import javax.persistence.IdClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OauthId implements Serializable {
	private static final long serialVersionUID = 1L; // 기본값(초기화)

	private String oauthtype;

	private String oauthtoken;

}