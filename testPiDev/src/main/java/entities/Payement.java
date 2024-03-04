package entities;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;

public class Payement {
    public static void main(String[] args) {
// Set your secret key here
        Stripe.apiKey = "sk_test_51OpSALDEN9VkIs736GAAZyNKvqMHMwIfXT8FRwfVo2RJcNNgUWQ2xS4J2HdapuS3QoXgTyUuwX6TtrVWLlcTokMC003VZHybX6";

        try {
// Retrieve your account information
            Account account = Account.retrieve();
            System.out.println("Account ID: " + account.getId());
// Print other account information as needed
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}
