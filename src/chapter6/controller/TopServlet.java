package chapter6.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.User;
import chapter6.beans.UserComment;
import chapter6.beans.UserMessage;
import chapter6.logging.InitApplication;
import chapter6.service.CommentService;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/index.jsp" })

public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	* ロガーインスタンスの生成
	*/
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public TopServlet() {
		InitApplication application = InitApplication.getInstance();
		application.init();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		//セッションからユーザーの取得ができたら、変数isShowMessageFormにtrueを設定する
		boolean isShowMessageForm = false;
		User user = (User) request.getSession().getAttribute("loginUser");
		if (user != null) {
			isShowMessageForm = true;
		}

		//input type dateから日時指定があったときは、それをサービスへわたす
		String startDate = request.getParameter("startDate");
		if (StringUtils.isBlank(startDate)) {
			startDate = null;
		}
		String endDate = request.getParameter("endDate");
		if (StringUtils.isBlank(endDate)) {
			endDate = null;
		}

		//userIdをDBから取得してMessageServiceのselectへ
		String userId = request.getParameter("user_id");
		List<UserMessage> messages = new MessageService().select(userId, startDate, endDate);

		//commentServiceへidを渡す
		List<UserComment> comments = new CommentService().select();

		//入力した日付を保持するためにrequestにセットする
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("messages", messages);
		request.setAttribute("comments", comments); //つぶやきの返信
		request.setAttribute("isShowMessageForm", isShowMessageForm);
		request.getRequestDispatcher("/top.jsp").forward(request, response);

	}

}
