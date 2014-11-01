package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import hibernate.HiberUserDao;
import hibernate.User;

public class TableTag extends SimpleTagSupport {

	@Override
	public void doTag() throws IOException {
		JspWriter out = getJspContext().getOut();
		List<User> users = new HiberUserDao().findAll();
		int newYear = Calendar.getInstance().getWeekYear();
//		out.print(newYear);
		Iterator<User> iterUser = users.iterator();

		while (iterUser.hasNext()) {
			String role = null;
			int age;
			User user = iterUser.next();
			Calendar userYear = Calendar.getInstance();
			if (user.getBirthday() == null) {
				age = newYear;
			} else {
//				out.print(user.getBirthday());
				userYear.setTime(user.getBirthday());
				age = newYear - userYear.get(Calendar.YEAR);
//				out.print(userYear.get(Calendar.YEAR));
			}
			if (user.getRole().getName().equalsIgnoreCase("Admin")) {
				role = "Admin";
			} else {
				role = "User";
			}

			out.print("<tr><td>" + user.getLogin() + "</td><td>"
					+ user.getFirstName() + "</td><td>" + user.getLastName()
					+ "</td><td>" + age + "</td><td>" + role + "</td>"
					+ "<td><a href=\"controller?action=createdit&editLogin="
					+ user.getLogin() + "\"> Edit </a>" + "&nbsp"
					+ " <a href=\"controller?action=remove&editremove="
					+ user.getLogin() + "\" onclick=\"return confirmation();\" > Delete </a></td></tr>");

		}

	}
}
