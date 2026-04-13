package com.mjsamaha.codelobster.user;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {

	/**
	 * Default
	 * <li>Solve problems</li>
	 * <li>Participate in contests</li>
	 * <li>View profile and statistics</li>
	 */
	USER("ROLE_USER"),

	/**
	 * Moderator
	 * <li>All USER permissions</li>
	 * <li>Review and moderate user-generated content (comments, discussions)</li>
	 * <li>Handle user reports and enforce community guidelines</li>
	 * <li>Limited access to user management (e.g., warn or temporarily suspend
	 * users)</li>
	 */
	MODERATOR("ROLE_MODERATOR"),

	/**
	 * Problem Setter
	 * <li>All USER & MODERATOR permissions</li>
	 * <li>Create and manage problems and problem sets</li>
	 * <li>Access to problem creation tools and analytics</li>
	 * <li>Manage test cases and problem difficulty ratings</li>
	 * <li>Write official editorials for problems</li>
	 * <li>Create learning modules and tutorials</li>
	 */
	PROBLEM_SETTER("ROLE_PROBLEM_SETTER"),

	/**
	 * Contest Manager
	 * <li>All USER, MODERATOR & PROBLEM_SETTER permissions</li>
	 * <li>Create and manage contests and competitions</li>
	 * <li>Access to contest creation tools and analytics</li>
	 * <li>Manage contest schedules and participant lists</li>
	 */
	CONTEST_MANAGER("ROLE_CONTEST_MANAGER"),

	/**
	 * Administrator
	 * <li>All permissions of all other roles</li>
	 * <li>Full access to user management (create, edit, delete users)</li>
	 * <li>Full access to content management (problems, contests, editorials)</
	 * <li>Access to system settings and configurations</li>
	 */
	ADMIN("ROLE_ADMIN"),

	/**
	 * Banned User
	 * <li>No permissions</li>
	 * <li>Used for users who have violated terms of service or community
	 * guidelines</li>
	 */
	BANNED("ROLE_BANNED");

	private final String authority;

	UserRole(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}

	/**
	 * Convert to Spring Security GrantedAuthority
	 */
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(authority));
	}

	/**
	 * Check if this role has at least the privileges of another role
	 */
	public boolean hasPrivilege(UserRole requiredRole) {
		if (this == BANNED)
			return false;
		if (this == ADMIN)
			return true;

		// Define privilege hierarchy
		switch (requiredRole) {
		case USER:
			return this == USER || this == MODERATOR || this == PROBLEM_SETTER || this == CONTEST_MANAGER;
		case MODERATOR:
			return this == MODERATOR || this == PROBLEM_SETTER || this == CONTEST_MANAGER;
		case PROBLEM_SETTER:
			return this == PROBLEM_SETTER;
		case CONTEST_MANAGER:
			return this == CONTEST_MANAGER;
		case ADMIN:
			return this == ADMIN;
		default:
			return false;
		}
	}

}
