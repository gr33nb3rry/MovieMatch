package org.moviematchers.moviematch.controller;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// This class disables default error pages. Since this application
// is a server, we don't need to process any additional information
// about error status in the model view controller spring boot web model.
@Controller
public class StatusCodeErrorController implements ErrorController {
	@RequestMapping("/error")
	public ResponseEntity<Void> handleError(HttpServletResponse request) {
		return ResponseEntity.status(request.getStatus()).build();
	}
}