package gmail.tjsdnwls813.Portfolio.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import gmail.tjsdnwls813.Portfolio.domain.Board;
import gmail.tjsdnwls813.Portfolio.domain.Criteria;
import gmail.tjsdnwls813.Portfolio.domain.SearchCriteria;

public interface BoardService {
	
		//게시글 작성을 위한 메소드
		public int register(HttpServletRequest request);
		
		//게시글 전체 목록을 가져오는 메소드
		public List<Board> list();

		//상세보기를 위한 메소드 - DAO작업은 2개(조회수 1증가, 글번호를 가지고 데이터찾아오기)이지만 Service는 1개 
		//Why? 사용자가 요청한 작업이 1개 이므로 Service도 1개
		public Board detail(int bno);
		
		//게시물 수정을 위해서 데이터를 찾아오는 메소드
		public Board updateView(int bno);
		
		//게시글 수정을 위한 메소드
		public int update(Board board);
		
		//게시글 삭제를 위한 메소드
		public int delete(int bno);
		
		//페이지 번호에 해당하는 데이터 목록을 가져오는 메소드
		//데이터 목록외에도 다른 데이터를 리턴해야 하기 때문에 리턴타입을 변경
		//public Map<String, Object> list(Criteria criteria);
		public Map<String, Object> list(SearchCriteria criteria);
		

}
