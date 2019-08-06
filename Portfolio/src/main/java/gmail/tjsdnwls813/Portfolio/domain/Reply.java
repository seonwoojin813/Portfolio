package gmail.tjsdnwls813.Portfolio.domain;

import lombok.Data;

@Data
public class Reply {
	private int rno;
	private String replytext;
	//Date 타입은 연산을 할 때는 편리하지만 출력을 할 때는 불편해서 String으로
	private String regdate;
	//출력할 날짜 포맷을 저장할 프로퍼티
	private String datedisp;
	private String email;
	private String nickname;
	private int bno;
	}
