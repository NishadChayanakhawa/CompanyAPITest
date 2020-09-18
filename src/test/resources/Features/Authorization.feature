Feature: Validate authorizations
	Background:
		Given API Base is "https://company.clearbit.com/v2"
		
	@RegressionTest
  Scenario Outline: Authorization validations
    Given "domain" parameter is set to "facebook.com"
    And apiKey provided is "<API Key>"
    When API is called
    Then Header Status should be <Status Code>
    And Response JSON matches schema "<Error JSON Schema>"
    And Body "error.type" should be "invalid_api_key"
    And Body "error.message" should be "Invalid API key provided"
    
    Examples:
    |API Key														|Status Code|Error JSON Schema					|
    |Invalid														|401				|ErrorJsonSchema						|
    |sk_1e7aa20d9a2e9962b9374949c59a9fac|401				|ErrorJsonSchema						|
    |sk_1e7aa20d9a2e9962b9374949c59a9fad|401				|ErrorJsonSchema						|
    |sk_1e7aa20d9a2e9962b9374949c59a9fae|401				|ErrorJsonSchema						|
    
    @SmokeTest @RegressionTest
    Scenario: No Authorization provided
    	Given "domain" parameter is set to "facebook.com"
	    And apiKey provided is ""
	    When API is called
	    Then Header Status should be 401
	    And Response JSON matches schema "ErrorJsonSchema"
	    And Body "error.type" should be "auth_required"
	    And Body "error.message" should be "Authentication is required for this action. Sign up at https://clearbit.com"
	  
	  @SmokeTest @RegressionTest
	  Scenario Outline: Valid authorization provided
    	Given "domain" parameter is set to "facebook.com"
	    And apiKey provided is "<Valid API Key>"
	    When API is called
	    Then Header Status should be 200
	    
	    Examples:
	    |Valid API Key											|
	    |sk_1e7aa20d9a2e9962b9374949c59a9fab|
	    |sk_1e7aa20d9a2e9962b9374949c59a9fab|
	    |sk_1e7aa20d9a2e9962b9374949c59a9fab|