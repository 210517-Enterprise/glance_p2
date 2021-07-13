package com.revature.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * User Spring bean object that holds quantities we want to know
 * about a user, expected to include
 * 		- firstName: String
 * 		- lastName: String
 * 		- profileID: int GENERATED
 * 		- pf: ProfileFacets (Like goals, we mat just do a list of Goasl)
 * 		- email: string
 * 		- password: string
 * 		- accounts: List<Account>
 * 		- phone: string
 * 		- plaid_key: string
 * 
 * 		-Class should be annotated to allow saving to db by DAOImpl
 * 	
 * 
 * 		Important methods
 * 		stringAsJSON : String - returns string of all data as JSON to be interpreted by front end
 * 
 * 		@author Kyle Castillo
 * 
 */

@Data @NoArgsConstructor 
@Entity @Table(name="users")
public class User {
	
	@Id @Column(name="user_idgi")
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
	private @Getter @Setter String cellphone; //FIXME should this be unique?
	
	@NotEmpty
	@Column(nullable=false, name="main_address")
	private @Getter @Setter String mainAddress;
	
	@Column(name="alt_address")
	private @Getter @Setter String altAddress;
	
	@Column(name="plaid_token", unique=true)
	private @Getter @Setter String plaidToken; //FIXME, check to see if this should be nullable or not
	
	@Column(name="plaid_item", unique=true)
	private @Getter @Setter String plaidItem; //FIXME, check to see if this should be nullable or not.
	
	@Column(name="creation_date")
	private Date creationDate;
	
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
			@NotEmpty String cellphone, @NotEmpty String mainAddress, String altAddress, String plaidToken,
			String plaidItem) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.cellphone = cellphone;
		this.mainAddress = mainAddress;
		this.altAddress = altAddress;
		this.plaidToken = plaidToken;
		this.plaidItem = plaidItem;
		this.creationDate = new Date();
	}
	
	/**
	 * Constructor used to instantiate all fields not related to the plaid API.
	 * @param email the email the user signs up with, this must be unique.
	 * @param firstName the first name of the user.
	 * @param lastName the last name of the user.
	 * @param cellphone the cellphone number of the user.
	 * @param mainAddress the primary address of the user.
	 * @param altAddress an alternate address of the user.
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
