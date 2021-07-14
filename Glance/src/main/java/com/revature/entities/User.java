package com.revature.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * User Spring Entity object that contains the following information regarding a user:<br>
 * <ul>
 * <li>The first name of the user</li>
 * <li>The last name of the user</li>
 * <li>An email for the user</li>
 * <li>A password, which will be hashed, for the user</li>
 * <li>A cellphone for the user</li>
 * <li>A main address for the user</li>
 * <li>An optional alternate address</li>
 * <li>Accounts associated with the user, this is purely used as a one to many relationship for accounts the user owns</li>
 * <li>A timestamp of when the user was created</li>
 * </ul>
 * <br>
 * The User entity bean will be annotated to allow an implementation done by a UserRepository and UserService class.
 * @author Kyle Castillo
 *
 */
@Data @NoArgsConstructor 
@Entity @Table(name="users")
public class User {
	
	@Id @Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty
	@Column(nullable=false, unique=true)
	private String email;
	
	@NotEmpty
	@Column(nullable=false)
	private @Getter @Setter String password;
	
	@NotEmpty
	@Column(nullable=false, name="first_name")
	private @Getter @Setter String firstName;
	
	@NotEmpty
	@Column(nullable=false, name="last_name")
	private @Getter @Setter String lastName;
	
	@NotEmpty
	@Column(nullable=false)
	private @Getter @Setter String cellphone;
	
	@NotEmpty
	@Column(nullable=false, name="main_address")
	private @Getter @Setter String mainAddress;
	
	@Column(name="alt_address")
	private @Getter @Setter String altAddress;
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private @Getter List<Account> accounts;
	
	
	@Column(name="creation_date")
	private @Getter Date creationDate;
	
	/**
	 * Primary constructor for the User entity.<br>
	 * This includes all necessary fields needed to create a user.
	 * @param email the email the user signs up with, this must be unique.
	 * @param password the password the user uses to log in.
	 * @param firstName the first name of the user.
	 * @param lastName the last name of the user.
	 * @param cellphone the cellphone number of the user.
	 * @param mainAddress the primary addresss of the user
	 */
	public User(@NotEmpty String email, @NotEmpty String password, @NotEmpty String firstName, @NotEmpty String lastName,
			@NotEmpty String cellphone, @NotEmpty String mainAddress) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.cellphone = cellphone;
		this.mainAddress = mainAddress;
		this.creationDate = new Date(); //FIXME, this might need a better implementation
	}
	
	/**
	 * Constructor used to instantiate all fields that are not auto generated.
	 * @param email the email the user signs up with, this must be unique.
	 * @param firstName the first name of the user.
	 * @param lastName the last name of the user.
	 * @param cellphone the cellphone number of the user.
	 * @param mainAddress the primary address of the user.
	 * @param altAddress an alternate address of the user.
	 * @param plaidToken the plaid token associated with the user.
	 * @param plaidItem the plaid item associated with the user.
	 */
	public User(@NotEmpty String email, @NotEmpty String password, @NotEmpty String firstName, @NotEmpty String lastName,
			@NotEmpty String cellphone, @NotEmpty String mainAddress, String altAddress) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.cellphone = cellphone;
		this.mainAddress = mainAddress;
		this.altAddress = altAddress;
		this.creationDate = new Date();
	}	
}
