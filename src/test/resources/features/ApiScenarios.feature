Feature: Validation of the API scenarios on PurgoMalum profanity service

  Scenario Outline: Verify the text is filtered by the service
    Given profanity service is available
    Then asterik is displayed as the default fill when fill text is not added in the <requestType> parameter
    Examples:
      | requestType |
      | xml         |
      | json        |
      | plaintext   |


  Scenario Outline: Validate that profanity text is replaced with fill text
    Given profanity service is available
    Then profanity word is replaced by the <fill text> when input as parameter in the <requestType>
    Examples:
      | requestType | fill text   |
      | xml         | Vishal      |
      | json        | Vishal      |
      | plaintext   | Vishal      |
#      | xml         | 121212      |
#      | json        | 121211      |
#      | plaintext   | 1112l       |
#      | xml         | V_s1hal     |
#      | json        | V_s1hal     |
#      | plaintext   | V_s1hal     |
#      | xml         | V~shal      |
#      | json        | V!shal      |
#      | plaintext   | V--shal     |
#      | xml         | "Vish"      |
#      | json        | 'Mishima'   |
#      | plaintext   | 'Nina'      |
#      | xml         | =!ABC12     |
#      | json        | =!ABC_12_   |
#      | plaintext   | {Nina_12}   |
#      | xml         | (=!ABC12)   |
#      | json        | [=!ABC_12]_ |
#      | plaintext   | {Nina_12}   |


  Scenario Outline: Validate the service successful response if text has profanity words
    Given profanity service is available
    Then profanity service returns success when input text has profanity word in the <requestType>
    Examples:
      | requestType |
      | xml         |
      | json        |
      | plaintext   |

  Scenario Outline: Validate that service returns false when input text does not contain profanity words
    Given profanity service is available
    Then profanity service returns false when input <text> does not have profanity word in the <requestType>
    Examples:
      | requestType | text               |
      | xml         | This is test Input |
      | json        | This is test Input |
      | plaintext   | This is test Input |


  Scenario Outline: Validate that service returns error when no input is provided
    Given profanity service is available
    Then error message is returned in the response when required parameter is not provided in <requestType>
    Examples:
      | requestType |
      | xml         |
      | json        |
      | plaintext   |


  Scenario Outline: Validation of error handling on the replacement text
    Given profanity service is available
    Then error message is returned when replacement text exceeds 20 characters in <requestType>
    Examples:
      | requestType |
      | xml         |
      | json        |
      | plaintext   |

  Scenario Outline: Validation of allowed characters on replacement text
    Given profanity service is available
    Then profanityword is replaced by the <character> in the <requestType>
    Examples:
      | requestType | character |
      | xml         | _         |
      | json        | _         |
      | plaintext   | _         |
#      | xml         | ~         |
#      | json        | ~         |
#      | plaintext   | ~         |
#      | xml         | pipe      |
#      | json        | pipe      |
#      | plaintext   | pipe      |
#      | xml         | =         |
#      | json        | =         |
#      | plaintext   | =         |
#      | xml         | -         |
#      | json        | -         |
#      | plaintext   | -         |


  Scenario Outline: Validation of error message when invalid character is used
    Given profanity service is available
    Then error message is provided in the response when invalid fill character is used to replace <text>
    Examples:
    |text|
    |This is an arse|
    |bastard is input|
















