package com.facturia.security.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "username", length = 50, nullable = true)
	private String username;
	@JsonIgnore
	@Column(name = "password", length = 64, nullable = true)
	private String password;
	@ManyToOne
	@JoinColumn(name = "rol_id", nullable = false)
	private Roles role;

}
