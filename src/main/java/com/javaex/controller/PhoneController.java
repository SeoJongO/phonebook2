package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc")
public class PhoneController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PhoneDao phoneDao = new PhoneDao();
		request.setCharacterEncoding("UTF-8");
		
		//파라미터 action 값을 읽어온다
		String action = request.getParameter("action");
//		System.out.println(action);
		
		if("list".equals(action)) {
			System.out.println("[리스트]");
			
			//리스트
			List<PersonVo> personList = phoneDao.getPersonList();
//			for(int i=0; i<personList.size(); i++) {
//				System.out.println(personList.get(i));
//			}
			
			//데이터 넣기 (어트리뷰트)
			request.setAttribute("pList", personList);
//			request.setAttribute("age", 25);
//			request.setAttribute("name", "황일영");
			
			//html작업 -> jsp에게 시킴(forward)
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/list.jsp");
//			rd.forward(request, response);
			
			WebUtil.forward(request, response, "/WEB-INF/list.jsp");
			
		// 등록폼
		} else if("wForm".equals(action)) {
			System.out.println("[등록폼]");
			
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/writeForm.jsp");
//			rd.forward(request, response);
			
			WebUtil.forward(request, response, "/WEB-INF/writeForm.jsp");
			
		// 등록
		} else if("insert".equals(action) ) {
			System.out.println("[등록]");
			
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			PersonVo personVo = new PersonVo(name, hp, company);
			System.out.println(personVo);
			
			int count = phoneDao.personInsert(personVo);
			
			if(count>0) {
				System.out.println("등록 성공");
			} else {
				System.out.println("등록 실패");
			}
			
//			response.sendRedirect("/phonebook2/pbc?action=list");
			
			WebUtil.redirect(request, response,"/phonebook2/pbc?action=list");
			
		// 수정폼	
		} else if("uForm".equals(action)) {
			System.out.println("[수정폼]");
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			PersonVo getPerson = phoneDao.getPerson(id);
			
			request.setAttribute("gPerson", getPerson);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/updateForm.jsp");
			rd.forward(request, response);
			
			WebUtil.forward(request, response, "/WEB-INF/updateForm.jsp");
			
		// 수정
		} else if("update".equals(action)) {
			System.out.println("[수정]");

			int personID = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			PersonVo personVo = new PersonVo(personID, name, hp, company);
			System.out.println(personVo);
			
			int count = phoneDao.personUpdate(personVo);
			
			if(count>0) {
				System.out.println("수정 성공");
			} else {
				System.out.println("수정 실패");
			}
			
//			response.sendRedirect("/phonebook2/pbc?action=list");
			
			WebUtil.redirect(request, response, "/phonebook2/pbc?action=list");
		
		// 삭제	
		} else if("delete".equals(action)) {
			System.out.println("[삭제]");
			
			int personID = Integer.parseInt(request.getParameter("id"));
		
			int count = phoneDao.personDelete(personID);
			
			if(count>0) {
				System.out.println("삭제 성공");
			} else {
				System.out.println("삭제 실패");
			}
			
//			response.sendRedirect("/phonebook2/pbc?action=list");
			
			WebUtil.redirect(request, response, "/phonebook2/pbc?action=list");
		
		// 재입력
		} else {
			System.out.println("재입력");
			
//			response.sendRedirect("/phonebook2/pbc?action=list");
			
			WebUtil.redirect(request, response, "/phonebook2/pbc?action=list");
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
