package gmail.tjsdnwls813.Portfolio.domain;

import lombok.Data;

@Data
public class User {
	private String email;
	private String pw;
	private String nickname;
	private String image;
	
	//@Data말고 getters and setters , toString으로 해줘도됨
	

}
