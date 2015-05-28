package le

import scala.concurrent.stm._
import scala.collection.immutable.Queue
import Util._

object _01_RefCompile {
  val r1 = Ref(1)
  val r2: Ref[Int] = Ref(1)

  
  //val insideR1 = r1() // Does NOT compile
  //r2() = 13 // Does NOT compile
  
  atomic { implicit tx =>
    r1.set(12)
    r1() = 13
    
    val i = r1.get
    val j = r1()
  }

  atomic { implicit tx =>
    val a = r1() // Compiles
    r2() = 12
  }
}

object _02_SingleOperationTransactions {
  val ref: Ref[Int] = Ref(1)
  val refView: Ref.View[Int] = ref.single

  refView.transform(_ + 1)

  refView() = 42 // Write
  val insideRef = refView() // Read
}



object _03_Isolation extends App {
  val r = Ref(1)

  thread {
    atomic { implicit tx =>
      println("Tx1: About to change Ref")
      r() = 12
      println("Tx1: Changed ref to " + r())
      Thread.sleep(3000)
      println("Tx1: About to commit")
    }
  }

  atomic { implicit tx =>
    println("Tx2: Starting Transaction")
    while (r() != 12) {
      println("Tx2: Read " + r())
      Thread.sleep(400)
    }
    println("Tx2: Read " + r())
  }
}

object _04_AtomicityExceptions extends App {
  val r = Ref(1)

  try {

    atomic { implicit tx =>
      println("Within tx 1: " + r())
      r() = 13
      println("Within tx 1: " + r())
      throw new IllegalStateException()
    }

  } catch {
    case e: IllegalStateException => println("After exception: " + r.single())
  }

}

object _05_AfterCommit extends App {

  atomic { implicit tx =>
    println("Start outer tx")

    atomic { implicit tx =>
      println("Start inner tx")
      Txn.afterCommit(_ => println("After commit"))
      println("End inner tx")
    }

    println("End outer tx")
  }
}

object _06_AfterRollback extends App {
  atomic { implicit tx =>
    println("Start outer tx")

    atomic { implicit tx =>
      println("Startinner tx")
      Txn.afterRollback(_ => println("After rollback"))
      println("End inner tx")
    }

    throw new IllegalStateException("Huston we've got a pr...")

    println("End outer tx")
  }
}

object Util {
  def thread(b: => Unit): Thread = {
    val t = new Thread() {
      override def run(): Unit = b
    }
    t.start
    t
  }
}