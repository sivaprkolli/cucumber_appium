Feature: Sauce Labs Cart Validation

        @SauceLabsCart @SL1
        Scenario Outline: Verify login and add product to cart on iOS and Android
            Given the user launches Sauce Labs app on "<platform>"
             When the user logs in to Sauce Labs app using credentials key "<credentialsKey>"
              And the user adds "<productName>" product to the cart
             Then the user should see "<productName>" in the cart
              And the user taps on checkout
              And the user adds checkout details using key "<checkoutKey>"
              And the user taps continue
              And the user taps finish
             Then the user should see order confirmation message for checkout key "<checkoutKey>"

        @Android
        Examples:
                  | platform | credentialsKey | productName         | checkoutKey      |
                  | android  | default_login  | Sauce Labs Backpack | default_checkout |

        @iOS
        Examples:
                  | platform | credentialsKey | productName         | checkoutKey      |
                  | ios      | default_login  | Sauce Labs Backpack | default_checkout |
