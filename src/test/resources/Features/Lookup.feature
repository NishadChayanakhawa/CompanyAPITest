Feature: Look up company based on domain name.
	Background:
		Given API Base is "https://company.clearbit.com/v2"
		And apiKey provided is "sk_1e7aa20d9a2e9962b9374949c59a9fab"
		
	@RegressionTest
	Scenario Outline: Verify company lookup based on domain
		Given "domain" parameter is set to "<Company Domain>"
		When API is called
		Then Body "name" should be "<Company Name>"
		And Body "legalName" should be "<Company Legal Name>"
		Examples:
		|Company Domain									|Company Name							|Company Legal Name				|
		|facebook.com										|Facebook									|Facebook Inc.						|
		|amazon.com											|Amazon										|Amazon.com, Inc.					|
		
	@RegressionTest
	Scenario Outline: Verify company lookup based on domain and company name
		Given "domain" parameter is set to "<Company Domain>"
		And "company_name" parameter is set to "<Company Name PARM>"
		When API is called
		Then Body "name" should be "<Company Name>"
		And Body "legalName" should be "<Company Legal Name>"
		Examples:
		|Company Domain									|Company Name PARM				|Company Name							|Company Legal Name						|
		|samsung.com										|Samsung									|Samsung Electronics			|Samsung Electronics Co. Ltd.	|
		|nokia.com											|nokia										|Nokia										|Nokia Corporation						|