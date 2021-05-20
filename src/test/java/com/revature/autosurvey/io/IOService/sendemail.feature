@tag
Feature: Send Email

  @tag1
  Scenario: Send Email
    Given path 'http://localhost:8080/io'
    And header Content-Type = 'application/json'
    And request { "recipient": "rjschaber@yahoo.com", "subject": "Test Email", "message": "This is just a test", "attachment": "C:\Users\Doubtless\Pictures\something.jpg" }
    When method POST
    Then status 200
    And match $ == { recipient: "rjschaber@yahoo.com", subject: "Test Email", message: "This is just a test", attachment: "C:\Users\Doubtless\Pictures\something.jpg" }