package chapter6.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter6.beans.Message;
import chapter6.logging.InitApplication;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/edit" })

public class EditServlet extends HttpServlet {

	/**
	* ロガーインスタンスの生成
	*/
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public EditServlet() {
		InitApplication application = InitApplication.getInstance();
		application.init();

	}

	//まずはつぶやきを取得したい
	//doGetメソッドをオーバーライド
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		//top.jspからidを取得
		int id = Integer.parseInt(request.getParameter("id"));

		//MessageServiceにidを渡す
		//戻り値Messageを変数messageとして扱う
		Message message = new MessageService().edit(id);

		//messagesテーブルから受け取った情報をsetAttributeでキーと連携させる
		request.setAttribute("editMessage", message);

		//edit.jspの呼び出し
		request.getRequestDispatcher("/edit.jsp").forward(request, response);
	}

	//つぶやきの編集
	//doPostでedit.jspからMessageをまとめて取得する
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ログの生成
		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		//edit.jspから情報の取得
		//MessageServlet doPostを参考にして情報の取得をする
		//更新したつぶやきを変数textとして扱う↓
		String text = request.getParameter("text"); //request.getParameterは画面(jsp)上の情報を取ってくるもの
		//messageのtextに詰める↓
		Message message = new Message();
		message.setText(text);
		//idもupdateに渡すためにmessageに詰める
		message.setId(Integer.parseInt(request.getParameter("id")));

		//取得した情報をMessageServiceに渡す
		new MessageService().update(message);

		//つぶやきを更新後、TopServletを呼び出してつぶやきの再読み込みを行う
		//EditServletのdoPost → TopServletのdoGet → top.jspの順で呼び出される
		response.sendRedirect("./");
	}

}
