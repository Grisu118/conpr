package blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class OrderProcessing {
	
	public static void main(String[] args) {
		int nCustomers = 10;
		int nValidators = 2;
		int nProcessors = 3;
		BlockingQueue<Order> queue1 = new ArrayBlockingQueue<>(10);
		BlockingQueue<Order> queue2 = new ArrayBlockingQueue<>(10);

		for (int i = 0; i < nCustomers; i++) {
			new Customer("" + i, queue1).start();
		}

		for (int i = 0; i < nValidators; i++) {
			new OrderValidator(queue1, queue2).start();
		}

		for (int i = 0; i < nProcessors; i++) {
			new OrderProcessor(queue2).start();
		}
	}
	
	static class Order {
		public final String customerName;
		public final int itemId;
		public Order(String customerName, int itemId) {
			this.customerName = customerName;
			this.itemId = itemId;
		}
		
		@Override
		public String toString() {
			return "Order: [name = " + customerName + " ], [item = " + itemId +" ]";  
		}
	}
	
	
	static class Customer extends Thread {

		BlockingQueue<Order> orders;

		public Customer(String name, BlockingQueue<Order> orders) {
			super(name);
			this.orders = orders;
		}
		
		private Order createOrder() {
			Order o = new Order(getName(), (int) (Math.random()*100));
			System.out.println("Created:   " + o);
			return o;
		}
		
		private void handOverToValidator(Order o) throws InterruptedException {
			orders.put(o);
		}
		
		@Override
		public void run() {
			try {
				while(true) {
					Order o = createOrder();
					handOverToValidator(o);
					Thread.sleep((long) (Math.random()*1000));
				}
			} catch (InterruptedException e) {}
		}
	}
	
	
	static class OrderValidator extends Thread {

		BlockingQueue<Order> orders;
		BlockingQueue<Order> validatedOrders;

		public OrderValidator(BlockingQueue<Order> orders, BlockingQueue<Order> validatedOrders) {
			this.orders = orders;
			this.validatedOrders = validatedOrders;
		}
		
		public Order getNextOrder() throws InterruptedException {
			return orders.take();
		}
		
		public boolean isValid(Order o) {
			return o.itemId < 50;
		}
		
		public void handOverToProcessor(Order o) throws InterruptedException {
			validatedOrders.put(o);
		}
		
		@Override
		public void run() {
			try {
				while(true) {
					Order o = getNextOrder();
					if(isValid(o)) {
						handOverToProcessor(o);
					} else {
						System.err.println("Destroyed: " + o);
					}
					Thread.sleep((long) (Math.random()*1000));
				}
			} catch (InterruptedException e) {}
		}
	}
	
	
	static class OrderProcessor extends Thread {

		BlockingQueue<Order> validatedOrders;

		public OrderProcessor(BlockingQueue<Order> validatedOrders) {
			this.validatedOrders = validatedOrders;
		}
		
		public Order getNextOrder() throws InterruptedException {
			return validatedOrders.take(); //TODO
		}
		
		public void processOrder(Order o) {
			System.out.println("Processed: " + o);
		}
		
		@Override
		public void run() {
			try {
				while(true) {
					Order o = getNextOrder();
					processOrder(o);
					Thread.sleep((long) (Math.random()*1000));
				}
			} catch (InterruptedException e) {}
		}
	}
}
