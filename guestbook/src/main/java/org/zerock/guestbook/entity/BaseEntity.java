package org.zerock.guestbook.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass //테이블생성을 안함(부모클래스로 사용하겠다는 의미)
@EntityListeners(value= {AuditingEntityListener.class}) //GuestbookApplication에 @EnableJpaAuditing을 넣어줘야 활성화가 됨
@Getter
public class BaseEntity {
	//작성시간
	@CreatedDate //persistence context에 entity(객체)가 생성될 때를 의미
	//DB에 없어서 새롭게 만들때만 셋팅, 있으면 null
	@Column(updatable = false) //처음에 한번 생성하면 update하지 마라(이게 없으면 수정할때마다 같이 시간이 변경됨)
	private LocalDateTime regDate;
	//수정시간
	@LastModifiedDate //마지막 수정시간을 의미
	private LocalDateTime modDate;
}
