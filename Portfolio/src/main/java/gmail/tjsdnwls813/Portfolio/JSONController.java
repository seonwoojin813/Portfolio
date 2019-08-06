package gmail.tjsdnwls813.Portfolio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gmail.tjsdnwls813.Portfolio.domain.Reply;
import gmail.tjsdnwls813.Portfolio.service.ReplyService;
import gmail.tjsdnwls813.Portfolio.service.UserService;

@RestController //JSON을 리턴 할 수 있는 Controller

public class JSONController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReplyService replyService;

	// 이메일 중복검사 요청을 처리할 메소드
	@RequestMapping(value = "user/emailcheck", method = RequestMethod.GET)
	public Map<String, Object> emailcheck(@RequestParam("email") String email) {
		// 서비스 메소드 호출
		String result = userService.emailcheck(email);
		// 출력할 데이터 만들기
		Map<String, Object> map = new HashMap<String, Object>();
		// 중복된 이메일이 없으면 result 키에 true를 저장하고
		// 중복된 이메일이면 false를 저장
		map.put("result", result == null);
		
		return map;
	}
	//닉네임 중복검사 요청을 처리할 메소드
	@RequestMapping(value = "user/nicknamecheck", method = RequestMethod.GET)
	public Map<String, Object> nicknamecheck(@RequestParam("nickname") String nickname){
	//출력할 데이터 만들기
	Map<String, Object>map = new HashMap<String, Object>();
	//필요한 서비스 메소드 호출
	String result = userService.nicknamecheck(nickname);
	// 중복된 닉네임이 없으면 result 키에 true를 저장하고
	// 중복된 닉네임이면 false를 저장
	map.put("result", result == null);
	
	return map;
  }

	@RequestMapping(value = "/reply/register", method = RequestMethod.GET)
	public Map<String, Object> replyRegister(HttpServletRequest request) {
		int result = replyService.register(request);
		Map<String, Object> map = new HashMap<String, Object>();
		if (result > 0) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		return map;
	}
	
	//글번호에 해당하는 댓글 목록을 가져오는 메소드
	@RequestMapping(value="/reply/list", method = RequestMethod.GET)
	public List<Reply> replyList(@RequestParam("bno") int bno){
	     return replyService.list(bno);
	}
	
	// 댓글 번호에 해당하는 댓글을 삭제하는 메소드
	@RequestMapping(value = "/reply/delete", method = RequestMethod.GET)
	public Map<String, Object> delete(@RequestParam("rno") int rno) {
		int result = replyService.delete(rno);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result > 0);
		return map;
	}
	
	// 댓글 수정을 위한 메소드
	@RequestMapping(value = "/reply/update", method = RequestMethod.GET)
	public Map<String, Object> update(Reply reply) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = replyService.update(reply);
		if (result > 0) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		return map;
	}
	
	// 위도와 경도 문자열을 받아서 주소를 리턴하는 요청을 처리하는 메소드
	// 결과는 address속성에 들어감
	@RequestMapping(value = "address", method = RequestMethod.GET)
	public Map<String, String> address(@RequestParam("param") String param) {
		Map<String, String> map = new HashMap<>();
		String address = userService.convertAddress(param);
		map.put("address", address);
		return map;
	}

}
