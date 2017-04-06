Feature: Log into the system
Scenario: Politician is logging in
	Given the politician is in the login page
	And introduces his credentials, "user1@me.com" and "user1" into the login form
	When he pushes the "Log in" button
	Then he gets redirected to the dashboard view
