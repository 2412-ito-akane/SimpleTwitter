package chapter6.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chapter6.beans.User;

//対象になるのはsettingとeditなのでそれを指定する
@WebFilter(urlPatterns = {"/setting","/edit" })
public class LoginFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//セッションにユーザー情報があるかないかで分岐
		//セッションの取得
		HttpSession session = ((HttpServletRequest) request).getSession();
		//セッション内のログインユーザー情報のみについて取得する
		User loginUser = (User) session.getAttribute("loginUser");
		//エラーメッセージのためのList
        List<String> errorMessages = new ArrayList<String>();

		if (loginUser != null) {
			//ログインしているときの処理
			chain.doFilter(request, response);
		} else {
			//ログインしていないときログイン画面へ遷移する
			//エラーメッセージの表示
			errorMessages.add("ログインをしてください");
            session.setAttribute("errorMessages", errorMessages);
			((HttpServletResponse) response).sendRedirect("login");
		}
	}

	//initメソッド
	@Override
	public void init(FilterConfig config) {
	}

	//destroyメソッド
	@Override
	public void destroy() {
	}
}
