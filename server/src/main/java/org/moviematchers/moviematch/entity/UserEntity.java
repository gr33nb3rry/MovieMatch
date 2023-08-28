package org.moviematchers.moviematch.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "movie_match_user")
public class UserEntity {
	@Id
	@SequenceGenerator(
		name = "user_sequence",
		sequenceName = "user_sequence",
		allocationSize = 1
	)
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "user_sequence"
	)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "user_name")
	private String username;

	@Column(name = "user_password")
	private String password;

	public UserEntity() {
	}

	public UserEntity(Long id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public UserEntity(Long id, String username) {
		this.id = id;
		this.username = username;
	}

	public UserEntity(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
