package gmail.tjsdnwls813.Portfolio.domain;

import lombok.Data;

@Data
public class Board {
	private int bno;
	private String title;
	private String content;
	private int readcnt;
	// 원래 자료형은 Date지만 사용을 편리하게 하기 위해서 String으로 변환
	private String regdate;
	private String updated;
	// 출력을 위한 변수
	private String dispdate;
	private String ip;
	private String email;
	private String nickname;
    //댓글의 개수 저장
	private int replycnt;
}
