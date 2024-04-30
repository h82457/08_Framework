package com.project.pawlife.email.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.project.pawlife.email.model.mapper.EmailMapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
	
	private final EmailMapper mapper;
	private final JavaMailSender mailSender;
	private final SpringTemplateEngine templetEngine; // html -> java 변환 템플릿
	
	// 이메일 발송
	@Override
	public String sendEmail(String htmlName, String email) {

		String authKey = createAuthKey();
		
		try {
			
		String subject = null;
		
		switch (htmlName) {
		case "sendEmail": 
			subject = "[PAWLIFE] 회원가입 인증번호 입니다."; break;
		}

		// 인증 메일 발송
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		
		helper.setTo(email);
		helper.setSubject(subject);
		helper.setText(loadHTML(authKey, htmlName), true);
		helper.addInline("logo", new ClassPathResource("static/images/logo.png"));

		mailSender.send(mimeMessage);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		// > AUTH_KEY 테이블에 저장후 비교
		Map<String, String> map = new HashMap<>();
		map.put("authKey", authKey);
		map.put("email", email);
		
		// 이전에 이메일 인증을 한 이메일인 경우
		int result = mapper.updateAuthKey(map);
		
		// 최초 인증
		if(result == 0) {
			result = mapper.insertAuthKey(map);
		}
		// 인증 실패
		if(result == 0 ) {
			return null;
		}
		
		return authKey;
	}
	
	/** HTML 파일을 읽어와 String으로 변환 (타임리프 적용<- import)
	 * @param authKey
	 * @param sendemail
	 * @return
	 */
	private String loadHTML(String authKey, String htmlName) {

		Context context = new Context();
		context.setVariable("authKey", authKey); // html에서 사용할 authKey값 추가
		
		// templates/email 폴더에서 htmlName과 같은 .html 파일 내용을 읽어와 String으로 변환
		return templetEngine.process("email/" + htmlName, context);
	}
	

	/** 인증번호 생성 (영어 대문자 + 소문자 + 숫자 6자리)
	 * @return
	 */
	public String createAuthKey() {
		
		String key = "";
		
		for(int i=0 ; i<6 ; i++) {
			
			int sel1 = (int)(Math.random() * 3); // 0일경우 아래에서 숫자 생성해서 추가
			
			if(sel1 == 0) {
				int num = (int)(Math.random() * 10); // 0~9
				key += num;
				
			} else {
				
				char ch = (char)(Math.random() * 26 + 65); // A~Z
				int sel2 = (int)(Math.random() *2); // 0<- 소문자 
				
				if(sel2 == 0) {
					ch = (char)(ch + ('a' - 'A')); // 대문자로 변경
				}
				key += ch;
			}
		}
		
		return key;
	}

	
	// 이메일 인증번호 비교
	@Override
	public int checkAuthKey(Map<String, Object> map) {
		
		return mapper.checkAuthKey(map);
	}
	
	
	
}
