package com.example.demo;

import java.sql.SQLException;

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

import com.rollbar.notifier.Rollbar;
import com.rollbar.notifier.config.Config;
import com.rollbar.notifier.config.ConfigBuilder;

@RestController
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService repository;
	Rollbar rollbar;

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

	@RequestMapping("/rollbar")
	public LoginResponse authUser() {
		setupRollbar();
		LoginResponse response = new LoginResponse();
		String test=null;
		

		return response;
	}

	private void setupRollbar() {
		Config config = ConfigBuilder.withAccessToken("ff70890763f540d4819dd0fb1d9d5e40").environment("production")
				.codeVersion("3b8e920").build();
		rollbar = new Rollbar(config);
		rollbar.init(config);
	}
}
