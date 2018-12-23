package org.prj.chat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class ChatDAO {
	private Connection conn;

	public ChatDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/anonymouschat?serverTimezone=Asia/Seoul&amp";
			String dbID = "root";
			String dbPW = "1111";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPW);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 현재 시각의 모든 메시지 가져오기
	public ArrayList<Chat> getChatList(String nowTime) {
		ArrayList<Chat> chatList = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM chat WHERE chatTime > ? ORDER BY chatTime ASC";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, nowTime);
			rs = pstm.executeQuery();
			chatList = new ArrayList<>();
			while (rs.next()) {
				Chat chat = new Chat();
				chat.setChatId(rs.getInt("chatId"));
				chat.setChatName(rs.getString("chatName"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;")
						.replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if (Integer.parseInt(rs.getString("chatTime").substring(11, 13)) >= 12) {
					timeType = "오후";
					chatTime -= 12;
				}

				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":"
						+ rs.getString("chatTime").substring(14, 16) + "");
				chatList.add(chat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return chatList;
	}

	public ArrayList<Chat> getChatListByRecent(String chatId) {
		ArrayList<Chat> chatList = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM chat WHERE chatId > ? ORDER BY chatTime ASC";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, Integer.parseInt(chatId));
			rs = pstm.executeQuery();
			chatList = new ArrayList<>();
			while (rs.next()) {
				Chat chat = new Chat();
				chat.setChatId(rs.getInt("chatId"));
				chat.setChatName(rs.getString("chatName"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;")
						.replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if (Integer.parseInt(rs.getString("chatTime").substring(11, 13)) >= 12) {
					timeType = "오후";
					chatTime -= 12;
				}

				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":"
						+ rs.getString("chatTime").substring(14, 16) + "");
				chatList.add(chat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return chatList;
	}

	// 사용자가 메시지 입력해서 제출
	public int submit(String chatName, String chatContent) {
		PreparedStatement pstm = null;
		// auto_increment 컬럼에 null값 넣어준다
		String sql = "INSERT INTO chat VALUES(NULL,?,?,now())";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, chatName);
			pstm.setString(2, chatContent);
			return pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)
					pstm.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 오류가 나면 1 반환
		return -1;
	}

	// 현재 시각의 모든 메시지 가져오기
	public ArrayList<Chat> getChatListByRecent(int number) {
		ArrayList<Chat> chatList = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM chat WHERE chatId > (SELECT MAX(chatId) - ? FROM CHAT) ORDER BY chatTime ASC";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, number);
			rs = pstm.executeQuery();
			chatList = new ArrayList<>();
			while (rs.next()) {
				Chat chat = new Chat();
				chat.setChatId(rs.getInt("chatId"));
				chat.setChatName(rs.getString("chatName"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;")
						.replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if (Integer.parseInt(rs.getString("chatTime").substring(11, 13)) >= 12) {
					timeType = "오후";
					chatTime -= 12;
				}

				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":"
						+ rs.getString("chatTime").substring(14, 16) + "");
				chatList.add(chat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return chatList;
	}
}
