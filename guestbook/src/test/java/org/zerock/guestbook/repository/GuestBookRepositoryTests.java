package org.zerock.guestbook.repository;


import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.GuestBook;
import org.zerock.guestbook.entity.QGuestBook;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;


@SpringBootTest
public class GuestBookRepositoryTests {

	@Autowired //필드주입(직접주입)
	GuestBookRepository guestBookRepository;
	
	@Test
	public void testInsert() {
		GuestBook guestbook = new GuestBook(null, "제목", "내용", "작성자");
		guestBookRepository.save(guestbook);	
	}
	
	@Test
	public void testModify() {
		GuestBook guestbook = new GuestBook(2, "제목", "시간이 바뀌나요, 다시 수정합니당", "작성자");
		guestBookRepository.save(guestbook);	
		
		guestbook.changeTitle(null); //changeTitle()에 javadoc를 설정해놔서 마우스를 올리면 설명을 볼 수 있음
	}
	
	@Test
	public void testUpdate() {
		//조회
		Optional<GuestBook> result= guestBookRepository.findById(300);
		if(result.isPresent()) { //300번이 존재한다면
			GuestBook guestbook= result.get();
			guestbook.changeContent("수정합니다~");
			guestbook.changeTitle("제목도 수정합니다~");
			
			guestBookRepository.save(guestbook); //save는 무조건 조회(select)를 하고 다음 쿼리를 수행함
		}
	}
	
	@Test
	public void testDummies() {
		//IntStream.rangeClosed(1, 300).forEach(x -> guestBookRepository.save(new GuestBook(null,"title"+x,"content"+x,"writer"+x)));
		
		for(int i=1; i<=300;i++) {
			GuestBook guestbook = new GuestBook(null, "제목"+i, "내용"+i, "작성자"+(i%10));
			guestBookRepository.save(guestbook);	
		}
	}
	
	//Querydsl 사용법
	@Test
	public void testQuery1() {
		//페이지처리를 위한 페이지객체 생성
		Pageable pageable= PageRequest.of(0, 10, Sort.by("gno").descending()); //글번호 내림차순으로 정렬해서 10개씩 봤을 때 첫페이지를 보여주라는 의미
		
		////////Querydsl 사용법//////////
		//1. Querydsl 라이브러리 추가
		//2. Querydsl관련 빌드정보들 추가 (build.gradle에 정보 추가)
		//3. 빌드후에 QClass 이용해서 작업
		//4. 추가로 Repository 인터페이스 설정시 Querydsl 관련 추가상속(GuestBookRepository에서 추가하기)
		QGuestBook qGuestBook= QGuestBook.guestBook;
		
		//Where 조건을 셋팅하기 위한 객체
		BooleanBuilder builder= new BooleanBuilder();
		
		//원하는 Where절 조건 셋팅(제목 or 작성자 or 내용)
		BooleanExpression expression= qGuestBook.title.contains("9");
		BooleanExpression expression2= qGuestBook.content.contains("8");
		BooleanExpression expression3= qGuestBook.writer.contains("7");
		expression.or(expression2).or(expression3);
		
		//Where조건 세팅
		builder.and(expression);
		
		//실행                                       //query 인터페이스를 통해서 추가된 메소드 
		Page<GuestBook> list= guestBookRepository.findAll(builder, pageable);
		
		list.forEach(x -> System.out.println(x));
	}
	
	@Test
	public void delete() {
		guestBookRepository.deleteById(300);
	}
	
	@Test
	public void delete1() {
		//1~299까지만 남겨두는 프로그램을 만들어서 동작시키기
		guestBookRepository.del300();
	}
	
	@Test
	public void delete2() {
		//1~295까지만 남겨두는 프로그램을 만들어서 동작시키기
		guestBookRepository.del300two();
	}
	
	@Test
	public void quiz1() {
		Pageable pageable= PageRequest.of(0,3);
		guestBookRepository.findAll(pageable).get().forEach(x->System.out.println(x));
	}
}
