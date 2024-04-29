package com.project.pawlife.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UploadFile {
	
	private int fileNo;
	private String filePath;
	private String fileOriginalName;
	private String fileRename;
	private String fileUploadDate;
	private int adoptNo;
	private int reviewNo;
	private int memberNo;
	private String fileFullpath;
	
}
