package edu.kh.project.common.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoardNameInterceptor implements HandlerInterceptor{ // <- HandlerInterceptor 상속!!

	// 후처리( Controller -> Dispatcher Servlet 사이)
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		// application scope 에서 boardTypeList 얻어오기
		ServletContext application = request.getServletContext();
		
		List<Map<String, Object>> boardTypeList = (List<Map<String, Object>>)application.getAttribute("boardTypeList");
		
		
		log.debug(boardTypeList.toString());
		
		String uri = request.getRequestURI(); // Uniform Resource Identifier : 통합 자원 식별자 (자원 이름,주소만 봐도 무엇인지 구별할수 있는 문자열)
//												 		ㄴ uri : /board/1    /      url : localhost/board/1
		
		log.debug("uri : " + uri);
		
		
		try { // /board 로 시작되는 요청 주소시 오류가 나면 ...
			int boardCode = Integer.parseInt( uri.split("/")[2] );
	//											["", "board", "1"]
			
			// boardTypeList 에서 boardCode를 하나씩 꺼내서 비교
			for(Map<String, Object> boardType : boardTypeList) {
				
				// Object 에서 한번에 int로 변환 불가, String으로 바꿔준 후 다시 int로 변환 필요
				//				String.valueOf(값) : String으로 변환
				
				int temp = Integer.parseInt( String.valueOf( boardType.get("boardCode") ));
				
				if(temp == boardCode) {
					request.setAttribute("boardName", boardType.get("boardName"));
					break;
				}
				
			}
		} catch (Exception e) {
					// 오류시 무시하고 그냥 실행
		}
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	} 

}
