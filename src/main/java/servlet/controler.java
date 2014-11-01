package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hibernate.HiberRoleDao;
import hibernate.HiberUserDao;
import hibernate.Role;
import hibernate.User;

/**
 * Servlet implementation class contoler
 */
public class controler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MESS_ERROR_LOGIN = "Login or password is wrong";
	private static final String MESS_ERROR_EXISTS = "This user already exists";
	private static final String MESS_ERROR_EMPTY = "The form has a blank line";
	private static final String MESS_ERROR_TRY = "Please try again!!!";
	private static final String MESS_ERROR_PASSWORD = "Passwords do not match";
	private static final String MESS_ERROR_DATE = "Wrong date format!!!";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public controler() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		commands(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		PrintWriter out = response.getWriter();
//		out.println("sdfsdf");
		commands(request, response);
	}

	private void commands(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String comm = request.getParameter("action");
		if (comm == null) {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/index");
			dispatcher.forward(request, response);
		} else {

			if (comm.equalsIgnoreCase("login")) {
				login(request, response);
			} else {

			}

			if (comm.equalsIgnoreCase("logout")) {
				logout(request, response);
			}

			if (comm.equalsIgnoreCase("add")) {
				add(request, response);
			}

			if (comm.equalsIgnoreCase("createdit")) {
				createdit(request, response);
			}

			if (comm.equalsIgnoreCase("edit")) {
				edit(request, response);
			}
			if (comm.equalsIgnoreCase("remove")) {
				remove(request, response);
			}
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String login = request.getParameter("login");
		String password = request.getParameter("pass");

		User user = new User();
		HiberUserDao jdbcUser = new HiberUserDao();

		user.setLogin(login);
		user.setPassword(password);

		User userfindByLogin = jdbcUser.findByLogin(login);
//		PrintWriter out = response.getWriter();
		
//		 out.println(userfindByLogin.getRole().getId());
//		 out.println(userfindByLogin);
//		out.println("sdfsdf");
		if (userfindByLogin == null
				|| !userfindByLogin.getPassword().equalsIgnoreCase(password)) {
			request.setAttribute("message", MESS_ERROR_LOGIN);
			request.setAttribute("message2", MESS_ERROR_TRY);
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/index");
			dispatcher.forward(request, response);
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("current", userfindByLogin);
			if (userfindByLogin.getRole().getName().equalsIgnoreCase("Admin")) {
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/admin");
				dispatcher.forward(request, response);
			} else {
				request.setAttribute("current", userfindByLogin.getFirstName());
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/user");
				dispatcher.forward(request, response);
			}

		}

	}

	private void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("current");
		request.getSession().removeAttribute("current");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index");
		dispatcher.forward(request, response);
	}

	private void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("Cancel") != null) {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/admin");
			dispatcher.forward(request, response);
		} else {
			User user = new User();
			if (request.getParameter("Ok") != null) {
				if (validator(request, response)) {

					String login = request.getParameter("login");
					String pass = request.getParameter("pass");
					String email = request.getParameter("email");
					String first = request.getParameter("first");
					String last = request.getParameter("last");
					String birth = request.getParameter("birth");
					String role = request.getParameter("role");

					Date date = null;
					SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");

					try {
						date = format.parse(birth);
						user.setBirthday(new java.sql.Date(date.getTime()));
					} catch (ParseException e) {
						request.setAttribute("wrong", MESS_ERROR_DATE);
						RequestDispatcher dispatcher = request
								.getRequestDispatcher("/add");
						dispatcher.forward(request, response);
					}
					Role roleDaoAdmin = new HiberRoleDao().findByName("Admin");
					Role roleDaoUser = new HiberRoleDao().findByName("User");
					if (role.equalsIgnoreCase("Admin")) {
						user.setRole(roleDaoAdmin);
					} else {
						user.setRole(roleDaoUser);
					}

					user.setLogin(login);
					user.setPassword(pass);
					user.setEmail(email);
					user.setFirstName(first);
					user.setLastName(last);

					new HiberUserDao().create(user);
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("/admin");
					dispatcher.forward(request, response);
				}
			}
		}

	}

	private void createdit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User user = new HiberUserDao().findByLogin(request
				.getParameter("editLogin"));
		HttpSession session = request.getSession(true);
		session.setAttribute("editLogin", user.getLogin());
		session.setAttribute("editPass", user.getPassword());
		session.setAttribute("editEmail", user.getEmail());
		session.setAttribute("editFname", user.getFirstName());
		session.setAttribute("editLname", user.getLastName());
		session.setAttribute("editEmail", user.getEmail());
		session.setAttribute("editBirthday", user.getBirthday().toString());

		if (user.getRole().getId() == 1L) {
			session.setAttribute("currentRole", "Admin");
			session.setAttribute("secondRole", "User");
		} else {
			session.setAttribute("currentRole", "User");
			session.setAttribute("secondRole", "Admin");
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/edit");
		dispatcher.forward(request, response);
	}

	private void edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("Cancel") != null) {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/admin");
			dispatcher.forward(request, response);
		}

		String userLogin = request.getParameter("login");
		User user = new HiberUserDao().findByLogin(userLogin);

		if (request.getParameter("Ok") != null) {
			if (validator(request, response)) {

				String pass = request.getParameter("pass");
				String email = request.getParameter("email");
				String first = request.getParameter("first");
				String last = request.getParameter("last");
				String birth = request.getParameter("birth");
				String role = request.getParameter("role");

				Date date = null;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");

				try {
					date = format.parse(birth);
					user.setBirthday(new java.sql.Date(date.getTime()));
				} catch (ParseException e) {
					request.setAttribute("wrong", MESS_ERROR_DATE);
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("/edit");
					dispatcher.forward(request, response);
				}
				Role roleDaoAdmin = new HiberRoleDao().findByName("Admin");
				Role roleDaoUser = new HiberRoleDao().findByName("User");
				if (role.equalsIgnoreCase("Admin")) {
					user.setRole(roleDaoAdmin);
				} else {
					user.setRole(roleDaoUser);
				}

				user.setPassword(pass);
				user.setEmail(email);
				user.setFirstName(first);
				user.setLastName(last);
				new HiberUserDao().update(user);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/admin");
				dispatcher.forward(request, response);
			}
		}

	}

	private void remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String editremove = request.getParameter("editremove");
		User user = new HiberUserDao().findByLogin(editremove);
		new HiberUserDao().remove(user);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin");
		dispatcher.forward(request, response);
	}

	private boolean validator(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String pass = request.getParameter("pass");
		String passAgain = request.getParameter("passAgain");
		String birth = request.getParameter("birth");

		// HttpSession session = request.getSession();

		request.setAttribute("returnLogin", request.getParameter("login"));
		request.setAttribute("returnPass", request.getParameter("pass"));
		request.setAttribute("returnEmail", request.getParameter("email"));
		request.setAttribute("returnFirst", request.getParameter("first"));
		request.setAttribute("returnLast", request.getParameter("last"));
		
		HiberUserDao jdbcUser = new HiberUserDao();
		User userfindByLogin = jdbcUser.findByLogin(request.getParameter("login"));
		
		
		if (request.getParameter("login").equals("")
				|| request.getParameter("pass").equals("")
				|| request.getParameter("email").equals("")
				|| request.getParameter("first").equals("")
				|| request.getParameter("last").equals("")&&request.getParameter("action").equalsIgnoreCase("add")) {
			request.setAttribute("wrong", MESS_ERROR_EMPTY );
			RequestDispatcher dispatcher = request.getRequestDispatcher("/add");
			dispatcher.forward(request, response);
			return false;

		}
		
		if (request.getParameter("login").equals("")
				|| request.getParameter("pass").equals("")
				|| request.getParameter("email").equals("")
				|| request.getParameter("first").equals("")
				|| request.getParameter("last").equals("")&&request.getParameter("action").equalsIgnoreCase("edit")) {
			request.setAttribute("wrong", MESS_ERROR_EMPTY );
			RequestDispatcher dispatcher = request.getRequestDispatcher("/edit");
			dispatcher.forward(request, response);
			return false;

		}

		if(userfindByLogin != null&&request.getParameter("action").equalsIgnoreCase("add")){
			request.setAttribute("returnLogin", "");
			request.setAttribute("wrong", MESS_ERROR_EXISTS);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/add");
			dispatcher.forward(request, response);
			return false;			
		}

		if (!pass.equals(passAgain)) {
			if(request.getParameter("action").equalsIgnoreCase("add")){
				request.setAttribute("returnPass", "");
				request.setAttribute("wrong", MESS_ERROR_PASSWORD);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/add");
				dispatcher.forward(request, response);
				return false;
			}else{
				request.setAttribute("returnPass", "");
				request.setAttribute("wrong", MESS_ERROR_PASSWORD);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/edit");
				dispatcher.forward(request, response);
				return false;
			}

		}

		try {
			if (birth.equalsIgnoreCase("yyyy-mm-dd")
					|| Integer.valueOf(birth.split("-")[1]) < 1
					|| Integer.valueOf(birth.split("-")[1]) > 12
					|| Integer.valueOf(birth.split("-")[2]) < 1
					|| Integer.valueOf(birth.split("-")[2]) > 31
					|| Integer.valueOf(birth.split("-")[0]) > Calendar
							.getInstance().getWeekYear()) {
				throw new NullPointerException();
			}
		} catch (Exception e) {

			if (request.getParameter("action").equalsIgnoreCase("add")) {
				request.setAttribute("wrong", MESS_ERROR_DATE);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/add");
				dispatcher.forward(request, response);
				return false;
			}

			if (request.getParameter("action").equalsIgnoreCase("edit")) {
				request.setAttribute("wrong", MESS_ERROR_DATE);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/edit");
				dispatcher.forward(request, response);
				return false;
			}
		}

		return true;
	}
}
