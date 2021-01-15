package com.cos.person.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.person.domain.CommonDto;
import com.cos.person.domain.JoinReqDto;
import com.cos.person.domain.UpdateReqDto;
import com.cos.person.domain.User;
import com.cos.person.domain.UserRepository;


@RestController
public class UserController {

//	private final UserRepositoty userRepository;	//다른방법은 @RequiredArgsConstructor 위에설정
	
	private UserRepository userRepository; //지금은 null
	
	public UserController(UserRepository userRepository) {	//ioc가 얘한테 값을 넣어줄려고 찾는다. 뭘보고 찾냐면 타입을(UserRepository) 찾아서 메모리에 있는지
		this.userRepository = userRepository;				//이것을 DI 의존성 주입 이라고 부른다, 그러면 이제 한번 띄어진 것을 재사용 가능하다.
	}
	
	// 주소는 이렇게 된다. http://localhost:8080/user
	@GetMapping("/user")
	public CommonDto<List<User>> findAll() {
		System.out.println("findAll()");
		//Reopository를 불러 올려면 원래 JSP에서는 new를 여기서 해줬는데 지금은 메모리에 띄어있는 상태이다. @Repository를 해줬기 때문 ioc가 관리중
		return new CommonDto<>(HttpStatus.OK.value(),userRepository.fineAll());//동작하는것은 MessageConverter가 동작한다.(이미 필터가 짜여있다. ) javaObject => Json String 자바오브젝트를 Json 문자열로 바꾼다()		
	}
	
	// http://localhost:8080/user/1
	@GetMapping("/user/{id}")	//문법인데 중괄호를 하면 id에 들어온 값을 바인딩 할수 있다.
	public CommonDto<User> findById(@PathVariable int id) {	//주소로 들어오는 것은 모두 String이다, @PathVariable을 통해 주소에 적혀있는 값을 int로 바꿔주는 것 
		System.out.println("findById");
		return new CommonDto<>(HttpStatus.CREATED.value(),userRepository.fineById(id));	//재사용 가능
	}
	
	// 1.
// 	http://localhost:8080/user 주소가 처음이랑 같지만 method가 달라서 get post에 따라 실행이 다름
//	@PostMapping("/user")
//	public void save(String username, String password, String phone) {	//함수에 걸려있는 필드가 오브젝트가 아니라 자료형이라서 알아서 x-www-form-url로 
//		System.out.println("save");										//파싱해서 이런 데이터가 들어왔다고 생각해서 parameter해서 값을 넘겨줌
//		System.out.println("username"+username); //ssar					//request.getparameter()
//	}	//이제 json데이터가 넘어왔을 때 밑에서 해결
	
	// 2. 
	// 유저에 정보를 받을 것
	// text/plain => @RequestBody 어노테이션 사용 String data로 data를 바로 읽을 수 있음
	// application/json => @RequestBody 어노테이션 + 오브젝트로 받기 // {"username":"ssar","password":"1234"}  
//	@PostMapping("/user")
//	public String save(@RequestBody User user) {	//버퍼로 바로 읽어준다
//		System.out.println("save");												
//		userRepository.save(user);
//		return "ok";	//ok로만 응답하는 건 위험 단순 문자이기 때문에 그렇기 때문에 밑으로 이동
//	}
	
	// 3.  
	@PostMapping("/user")
	public CommonDto<String> save(@RequestBody JoinReqDto dto) {	//버퍼로 바로 읽어준다
		System.out.println("save");												
		userRepository.save(dto);
		return new CommonDto<>(HttpStatus.OK.value(),"ok");	//ok이면 httpstatus 200응답 //데이터베이스에 값이 잘들어갔을 때는 HttpStatus.CREATED.value() 201번임
	}
	
	@DeleteMapping("/user/{id}")
	public CommonDto delete(@PathVariable int id) {
		System.out.println("delete");
		userRepository.delete(id);
		return new CommonDto<>(HttpStatus.OK.value());
		
	}
	
	@PutMapping("/user/{id}")
	public CommonDto update(@PathVariable int id, @RequestBody UpdateReqDto dto) {
		System.out.println("update");
		System.out.println("id:"+id);
		System.out.println("dto:"+dto);
		System.out.println("dto.password:"+dto.getPassword());
		userRepository.update(id, dto);
		return new CommonDto<>(HttpStatus.OK.value());
	}
}
