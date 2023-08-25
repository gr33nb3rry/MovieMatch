package org.moviematchers.moviematch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/*
This controller adds http endpoint which allows for the client to
fetch server status, without trying to fetch random resource, which
could lead to undefined results. This class have little to no logic,
due to how it is designed to work in synergy with spring security,
which would return status 401 if authentication was unsuccessful.
 */
@Controller
@RequestMapping("status")
public class ServerStatusController {
	@GetMapping
	public ResponseEntity<Void> getServerStatus() {
		return ResponseEntity.ok().build();
	}
}