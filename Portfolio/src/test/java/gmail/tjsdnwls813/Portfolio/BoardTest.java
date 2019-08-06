package gmail.tjsdnwls813.Portfolio;

import java.sql.Connection;

import javax.sql.DataSource;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//Spring 테스트 클래스를 만들어주는 어노테이션 설정
@RunWith(SpringJUnit4ClassRunner.class)
//Spring 설정 파일을 실행하기 위한 설정
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class BoardTest {
//테스트 할 객체 주입
	@Autowired
	private DataSource dataSource;


	//테스트 할 메소드
		@Test
		 public void conTest() {
			try(Connection con = dataSource.getConnection()){
				System.out.println("con:" + con);
			}catch(Exception e) {
				System.out.println("예외:" + e.getMessage());
			}
		}  


}
