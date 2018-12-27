package io.feikuai.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.feikuai.service.GensService;

/**
 * demo
 * @author liudo
 * @date 2018/12/26
 */
@Controller
@RequestMapping("/")
public class DemoController {

	/**
	 * 生成代码服务类
	 */
	@Resource(name = "gensService")
	private GensService gensService;

	/**
	 * 生成代码
	 */
	@RequestMapping("/gen/{tableName}")
	public void gensCode(@PathParam("tableName") String tableName, HttpServletResponse response) throws IOException {
		byte[] data = gensService.gensCode(tableName);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"fei-kuai.zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");

		IOUtils.write(data, response.getOutputStream());
	}

	/**
	 * 生成代码
	 */
	@RequestMapping("/gens")
	public void gensCodes(HttpServletResponse response) throws IOException {
		byte[] data = gensService.gensCodes();
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"fei-kuai.zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");

		IOUtils.write(data, response.getOutputStream());
	}
}
