package org.zerock.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.guestbook.entity.GuestBook;

public interface GuestBookRepository extends JpaRepository<GuestBook, Integer>, QuerydslPredicateExecutor<GuestBook>{
	
	@Query(value = "DELETE FROM guest_book WHERE gno>300", nativeQuery = true)
	public void del300();
	
	//select를 제외한 insert, update, delete를 쓸때는 어노테이션 @Modifying, @Transactional 붙여줘야함
    @Modifying
    @Transactional
	@Query("DELETE FROM GuestBook gb WHERE gb.gno>295")
	public void del300two();
}
