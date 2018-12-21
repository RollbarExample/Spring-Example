package com.example.demo;

import java.sql.SQLException;
import java.util.HashMap;

import javax.validation.Valid;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rollbar.api.payload.data.Person;
import com.rollbar.notifier.Rollbar;
import com.rollbar.notifier.config.Config;
import com.rollbar.notifier.config.ConfigBuilder;

@RestController
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	Rollbar rollbar;
	
	@RequestMapping("/rollbar/Testing")
	public String testing() {
		setupRollbar();
		String test = "Test";
		try {
			test.toString();
		} catch (Exception e) {
			e.printStackTrace();
			 HashMap<String,Object> map=new HashMap<String, Object>();
			 map.put("Id","123");
			 map.put("User Name","John Doe");
			 map.put("Email","john@doe.com");
			 rollbar.error(e,map);
			
			return "Exception : "+e.getMessage();
		}
		return "SUCCESS";
	}

	private void setupRollbar() {
		Config config = ConfigBuilder.withAccessToken("ff70890763f540d4819dd0fb1d9d5e40").environment("Development")
				.codeVersion("f930978").build();
		rollbar = new Rollbar(config);
		rollbar.init(config);

	}

	@Autowired
	private UserService repository;

	@RequestMapping("/auth")
	public LoginResponse authUser(@RequestHeader(value = "authToken") String authToken) {
		LoginResponse response = new LoginResponse();
		setupRollbar();
		if (!Constant.authTken.equalsIgnoreCase(authToken)) {
			response.setErrorCode(Constant.INVALID_TOKEN_CODE + "");
			response.setErrorMsg(Constant.INVALID_TOKEN_MSG);
			LOGGER.error(Constant.INVALID_TOKEN_MSG);
			return response;
		}
		User user = new User();
		user.setName("Test User");
		user.setAge("25");
		response.setStatus(true);
		response.setErrorCode("SUCCESS");
		response.setErrorMsg("Success");
		response.setUserDetail(user);
		return response;
	}

	@PostMapping("/addUser")
	public GenericResponse createQuestion(@Valid @RequestBody User request) {
		GenericResponse response = new GenericResponse();
		try {
			UserEntity entity = new UserEntity();
			entity.setAge(request.getAge());
			entity.setName(request.getName());
			repository.save(entity);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("Invalid sql query", e);
		}
		return response;
	}

	@RequestMapping("/rollbar/Production")
	public LoginResponse authUser() {
		setupRollbar();
		LoginResponse response = new LoginResponse();
		String test=null;
		try {
			test.toString();
		} catch (Exception e) {
			// TODO: handle exception
			rollbar.error(e);
		}
		return response;
	}
	@RequestMapping("/rollbar/Staging")
	public LoginResponse staging() {
		setupRollbar();
		LoginResponse response = new LoginResponse();
		String[] test= {"Test1"};
		try {
			String testString=test[1];
		} catch (Exception e) {
			rollbar.error(e);
		}
		return response;
	}
	
}
