Feature: Log into the system
Scenario: Politician is logging in
	Given the politician introduces his email "user1@me.com" and password "user1" into the login form
	Then he logs into the system successfully
	And gets redirected to the dashboard view