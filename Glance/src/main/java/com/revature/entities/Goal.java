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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a goal within the Glance application holding the necessary information to display goal information to a user on demand.
 * 
 * This entity is required for next version new functionality, but should not be used within the current release.
 * 
 * The the user_id provides foreign key functionality to link a goal to a user.
 * The account_id provides foreign key functionality to link a goal to a specific account.
 * @author Kyle Castillo
 *
 */
@Data @NoArgsConstructor
@Entity @Table(name="goals")
public class Goal {
	
	@Id @Column(name="goal_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty
	@Column(nullable=false, name="goal_name")
	private @Getter @Setter String goalName;
	
	@NotEmpty
	@Column(nullable=false, name="goal_amount")
	private @Getter @Setter int goalAmount;
	
	@NotNull
	@ManyToOne(targetEntity = User.class) @JoinColumn(name="user_id")
	private @Getter @Setter int userId;

	
	@NotNull
	@OneToOne @JoinColumn(name="account_id")
	private @Getter @Setter Account account;
	
	@Column(name="start_date")
	private @Getter Date startDate;
	
	@Column(name="finish_date")
	private @Getter @Setter Date finishDate;

	/**
	 * Primary constructor for goal object.
	 * @param goalName the name of the goal.
	 * @param goalAmount the amount the goal needs to achieve.
	 * @param userId a foreign key reference to the owner.
	 * @param accountId a reference to the account id associated with this goal.
	 */
	public Goal(@NotEmpty String goalName, @NotEmpty int goalAmount, @NotNull int userId, @NotNull Account account) {
		super();
		this.goalName = goalName;
		this.goalAmount = goalAmount;
		this.userId = userId;
		this.account = account;
	}
}
