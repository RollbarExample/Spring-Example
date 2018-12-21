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

	@RequestMapping("/rollbar/generateError")
	public String generateError() {
		setupRollbar();
		String test = "Test";
		try {
			test.toString();
		} catch (Exception e) {
			e.printStackTrace();
			rollbar.error(e);
			return "Exception : " + e.getMessage();
		}
		return "SUCCESS";
	}

	private void setupRollbar() {
		Config config = ConfigBuilder.withAccessToken("ff70890763f540d4819dd0fb1d9d5e40").environment("Development")
				.codeVersion("f930978").build();
		rollbar = new Rollbar(config);
		rollbar.init(config);

	}

}
