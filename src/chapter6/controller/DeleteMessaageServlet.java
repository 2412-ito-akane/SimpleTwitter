package chapter6.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter6.logging.InitApplication;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/deleteMessage" })

public class DeleteMessaageServlet extends HttpServlet {

	/**
	* ロガーインスタンスの生成
	*/
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public DeleteMessaageServlet() {
		InitApplication application = InitApplication.getInstance();
		application.init();

	}

	//doPostメソッドをオーバーライドする
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());


		//画面からidを取得してMessageServletに渡す
		//type = hiddenで設定したidをintとして取得する
		int id = Integer.parseInt(request.getParameter("id"));

		//MessageServiceへdelete()を使ってidを渡す
		new MessageService().delete(id);

		//投稿をDBから削除後、TopServletを呼び出してつぶやきの再読み込みを行う
		//DeleteMessageServletのdoPost → TopServletのdoGet → top.jspの順で呼び出される
		response.sendRedirect("./");
	}

}
