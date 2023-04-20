package org.zerock.guestbook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * *이런주석을 javadoc라고 함
 * 게시글 저장을 위한 Entity 클래스
 * @author lim
 * @since 2023.04.20.
 * @version 1.0
 *
 */
//겟터셋터가 없어도 값을 세팅해서 읽어올 수 있는 이유는 jpa가 작동할 때 java reflection이라는 기법을 사용하기 때문이다.
@Entity //실제로 저정할 데이터
@AllArgsConstructor
@NoArgsConstructor
//jpa가 기본생성자를 통해서 객체를 생성하기 때문에 꼭 기본생성자가 필요함(AllArgs~를 넣었기때문에 No~를 넣어준것, 없으면 자동으로 생성되기 때문에 없어도 됨)
//@Builder를 사용하려면 All~, No~가 필요함
@ToString
@Getter //객체.데이터 로 값을 불러오기 위해서 getter를 넣어줌
public class GuestBook extends BaseEntity {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY) //auto-increment
	private Integer gno;
	
	@Column(nullable = false)
	private String title;
	
	@Column(length = 1500, nullable = false)
	private String content;
	
	private String writer;
	
	/**
	 * 수정시 제목수정용 메소드
	 * @param title 제목
	 */
	public void changeTitle(String title) {
		this.title=title;
	}
	
	public void changeContent(String content) {
		this.content=content;
	}
}
