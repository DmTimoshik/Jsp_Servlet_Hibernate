package hibernate;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "USER")
public class User {

	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="ID")
	private Long id;

	@Column(name = "LOGIN")
	private String login;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "FIRSTNAME")
	private String firstName;
	
	@Column(name = "LASTNAME")
	private String lastName;
	
	@Column(name = "BIRTHDAY")
	private java.sql.Date birthday;
	

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH} )
	@JoinColumn(name="ROLEID")
	private Role role;
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public java.sql.Date getBirthday() {
		return birthday;
	}

	public void setBirthday(java.sql.Date birthday) {
		this.birthday = birthday;
	}

//	public long getRole() {
//		return role;
//	}
//
//	public void setRole(long role) {
//		this.role = role;
//	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password
				+ ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", birthday=" + birthday + "]";
	}

}
