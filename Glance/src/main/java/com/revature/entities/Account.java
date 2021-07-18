package com.revature.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Represents an account within the application holding information from the Plaid API necessary to retrieve account information on an as needed basis within the application. <br/>
 * 
 * The user provides foreign key functionality to link users to their accounts.
 * The goal provides foreign key functionality to accounts to a goal for future functionality improvements not yet implemented.
 * The plaid_key provides the plaid based account id that is used for retrieval of further information from the Plaid API.
 * The plaid_item is the unique identifier of the item in the Plaid API the account is associated with. This value is necessary for support requests from Plaid.
 * @author Kyle Castillo
 *
 */
@Data @NoArgsConstructor
@Entity @Table(name="accounts")
public class Account {

	@Id @Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY) 
	@JoinColumn(nullable=false,name="user_id")
	private @Getter @Setter User user;
	
	@OneToOne(targetEntity = Goal.class, fetch = FetchType.LAZY)
	@JoinColumn(name="goal_id")
	private @Getter @Setter Goal goal;
	
	@NotNull
	@Column(nullable=false, name="plaid_item")
	private @Getter @Setter String plaidItem;
	
	@NotNull
	@Column(nullable=false, name="plaid_key")
	private @Getter @Setter String plaidKey;
	
	@Column(name="creation_date")
	private @Getter Date creationDate;
	
	
	/**
	 * Parameterized constructor for an account object that sets necessary values for an account object. 
	 * @param plaidItem : unique identifier of the item in the Plaid API the account is associated with. This value is necessary for support requests from Plaid
	 * @param plaidKey : the plaid based account id that is used for retrieval of further information from the Plaid API.
	 * @param user : the user the account is associated with.
	 */
	public Account(@NotNull String plaidItem, @NotNull String plaidKey, @NotNull User user) {
		super();
		this.plaidItem = plaidItem;
		this.plaidKey = plaidKey;
		this.user = user;
		this.creationDate = new Date();
	}
}
