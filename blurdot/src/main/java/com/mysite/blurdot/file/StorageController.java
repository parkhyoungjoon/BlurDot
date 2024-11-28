package com.mysite.blurdot.file;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StorageController {
	@GetMapping("/storage/list")
	public String stoage_list() {
		return ("storage/storage_list");
	}
}
