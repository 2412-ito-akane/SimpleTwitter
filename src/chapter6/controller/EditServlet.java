package chapter6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

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

		//エラーメッセージのリスト
		HttpSession session = request.getSession();
		List<String> errorMessages = new ArrayList<String>();

		//top.jspからidを取得
		String strId = request.getParameter("id");

		//取得したidが空白かの確認
		if (StringUtils.isBlank(strId) || !(strId.matches("^[0-9]*$"))) {
			//空白だったらエラーメッセージ
			errorMessages.add("不正なパラメータが入力されました");
			session.setAttribute("errorMessages", errorMessages);
			response.sendRedirect("./");
			return;
		}

		//正しいidの場合、int型に変換する
		int id = Integer.parseInt(strId);

		//MessageServiceにidを渡す
		//戻り値Messageを変数messageとして扱う
		Message message = new MessageService().select(id);
		//存在しないidのときの処理
		if (message == null) {
			//idを問い合わせた結果、nullが返ってきた場合にエラーメッセージ
			errorMessages.add("不正なパラメータが入力されました");
			session.setAttribute("errorMessages", errorMessages);
			response.sendRedirect("./");
			return;
		}

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
		int id = Integer.parseInt(request.getParameter("id"));//intに変換する前に確認処理

		//textの内容についてバリデーションチェック
		//トップ画面に遷移しない場合
		List<String> errorMessages = new ArrayList<String>();

		//messageのtextに詰める↓
		Message message = new Message();
		message.setText(text);

		//idもupdateに渡すためにmessageに詰める
		message.setId(id);

		if (!isValid(text, errorMessages)) {
			request.setAttribute("errorMessages", errorMessages);
			//入力した内容を保持するためにrequestにセットする
			//edit.jspでは ${editMessage.text} が呼び出されているのでそこにmessageをセットする
			request.setAttribute("editMessage", message);
			request.getRequestDispatcher("edit.jsp").forward(request, response);
			return;
		}

		//取得した情報をMessageServiceに渡す
		new MessageService().update(message);

		//つぶやきを更新後、TopServletを呼び出してつぶやきの再読み込みを行う
		//EditServletのdoPost → TopServletのdoGet → top.jspの順で呼び出される
		response.sendRedirect("./");
	}

	//isValidを追加
	private boolean isValid(String text, List<String> errorMessages) {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		//トップに遷移せずに表示させるエラーメッセージが表示
		if (StringUtils.isBlank(text)) {
			errorMessages.add("入力してください");
		} else if (140 < text.length()) {
			errorMessages.add("140文字以下で入力してください");
		}

		if (errorMessages.size() != 0) {
			return false;
		}
		return true;
	}

}
