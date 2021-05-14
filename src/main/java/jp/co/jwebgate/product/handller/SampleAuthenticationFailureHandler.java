package jp.co.jwebgate.product.handller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 認証失敗時に呼び出されるハンドラクラス
 * @author y-wayama
 *
 */

public class SampleAuthenticationFailureHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(
				HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse,
				AuthenticationException authenticationException)
					throws IOException, ServletException{

		//エラーIDを用意する
		String errorId = "";
		//ExceptionからエラーIDをセットする
		if(authenticationException instanceof BadCredentialsException){
			errorId = "ERR001";
		}
		//ログイン画面にエラーIDをリダイレクトする
		httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login"
				+ "?error=" + errorId);

	}

}
