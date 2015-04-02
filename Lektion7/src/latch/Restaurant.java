package latch;

import java.util.concurrent.CountDownLatch;

public class Restaurant {
	
	public static void main(String[] args) {
		int nrGuests = 2;

		CountDownLatch l1 = new CountDownLatch(1);
		CountDownLatch l2 = new CountDownLatch(nrGuests);
				
		new Cook(l1).start();
		
		for(int i = 0; i < nrGuests; i++) {
			new Guest(l1, l2).start();
		}
		
		new DishWasher(l2).start();
	}
	
	
	static class Cook extends Thread {

		CountDownLatch l1;

		public Cook(CountDownLatch l1) {
			this.l1 = l1;
		}
		
		@Override
		public void run() {
			System.out.println("Start Cooking..");
			try {
				sleep(5000);
			} catch (InterruptedException e) {}
			System.out.println("Meal is ready");
			l1.countDown();
		}
	}
	
	
	static class Guest extends Thread {

		CountDownLatch l1;
		CountDownLatch l2;

		public Guest(CountDownLatch l1, CountDownLatch l2) {
			this.l1 = l1;
			this.l2 = l2;
		}
		
		@Override
		public void run() {
			try {
				sleep(1000);
				System.out.println("Entering restaurant and placing order.");
				l1.await();
				System.out.println("Enjoying meal.");
				sleep(5000);
				System.out.println("Meal was excellent!");
				l2.countDown();
			} catch (InterruptedException e) {}
		}
	}
	
	
	static class DishWasher extends Thread {

		CountDownLatch l2;

		public DishWasher(CountDownLatch l2) {
			this.l2 = l2;
		}
		
		@Override
		public void run() {
			try {
				System.out.println("Waiting for dirty dishes.");
				l2.await();
				System.out.println("Washing dishes.");
				sleep(0);
			} catch (InterruptedException e) {}
		}
	}
}
