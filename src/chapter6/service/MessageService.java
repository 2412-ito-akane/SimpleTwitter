package chapter6.service;

import static chapter6.utils.CloseableUtil.*;
import static chapter6.utils.DBUtil.*;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Message;
import chapter6.beans.UserMessage;
import chapter6.dao.MessageDao;
import chapter6.dao.UserMessageDao;
import chapter6.logging.InitApplication;

public class MessageService {

	/**
	* ロガーインスタンスの生成
	*/
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public MessageService() {
		InitApplication application = InitApplication.getInstance();
		application.init();

	}

	public void insert(Message message) {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		Connection connection = null;
		try {
			connection = getConnection();
			new MessageDao().insert(connection, message);
			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} catch (Error e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} finally {
			close(connection);
		}
	}

	//selectメソッド
	//指定したuserIdのつぶやきだけ見えるようにする
	public List<UserMessage> select(String userId, String start, String end) {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		final int LIMIT_NUM = 1000;

		Connection connection = null;
		try {
			connection = getConnection();

			Timestamp startTimestamp;
			Timestamp endTimestamp;

			//日時が指定されたときにDaoへ渡すように分岐する
			//開始日時についての分岐
			if(start != null) {
				String startDate = start + " 00:00:00.000";
				startTimestamp = Timestamp.valueOf(startDate);
			} else {
				String startDate = "2020-01-01 00:00:00.000";
				startTimestamp = Timestamp.valueOf(startDate);
			}
			
			//終了日時についての分岐
			if(end != null) {
				String endDate = end + " 23:59:59";
				endTimestamp = Timestamp.valueOf(endDate);
			} else {
				endTimestamp = new Timestamp(System.currentTimeMillis());
			}

			Integer id = null;
			if (!StringUtils.isEmpty(userId)) {
				//userIdが指定されているならの処理
				id = Integer.parseInt(userId);
			}

			List<UserMessage> messages = new UserMessageDao().select(connection, id, LIMIT_NUM, startTimestamp, endTimestamp);
			commit(connection);

			return messages;
		} catch (RuntimeException e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} catch (Error e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} finally {
			close(connection);
		}
	}

	//deleteメソッドを追加
	//DeleteMessageServletから渡されたint idをMessageDaoへ渡す
	public void delete(int id) {
		//ログの作成
		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		//DBにアクセスするためにgetConnectionを使いたい
		Connection connection = null;
		try {
			connection = getConnection();
			//MessageDaoのdeleteメソッドへidを渡す
			new MessageDao().delete(connection, id);
			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} catch (Error e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} finally {
			close(connection);
		}
	}

	//selectメソッドを追加
	public Message select(int id) {
		//ログの作成
		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		//DBにアクセスするためにgetConnectionを使いたい
		Connection connection = null;
		try {
			connection = getConnection();
			//MessageDaoのeditメソッドへidを渡す
			Message message = new MessageDao().select(connection, id);
			//DBの操作を確立させる
			commit(connection);

			//Daoからの戻り値をMessageとしてSevletに返す
			return message;
		} catch (RuntimeException e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} catch (Error e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} finally {
			close(connection);
		}

	}

	//updateメソッド追加
	public void update(Message message) {
		//ログの作成
		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		//DBにアクセスするためにgetConnectionを使う
		//Connectionの初期化
		Connection connection = null;
		try {
			connection = getConnection();
			//MessageDaoのupdateメソッドへ渡す
			new MessageDao().update(connection, message);
			//DBの操作を確立させる
			commit(connection);

		} catch (RuntimeException e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} catch (Error e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} finally {
			close(connection);
		}

	}

}
