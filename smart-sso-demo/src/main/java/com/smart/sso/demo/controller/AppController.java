package com.smart.sso.demo.controller;

import javax.servlet.http.HttpServletRequest;

import com.smart.sso.client.local.constant.Oauth2Constant;
import com.smart.sso.client.local.rpc.Result;
import com.smart.sso.client.local.rpc.RpcAccessToken;
import com.smart.sso.client.local.rpc.SsoUser;
import com.smart.sso.client.local.util.Oauth2Utils;
import com.smart.sso.client.local.util.SessionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author Joe
 *
 */
@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/app")
public class AppController {

	@Value("${smart.sso.server-url}")
    private String serverUrl;
	@Value("${smart.sso.app-id}")
    private String appId;
	@Value("${smart.sso.app-secret}")
    private String appSecret;

	/**
	 * 初始页
	 * @param request
	 * @return
	 */
    @RequestMapping("/test")
	public Result test(HttpServletRequest request) {
		SsoUser user = SessionUtils.getUser(request);
		return Result.createSuccess(user);
	}

	/**
	 * 初始页
	 * @param request
	 * @return
	 */
	@RequestMapping
	public Result index(HttpServletRequest request) {
		SsoUser user = SessionUtils.getUser(request);
		return Result.createSuccess(user);
	}

	/**
	 * 登录提交
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public Result login(
			@RequestParam(value = Oauth2Constant.USERNAME, required = true) String username,
			@RequestParam(value = Oauth2Constant.PASSWORD, required = true) String password,
			HttpServletRequest request) {
		Result<RpcAccessToken> result = Oauth2Utils.getAccessToken(serverUrl, appId, appSecret, username, password);
		if (!result.isSuccess()) {
			return result;
		}
		SessionUtils.setAccessToken(request, result.getData());
		return Result.createSuccess().setMessage("登录成功");
	}
}
