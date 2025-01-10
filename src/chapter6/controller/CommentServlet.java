package chapter6.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chapter6.beans.Comment;
import chapter6.beans.User;
import chapter6.logging.InitApplication;
import chapter6.service.CommentService;

@WebServlet(urlPatterns = { "/comment" })
public class CommentServlet extends HttpServlet {

	/**
	* ロガーインスタンスの生成
	*/
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public CommentServlet() {
		InitApplication application = InitApplication.getInstance();
		application.init();

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		//ログの生成
		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		HttpSession session = request.getSession();
		Comment comment = new Comment();
		//画面からtextを取得してcommentにセット
		String text = request.getParameter("text");
		//この後にバリデーションチェックを挟む
		//空白や改行のみでない、140文字以内


		//バリデーションが問題なかったらcommentにセット
		comment.setText(text);

		//セッションからloginUserのidを取得
		User user = (User) session.getAttribute("loginUser");
		//commentのUserIdにログインユーザーのidをセット
		comment.setUserId(user.getId());

		//返信先のつぶやきの取得
		int messageId = Integer.parseInt(request.getParameter("messageId"));
		//返信先のつぶやきをmessageIdとしてセットする
		comment.setMessageId(messageId);

		//CommentServiceに渡す
		new CommentService().insert(comment);
		//topServletの呼び出し
		response.sendRedirect("./");
	}
}
