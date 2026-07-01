Feature: Sauce Labs Cart Validation

        @SauceLabsCart @SL1
        Scenario Outline: Verify login and add product to cart on iOS and Android
            Given the user launches Sauce Labs app on "<platform>"
             When the user logs in to Sauce Labs app with "<username>" and "<password>"
              And the user adds "<productName>" product to the cart
             Then the user should see "<productName>" in the cart
              And the user taps on checkout
              And the user adds checkout details "<firstName>", "<lastName>", and "<pin>"
              And the user taps continue
              And the user taps finish
             Then the user should see order confirmation message "<orderMessage>"

        @Android
        Examples:
                  | platform | username      | password     | productName         | firstName | lastName | pin   | orderMessage           |
                  | android  | standard_user | secret_sauce | Sauce Labs Backpack | John      | Doe      | 56001 | Thnaks for your order |

        @iOS
        Examples:
                  | platform | username      | password     | productName         | firstName | lastName | pin   | orderMessage           |
                  | ios      | standard_user | secret_sauce | Sauce Labs Backpack | John      | Doe      | 56001 | Thnaks for your order |
