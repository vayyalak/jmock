package com.objectmentor.jmock;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@SuppressWarnings("deprecation")
@RunWith(JMock.class)
public class WithdrawalTest {

	
	public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(WithdrawalTest.class);
    }
 
    Withdrawal withdrawal;
    Mockery context = new Mockery();
 
    BankAccount account;
 
    @Before
    public void initialize() {
        withdrawal = new Withdrawal();
        withdrawal.setAmount(1500);
        account = context.mock(BankAccount.class);
        withdrawal.setAccount(account);
    }
 
    @Test
    public void failExecutionIfBalanceInadequate() {
        context.checking(new Expectations() {
            {
                one(account).getBalance();
                will(returnValue(1000));
            }
        });
 
        boolean exceptionThrown = false;
 
        try {
            withdrawal.execute();
        } catch (InsufficientFunds e) {
            // success
            exceptionThrown = true;
        }
        assertTrue("Should have thrown: " + InsufficientFunds.class,
                exceptionThrown);
    }
 
    @Test
    public void executeWithdrawChangesBalanceAndSetsExecuted() {
        context.checking(new Expectations() {
            {
                ignoring(account).getBalance();
                will(returnValue(2500));
                one(account).deduct(with(equal(1500)));
            }
        });
 
        withdrawal.execute();
 
        assertEquals(true, withdrawal.isExecuted());
    }
 
    @Test
    public void methodThatWeExpectWillThrowAnException() {
        boolean expectedThrown = false;
 
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            expectedThrown = true;
        }
 
        assertTrue(expectedThrown);
    }
}
