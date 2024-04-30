package com.project.pawlife.email.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthKey {
	private int keyNo;
	private String Email;
	private String authKey;
	private String createTime;
}
