package com.chejixing.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExportController {

	@RequestMapping("/export")
	public void export(@RequestParam String content, HttpServletResponse resp) throws UnsupportedEncodingException, IOException{
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment;filename=data.xls");
		byte[] bytes = content.getBytes();
//		resp.setContentLength(bytes.length);
		
		ServletOutputStream os = resp.getOutputStream();
		os.write(bytes);
		os.flush();
		os.close();
	}

}
