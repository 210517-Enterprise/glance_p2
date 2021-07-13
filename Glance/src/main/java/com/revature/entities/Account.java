package com.revature.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
 * Accounts entity for the glance application. The account entity should be used
 * when adding an account via plaid which provides an item and access key to that
 * account.<br>
 * 
 * In addition to that a user id will be set as a foreign key to refer to the user
 * that owns the particular account.
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
	@ManyToOne(targetEntity = User.class) @JoinColumn(nullable=false,name="user_id")
	private @Getter @Setter int userId;
	
	@OneToOne(targetEntity = Goal.class) @JoinColumn(name="goal_id")
	private @Getter @Setter Goal goal;
	
	@NotNull
	@Column(nullable=false, name="plaid_item")
	private @Getter @Setter String plaidItem;
	
	@NotNull
	@Column(nullable=false, name="plaid_key")
	private @Getter @Setter String plaidKey;
	
	@Column
	private @Getter Date creationDate;
	
	/**
	 * Constructor for the account entity.
	 * @param plaidItem the plaid item provided when adding an account from plaid
	 * @param plaidKey
	 */
	public Account(@NotNull String plaidItem, @NotNull String plaidKey, @NotNull int userId) {
		super();
		this.plaidItem = plaidItem;
		this.plaidKey = plaidKey;
		this.userId = userId;
		this.creationDate = new Date();
	}
}
