package org.prj.chat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChatSubmitServlet
 */
@WebServlet("/ChatListServlet")
public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String listType = request.getParameter("listType");
		if (listType == null || listType.equals("")) {
			response.getWriter().write("");
		} else if (listType.equals("today")) {
			response.getWriter().write(getToday());
		} else if (listType.equals("ten")) {
			response.getWriter().write(getTen());
		} else {
			try {
				Integer.parseInt(listType);
				response.getWriter().write(getId(listType));
			} catch(Exception e) {
				response.getWriter().write("");
			}
		}
	}

	public String getToday() {
		//JSON
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO dao = new ChatDAO();
		ArrayList<Chat> chatList = dao.getChatList(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		for (int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getChatName() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			//i가 마지막이 아니면 comma 넣어서 원소가 더 있다는 것 알려줌
			if (i != chatList.size() - 1) {
				result.append(",");
			}
		}
		result.append("], \"last\":\""+chatList.get(chatList.size()-1).getChatId()+"\"}");
		return result.toString();
	}

	//최근 10개 메시지
	public String getTen() {
		//JSON
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO dao = new ChatDAO();
		ArrayList<Chat> chatList = dao.getChatListByRecent(10);
		for (int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getChatName() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			//i가 마지막이 아니면 comma 넣어서 원소가 더 있다는 것 알려줌
			if (i != chatList.size() - 1) {
				result.append(",");
			}
		}
		result.append("], \"last\":\""+chatList.get(chatList.size()-1).getChatId()+"\"}");
		return result.toString();
	}

	public String getId(String chatId) {
		// JSON
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO dao = new ChatDAO();
		ArrayList<Chat> chatList = dao.getChatListByRecent(chatId);
		for (int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getChatName() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			// i가 마지막이 아니면 comma 넣어서 원소가 더 있다는 것 알려줌
			if (i != chatList.size() - 1) {
				result.append(",");
			}
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatId() + "\"}");
		return result.toString();
	}

}
