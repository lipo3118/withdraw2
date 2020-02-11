/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lipo3
 */
public class WithDraw {

    public static void main(String[] args) {

        Account at = new Account();
        Preson p1 = new Preson(at);
        Preson p2 = new Preson(at);
        Despoting d1 = new Despoting(at);
        p1.setName("George");
        p2.setName("Mary");
        
        d1.start();
        p1.start();
        p2.start();
        
    }
}

class Account {

    public int amount = 100;
}

class Preson extends Thread {

    Account acct;

    public Preson(Account a) {
        acct = a;
    }

    public void WithDraw(int i) {
        synchronized (acct) {
           while(true) {
            if (acct.amount >= i) {
                System.out.println("amount=" + acct.amount + "=>" + i + " " + Thread.currentThread().getName());
                acct.amount -= i;
                System.out.println("Balance=" + acct.amount);
                if (acct.amount < 10) {
                    acct.notify();
                }
            } else {
                try {
                    acct.wait();
                } catch (InterruptedException ie) {
                }
            }
        }
    }
    }

    @Override
    public void run() {
        while (acct.amount > 0) {
            WithDraw(20);
        }
    }
}

class Despoting extends Thread {

    Account acct;

    public Despoting(Account a) {
        acct = a;
    }

    public void Despoting(int i) {
        synchronized (acct) {
            while (true) {
                synchronized (acct) {
                    if (acct.amount < 20) {
                        System.out.println("錢不夠了存100");
                        acct.amount = i;
                        acct.notify();
                    } else {
                        try {
                            acct.wait();
                        } catch (InterruptedException ie) {
                        }
                    }
                }
            }
        }

    }

    public void run() {
        Despoting(100);
    }
}
