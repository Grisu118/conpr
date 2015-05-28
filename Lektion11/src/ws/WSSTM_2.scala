package ws

import scala.concurrent.stm._
import Util.thread
import java.util.concurrent.atomic.AtomicInteger

/* Aufgaben:
 * 1. Notieren Sie die erwarteten Ausgaben.
 * 2. Lassen Sie das Programm laufen. Haben sich Ihre Erwartungen bestätigt?
 */
object _05_AtomicityExceptions extends App {
  val r = Ref(1)
  var nonManaged = 0

  atomic { implicit tx =>

    try {
      println("a: " + r())
      r() = 13
      println("b: " + r())

      atomic { implicit tx =>
        println("c: " + r())
        r() = 26
        println("d: " + r())
        nonManaged = 13
        throw new IllegalStateException()
      }

    } catch {
      case e: IllegalStateException => println("f: " + r.single())
    }

    println("e: " + r())
  }
  
  println("r: " + r.single())
  println("nonManaged: " + nonManaged)
}

/* Aufgaben:
 * 1. Notieren Sie die erwarteten Ausgaben. Wann wird das "After commit" ausgegeben.
 * 2. Lassen Sie das Programm laufen. Haben sich Ihre Erwartungen bestätigt?
 */
object _06_AfterCommit extends App {

  atomic { implicit tx =>
    println("Start outer tx")

    atomic { implicit tx =>
      println("Start inner tx")
      Txn.afterCommit(_ => println("After commit"))
      println("End inner tx")
    }

    println("End outer tx")
  }

  println("Done")
}

  



