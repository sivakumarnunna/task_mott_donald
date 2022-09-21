

Feature: Mott donald tests
 


  Scenario: Verify links on Mott donald Homepage
    When I open mott donald homepage "https://www.mottmac.com"
    Then I validate all the links

  
  Scenario Outline: Search the jobTitle
    When I open mott donald job search page "https://www.mottmac.com/careers/search"
    And Enters the search keyword "<keyword>"
    And clicks on search icon
    Then ViewJob button should be displayed
    When I clicked on ViewJob button
    Then Job Description page should be displayed 
    Examples: 
      | keyword|
      | test engineer  |  
      | Lead estimator |
