package gmail.tjsdnwls813.Portfolio.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gmail.tjsdnwls813.Portfolio.domain.User;

@Repository
public class UserDao {
	@Autowired
	private SqlSession sqlSession;

	// email 중복 체크해주는 메소드
	public String emailcheck(String email) {
		//System.out.println(""+email);
		return sqlSession.selectOne("user.emailcheck", email);
	}

	// nickname 중복체크를 수행해주는 매소드
	public String nicknamecheck(String nickname) {
		//System.out.println("" + nickname);
		return sqlSession.selectOne("user.nicknamecheck", nickname);

	}
	
	//회원가입을 처리해주는 메소드
	public int register(User user) {
	  return sqlSession.insert("user.register", user);
	}
	
	//로그인 처리를 위한 메소드
	public User login(String email) {
    return sqlSession.selectOne("user.login", email);
    }
}
