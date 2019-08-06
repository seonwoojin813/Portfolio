package gmail.tjsdnwls813.Portfolio.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import gmail.tjsdnwls813.Portfolio.dao.UserDao;
import gmail.tjsdnwls813.Portfolio.domain.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public String emailcheck(String email) {
		//System.out.println(""+ email);
		return userDao.emailcheck(email);
	}

	@Override
	public String nicknamecheck(String nickname) {
		//System.out.println(""+ nickname);
		return userDao.nicknamecheck(nickname);

	}

	@Override
	public int register(MultipartHttpServletRequest request) {
		int result = -1;
		// 파라미터 읽기
		try {
			// 파라미터 인코딩 설정
			request.setCharacterEncoding("utf-8");
			String email = request.getParameter("email");
			String pw = request.getParameter("pw");
			String nickname = request.getParameter("nickname");
			// File 만 MultipartFile로 받고 나머지는 String
			MultipartFile image = request.getFile("image");
			// 파일을 업로드 할 경로를 설정
			// 기준이 webapp 또는 webcontent
			String uploadPath = request.getServletContext().getRealPath("/resources/userimage");
			// 파일명이 중복되지 않도록 하기위해서 랜덤한 문자열 생성
			UUID uid = UUID.randomUUID();
			// 원본 파일 이름 가져오기
			String filename = image.getOriginalFilename();
			// DAO의 파라미터 만들기
			User user = new User();
			// 선택한 파일이 있는 경우
			if (filename.length() > 0) {
				filename = uid + "_" + filename;
				// 실제 업로드할 경로 만들기
				String filepath = uploadPath + "/" + filename;
				// 파일 업로드
				File file = new File(filepath);
				image.transferTo(file);
			}
			// 파일이 없는 경우
			else {
				filename = "default.jpeg";
			}
			user.setImage(filename);
			user.setEmail(email);
			// 비밀번호는 암호화해서 저장
			user.setPw(BCrypt.hashpw(pw, BCrypt.gensalt(10)));
			user.setNickname(nickname);

			// DAO 메소드 호출
			result = userDao.register(user);

		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public User login(HttpServletRequest request) {
		// 참조형은 기본 값이 null
		User user = null;
		try {
			request.setCharacterEncoding("utf-8");
			String email = request.getParameter("email");
		    //System.out.println("email"+email); 
			String pw = request.getParameter("pw");
			// 로그인 관련된 DAO 메소드호출
			user = userDao.login(email);
			// email에 해당하는 데이터가 있다면
			if (user != null) {
				// 암호화된 비밀번호를 비교
				// 비밀번호가 일치하면 로그인성공
				// 그렇지 않으면 로그인 실패
				if (BCrypt.checkpw(pw, user.getPw())) {
					user.setPw(null);
				} else {
					user = null;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return user;
	}

	@Override
	public String convertAddress(String param) {
		// 주소같은경우는 참조형이니까 null로 만들어놓고
		String address = null;
		// 파라미터 읽기 
		// param의 구성  - latitude:longitude
		String [] coords = param.split(":");
		String latitude = coords[0];
		String longitude = coords[1];
		// 데이터를 다운로드 받을 URL을 생성
		String addr = "https://dapi.kakao.com/v2/local/geo/coord2address.json?x=" + longitude + "&y=" + latitude
				+ "&input_coord=WGS84";
		StringBuilder sb = new StringBuilder();
		try {
			// 다운로드 받을 주소 URL 객체로 생성
			// 파라미터에 한글이 있으면 한글을 인코딩 해야합니다.
			// URLEncoder.encode(문자열, 인코딩방식)
			URL url = new URL(addr);
			// url을 이용해서 HttpURLConnection을 생성
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			// 옵션 설정
			con.setConnectTimeout(30000);
			con.setUseCaches(false);
			// 헤더 추가
			con.addRequestProperty("Authorization", "KakaoAK 91ef79e25c29f0dff7c35c3b3a9c0228");
			// 스트림 가져오기
			InputStreamReader isr = new InputStreamReader(con.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			// 문자열을 읽어서 sb에 저장
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				sb.append(line);
			}
			// JSON 파싱을 위해서 String으로 변환
			String json = sb.toString();
			// JSONObject - JSON객체
			// JSONArray - JSON 배열
			// 객체는 get자료형("키")
			// 배열은 get 자료형(인덱스)

			// 시작이 { 이므로 JSO 객체로 변환
			// { (객체)이거는 카카오 예
			JSONObject root = new JSONObject(json);
			JSONArray documents = root.getJSONArray("documents");
			// 주소가 없는 경우를 대비해서 length가 1이상인 경우만 수행
			if (documents.length() > 0) {
				JSONObject first = documents.getJSONObject(0);
				JSONObject roadaddress = first.getJSONObject("address");
				address = roadaddress.getString("address_name");
			}

			isr.close();
			br.close();
			con.disconnect();

		} catch (Exception e) {
			System.out.println("주소 가져오기 에러:" + e.getMessage());

		}
		return address;
	}

}
