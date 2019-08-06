package gmail.tjsdnwls813.Portfolio.domain;

public class Criteria {
	// 출력할 데이터의 페이지 번호를 저장 할 프로퍼티
	private int page;
	// 페이지 당 출력할 데이터 개수를 저장할 변수
	private int perPageNum;
	// 현재 페이지 번호에 해당화는 데이터의 시작 번호
	private int pageStart;

	// 생성자 - 인스턴스 변수의 초기화에 많이 이용
	public Criteria() {
		// 페이지 번호는 기본적으로 1
		// 한 페이지에 출력되는 데이터개수는 10
		page = 1;
		perPageNum = 10;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}

	public int getPageStart() {
		// 페이지 번호에 해당하는 데이터의 시작번호
		// 1page - 1번
		// 2page - 11번
		// 3page -  21번 
		// 데이터 개수는 perPageNum 페이지 번호는 page
		pageStart = (page - 1) * perPageNum + 1;
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + ", pageStart=" + pageStart + "]";
	}

}
