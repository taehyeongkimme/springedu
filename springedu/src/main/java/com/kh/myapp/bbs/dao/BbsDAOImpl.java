package com.kh.myapp.bbs.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.kh.myapp.bbs.dto.BbsDTO;


//@Repository(value="bbsDAOImpl")
public class BbsDAOImpl implements BbsDAO {

	private static Logger logger = LoggerFactory.getLogger(BbsDAOImpl.class);
	
	@Inject
	JdbcTemplate jdbcTemplate;
	
	// 글쓰기
	@Override
	public int write(BbsDTO bbsDTO) throws Exception {
		logger.info("write(BbsDTO bbsDTO) 호출");

		logger.info("void write(BbsDTO bbsDTO) 호출됨: "+bbsDTO);
		
		int cnt = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO board (bnum,btitle,bid,bnickname,bhit,bcontent,bgroup,bstep,bindent) ");
		sql.append("values(boardnum_seq.nextval,?,?,?,0,?,boardnum_seq.currval,0,0) ");
		
		cnt = jdbcTemplate.update(sql.toString(), new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, bbsDTO.getBtitle());
				ps.setString(2, bbsDTO.getBid());
				ps.setString(3, bbsDTO.getBnickname());
				ps.setString(4, bbsDTO.getBcontent());				
			}
		});
		
		if(cnt>0) {
			logger.info("등록건수 : "+ cnt);
		}else {
			logger.info("등록건수 : "+ cnt);
		}
		return cnt;
	}
	// 글목록
	@Override
	public List<BbsDTO> list() throws Exception {
		logger.info("ArrayList<BbsDTO> list() 호출");
		List<BbsDTO> list = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT bnum,btitle,bid,bnickname,bcdate,budate, ");
		sql.append("	     bhit,bcontent,bgroup,bstep,bindent ");
		sql.append("  FROM (select * from board order by bgroup desc, bstep asc) ");		
		sql.append(" where rownum >=1 and rownum < 25 ");		
		
		list = (List<BbsDTO>)jdbcTemplate.query(
				sql.toString(),
				new BeanPropertyRowMapper(BbsDTO.class)
				);
		return list;
	}

	@Override
	public List<BbsDTO> list(int startRec, int endRec) throws Exception {
		logger.info("ArrayList<BbsDTO>list(int startRec, int endRec)");
		
		List<BbsDTO> list = null;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select t2.* ");
		sql.append("from (select row_number() over (order by bgroup desc, bstep asc) as num,t1.* ");
		sql.append("		    from board t1 ) t2 ");
		sql.append("where num between ? and ? ");
		
		list = (ArrayList<BbsDTO>)jdbcTemplate.query(
				sql.toString(),
				new Object[] {startRec, endRec},
				new BeanPropertyRowMapper(BbsDTO.class)
				);		
		return list;
	}
	// 글읽기
	@Override
	public BbsDTO view(String bnum) throws Exception {
		logger.info("BbsDTO view(String bnum) 호출");
		BbsDTO bbsdto = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT bnum,btitle,bid,bnickname,bcdate,budate, " );
		sql.append("	   bhit,bcontent,bgroup,bstep,bindent " );
		sql.append(" from board ");
		sql.append(" where bnum = ? " );
		
		bbsdto = jdbcTemplate.queryForObject(sql.toString(), 
				new Object[] {bnum},
				new BeanPropertyRowMapper<BbsDTO>(BbsDTO.class) );
		
		//조회수증가
		updateHit(bbsdto.getBnum());
		return bbsdto;
	}
	private void updateHit(int bnum) {
		int cnt = 0;

		StringBuffer sql = new StringBuffer();
		sql.append("update board set bhit= bhit + 1 ");
		sql.append("where bnum=? ");

		cnt = jdbcTemplate.update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, bnum);
						
					}
			
		});

		if(cnt >0) {
			logger.info("조회건수증가 : " +cnt);
		}else {
			logger.info("조회건수증가:"+cnt);
		}
	}
	// 글수정
	@Override
	public int modify(BbsDTO bbsDTO) throws Exception {
		logger.info("modify(BbsDTO boardDTO) 호출");
		int cnt = 0;
		
		StringBuffer sql = new StringBuffer();
		sql.append("update board set  " );
		sql.append("	   btitle=? , bcontent=?, budate=sysdate " );
		sql.append(" where bnum = ? " );
		
		cnt = jdbcTemplate.update(sql.toString(), new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, bbsDTO.getBtitle());
				ps.setString(2, bbsDTO.getBcontent());
				ps.setInt(3, bbsDTO.getBnum());
				
			}
			
		});
		
		if(cnt > 0) {
			logger.info("수정갯수 : " + cnt);
		} else {
			logger.info("수정갯수 : " +cnt);
		}
		return cnt;
	}
	// 글삭제
	@Override
	public int delete(String bnum) throws Exception {
		logger.info("delete 호출");
		int cnt = 0;
		StringBuffer sql = new StringBuffer();
		
		// 답글 존재 유무
		if(isReply(bnum)) {
			// 답글존재
			sql.append("update board set isdel = 'Y' where bnum= ? ");
			cnt = jdbcTemplate.update(sql.toString(),bnum); 
		}else {
			// 답글 미존재
			sql.append("delete from board " );
			sql.append(" where bnum = ? " );
			
			
			cnt = jdbcTemplate.update(sql.toString(), new PreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, Integer.valueOf(bnum));
				}
			});
		}

		if(cnt >0) {
			logger.info("삭제건수 : " +cnt);
		}else {
			logger.info("삭제건수 : " +cnt);
		}
		return cnt;
	}
	
	private boolean isReply(String bnum) {
		boolean isYN = false;
		
		int cnt = 0;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(bnum) from board	" );
		sql.append("where bgroup in( select bgroup from board t1 " );
		sql.append("														 where t1.bnum=?) " );
		sql.append("  and bnum <> ? " );
		cnt = jdbcTemplate.queryForObject(
				sql.toString(), new Object[] {bnum,bnum}, Integer.class);
		
		if(cnt>0) {
			isYN = true;
		}
		return isYN;
	}
	
	// 원글가져오기
	@Override
	public BbsDTO replyView(String bnum) throws Exception {
		BbsDTO bbsdto = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT bnum,btitle,bid,bnickname,bcdate,budate, " );
		sql.append("	   bhit,bcontent,bgroup,bstep,bindent " );
		sql.append(" from board ");
		sql.append(" where bnum = ? " );
		
		bbsdto = jdbcTemplate.queryForObject(sql.toString(),
				new Object[] {bnum},
				new BeanPropertyRowMapper<BbsDTO>(BbsDTO.class));
		
		return bbsdto;
	}
	// 답글쓰기
	@Override
	public int reply(BbsDTO bbsDTO) throws Exception {
		int cnt1 = 0 ,cnt2 = 0;
		// 이전 답글 step 업데이트(원글 그룹에 대한 세로정렬 재정의)
		cnt1 = updateStep(bbsDTO.getBgroup(),bbsDTO.getBstep());
		
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO board (bnum, btitle, bid, bnickname, ");
		sql.append("bhit, bcontent, bgroup, bstep, bindent) ");
		sql.append("VALUES (boardnum_seq.nextval, ?, ?, ?, 0, ?, ?, ?, ?)  ");
		
		cnt2 = jdbcTemplate.update(sql.toString(), new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, bbsDTO.getBtitle());
				ps.setString(2, bbsDTO.getBid());
				ps.setString(3, bbsDTO.getBnickname());
				ps.setString(4, bbsDTO.getBcontent());
				ps.setInt(5, bbsDTO.getBgroup());		//원글 번호 = 원글그룹
				ps.setInt(6, bbsDTO.getBstep()+1);		//원글 그룹의 세로정렬(답글단계)
				ps.setInt(7, bbsDTO.getBindent()+1);		//원글 그룹의 가로정렬(들여쓰기)
				
			}
			
		});
		
		if(cnt2 > 0) {
			logger.info("답글갯수 : " + cnt2);
		} else {
			logger.info("답글갯수 : " +cnt2);
		}
		
		return cnt2;
		
	}
	
	
	private int updateStep(int bgroup, int bstep) {
		logger.info("updateStep 호출");
		
		int cnt = 0;
		
		StringBuffer sql = new StringBuffer();
		sql.append("update board set bstep = bstep + 1 where bgroup=? and bstep>? ");
		
		cnt = jdbcTemplate.update(sql.toString(), new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, bgroup);
				ps.setInt(2, bstep);
			}
			
		});
		
		if(cnt > 0) {
			logger.info("게시글 그룹/ 게시글 스탭 1증가"  + cnt);
		} else {
			logger.info("게시글 그룹/ 게시글 스탭 1증가"  + cnt);
		}
		
		return cnt;
	}
	// 게시글 총계
	@Override
	public int totalRec() throws Exception {
		logger.info("int totalREC() 호출");
		int totalcnt = 0;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) totalRec from board ");
		
		totalcnt = jdbcTemplate.queryForObject(sql.toString(), Integer.class);
		return totalcnt;
	}
	// 검색목록
	@Override
	public List<BbsDTO> list(int startRecord, int endRecord, String searchType, String keyword) throws Exception {
		List<BbsDTO> alist = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select t2.*  " );
		sql.append("from (select row_number() over (order by bgroup desc, bstep asc) as num, t1.* " );
		sql.append("		from board t1 ");
		sql.append("		where bnum > 0 ");
		
		switch(searchType){
		case "TC" : // 제목 + 내용
			sql.append("and btitle like '%' || ? || '%' or bcontent like '%' || ? || '%' "); // 오라클에서 || 는 문자열 연결 연산자
			break;
		case "T" : // 제목
			sql.append("and btitle like '%' || ? || '%' ");
			break;
		case "C" : // 내용
			sql.append("and bcontent like '%' || ? || '%' ");
			break;
		case "N" : // 닉네임
			sql.append("and bnickname like '%' || ? || '%'");
			break;
		case "I" : // 아이디
			sql.append("and bid like '%' || ? || '%' ");
			break;
		
		default: // 제목 + 내용 + 작성자
			sql.append("and btitle like '%' || ? || '%' or bcontent like '%' || ? || '%' and bid like '%' || ? || '%' ");
			break;
		}
		sql.append("	)t2 ");
		sql.append("where num between ? and ? ");
		
		Object[] obj = null; 
		
		switch(searchType){
		case "TC" : // 제목 + 내용
			obj = new Object[] {keyword,keyword,startRecord,endRecord};
			
			break;
		case "T" : // 제목
		case "C" : // 내용
		case "N" : // 닉네임
		case "I" : // 아이디
			obj = new Object[] {keyword,startRecord,endRecord};
			break;
		
		default: // 제목 + 내용 + 작성자
			obj = new Object[] {keyword,keyword,keyword,startRecord,endRecord};

			break;
		}
		
		
		alist = (List<BbsDTO>)jdbcTemplate.query(sql.toString(), obj, new BeanPropertyRowMapper<BbsDTO>(BbsDTO.class));
		
		return alist;
	}
	// 검색 총계
	@Override
	public int searchTotalRec(String searchType, String keyword) throws Exception {
		int totalRec = 0;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) totalRec " );
		sql.append("from (select row_number() over (order by bgroup desc, bstep asc) as num, t1.* " );
		sql.append("		from board t1 ");
		sql.append("		where bnum > 0 ");
		switch(searchType){
		case "TC" : // 제목 + 내용
			sql.append("and btitle like '%' || ? || '%' or bcontent like '%' || ? || '%' "); // 오라클에서 || 는 문자열 연결 연산자
			break;
		case "T" : // 제목
			sql.append("and btitle like '%' || ? || '%' ");
			break;
		case "C" : // 내용
			sql.append("and bcontent like '%' || ? || '%' ");
			break;
		case "N" : // 닉네임
			sql.append("and bnickname like '%' || ? || ");
			break;
		case "I" : // 아이디
			sql.append("and bid like '%' || ? || '%' ");
			break;
		
		default: // 제목 + 내용 + 작성자
			sql.append("and btitle like '%' || ? || '%' or bcontent like '%' || ? || '%' and bid like '%' || ? || '%' ");
			break;
		}
		sql.append("	)t2 ");
		Object [] obj = null;
		switch(searchType){
		case "TC" : // 제목 + 내용
			obj = new Object[] {keyword, keyword};
			break;
		case "T" : // 제목
		case "C" : // 내용
		case "N" : // 닉네임
		case "I" : // 아이디
			obj = new Object[] {keyword};
			break;
		
		default: // 제목 + 내용 + 작성자
			obj = new Object[] {keyword, keyword, keyword};
			break;
		}

		totalRec = (Integer)jdbcTemplate.queryForObject(sql.toString(), obj, Integer.class);
		
		return totalRec;
	}

}