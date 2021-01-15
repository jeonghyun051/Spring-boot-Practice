package com.cos.person.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	public List<User> fineAll() {

		List<User> users = new ArrayList<>();
		users.add(new User(1, "ssar", "1234", "0105222"));
		users.add(new User(2, "cos", "1234", "0105222"));
		users.add(new User(3, "love", "1234", "0105222"));

		return users;
	}

	public User fineById(int id) {

		return new User(1, "ssar", "1234", "0105222");

	}
	
	public void save(JoinReqDto dto) {

		System.out.println("DB에 insert하기");

	}
	
	public void delete(int id) {	
		System.out.println("DB에 삭제하기");
	}
	
	public void update(int id, UpdateReqDto dto) {
		// dao연결해서 실행하다가 인섹션 터진다. 그러면 리턴이 안된다.
		//throw new IllegalArgumentException("아규먼트를 잘못 넣음");
		System.out.println("DB에 수정하기");
	}
	
}
