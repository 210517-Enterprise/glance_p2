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
import lombok.NoArgsConstructor;

/**
 * Users entity for the glance application. 
 * @author Kyle Castillo
 *
 */
@Data @NoArgsConstructor
@Entity @Table(name="users")
public class User {
	
	@Id @Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty
	@Column(nullable=false, unique=true)
	private String email;
	
	@NotEmpty
	@Column(nullable=false, name="first_name")
	private String firstName;
	
	@NotEmpty
	@Column(nullable=false, name="last_name")
	private String lastName;
	
	@NotEmpty
	@Column(nullable=false)
	private String cellphone; //FIXME should this be unique?
	
	@NotEmpty
	@Column(nullable=false, name="main_address")
	private String mainAddress;
	
	@Column(name="alt_address")
	private String altAddress;
	
	@Column(name="plaid_token", unique=true)
	private String plaidToken; //FIXME, check to see if this should be nullable or not
	
	@Column(name="plaid_item", unique=true)
	private String plaidItem; //FIXME, check to see if this should be nullable or not.
	
	@Column(name="creation_date")
	private Date creationDate;
	
	/**
	 * Primary constructor for the User entity.<br>
	 * This includes all necessary fields needed to create a user.
	 * @param email the email the user signs up with, this must be unique.
	 * @param firstName the first name of the user.
	 * @param lastName the last name of the user.
	 * @param cellphone the cellphone number of the user.
	 * @param mainAddress the primary addresss of the user
	 */
	public User(@NotEmpty String email, @NotEmpty String firstName, @NotEmpty String lastName,
			@NotEmpty String cellphone, @NotEmpty String mainAddress) {
		super();
		this.email = email;
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
	public User(@NotEmpty String email, @NotEmpty String firstName, @NotEmpty String lastName,
			@NotEmpty String cellphone, @NotEmpty String mainAddress, String altAddress, String plaidToken,
			String plaidItem) {
		super();
		this.email = email;
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
	public User(@NotEmpty String email, @NotEmpty String firstName, @NotEmpty String lastName,
			@NotEmpty String cellphone, @NotEmpty String mainAddress, String altAddress) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.cellphone = cellphone;
		this.mainAddress = mainAddress;
		this.altAddress = altAddress;
		this.creationDate = new Date();
	}
	
	
}
