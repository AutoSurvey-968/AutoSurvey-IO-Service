
Feature: Sending an Email

  Scenario: Send email with attachment
    Given url 'http://localhost:8083/io'
    And header Content-Type = 'application/json'
    And params { "recipient": "griffin_reynolds@putsbox.com", "subject": "Test Email", "message": "This is just a test", "attachment": "src/test/resources/genos.jpg" }
    When method POST
    Then status 200
    
  Scenario: Send email without attachment
    Given url 'http://localhost:8083/io'
    And header Content-Type = 'application/json'
    And params { "recipient": "griffin_reynolds@putsbox.com", "subject": "Test Email", "message": "This is just a test" }
    When method POST
    Then status 200