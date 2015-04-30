package functions

import java.util.concurrent.atomic.{AtomicInteger, AtomicReference}

object Einleitung {
  def call(): Unit = println(":-)")

  def async(action: () => Unit): Unit = {
    new Thread() {
      override def run() {
        action() // Hier wird die übergebene Funktion aufgerufen
      }
    }.start()
  }

  def main(args: Array[String]) {
    async(call)
  }
}

object Aufgabe1 {

  def clock(tick: Int) = println("Tick" + tick)
  def everySecond(action: (Int) => Unit): Unit = {
    new Thread() {
      override def run(): Unit = {
        var i = 0
        while(i < 50) {
          action(i)
          Thread.sleep(1000)
          i+= 1
        }
      }
    }.start()
  }

  def main(args: Array[String]) {

    /* Anonyme Funktionen */
    everySecond(i => println("Tick(" + i + ")"))

    everySecond(i => {
      println("Tock()")
      println("Tick(" + i + ")")
    })

    everySecond(clock)
  }
}

object Aufgabe2 {
  def time(block: () => Unit): Unit = {
    val start = System.currentTimeMillis()
    block()
    val end = System.currentTimeMillis()
    println("[" + (end - start) + "]")
  }

  def time[A](block: () => A): A = {
    val start = System.currentTimeMillis()
    val result = block()
    val end = System.currentTimeMillis()
    println("[" + (end - start) + "]")
    result
  }

  def main(args: Array[String]): Unit = {
    time(() => {
      Thread.sleep(100)
    })

    val a = time(() => {
      Thread.sleep(100)
      "Done"
    })

    println(a)

  }
}

object Aufgabe3 {
  class NiceAtomicInt(init: Int) {
    val int = new AtomicInteger(init)

    def modify(action: (Int) => Int): Unit = {
      while (true) {
        val old = int.get()
        val n = action(old)
        if (int.compareAndSet(old, n)) {
          return
        }
      }
    }
  }

  class NiceAtomic[A](init: A) {
    val o = new AtomicReference[A](init)

    def modify(action: (A) => A): Unit = {
      while (true) {
        val old = o.get()
        val n = action(old)
        if (o.compareAndSet(old, n))
          return
      }
    }

    override def toString: String =
      o.get().toString
  }

  def main(args: Array[String]): Unit = {
    val balance = new NiceAtomicInt(0)
    balance.modify(b => b + 10)

    val list = new NiceAtomic(List(1,2,3))
    list.modify(l => 0 :: l)
    println(list)
  }
}

/* ==Lösungen==
 
 Die Lösungen können mit NSACryptoTool.scala decodiert werden. 
 
 
 Aufgabe1:
 
 bowrpg Nhstnor1 {

  qrs pybpx(v: Vag) = cevagya("Gvpx(" + v + ")")

  qrs rirelFrpbaq(npgvba: (Vag) => Havg): Havg = {
    arj Guernq() {
      bireevqr qrs eha(): Havg = {
        ine v = 0
        juvyr (gehr) {
          npgvba(v)
          v += 1
          Guernq.fyrrc(1000)
        }
      }
    }.fgneg()
  }

  qrs znva(netf: Neenl[Fgevat]) {
    rirelFrpbaq(pybpx)

    /* Nabalzr Shaxgvbara */
    rirelFrpbaq(v => cevagya("Gvpx(" + v + ")"))

    rirelFrpbaq(v => {
      cevagya("Gbpx()")
      cevagya("Gvpx(" + v + ")")
    })
  }
}


Aufgabe 2:

bowrpg Nhstnor2 {
  qrs gvzr(oybpx: () => Havg): Havg = {
    iny fgneg = Flfgrz.pheeragGvzrZvyyvf()
    oybpx()
    iny raq = Flfgrz.pheeragGvzrZvyyvf()
    cevagya("[" + (raq - fgneg) + " zf]")
  }

  qrs gvzr[N](oybpx: () => N): N = {
    iny fgneg = Flfgrz.pheeragGvzrZvyyvf()
    iny erfhyg = oybpx()
    iny raq = Flfgrz.pheeragGvzrZvyyvf()
    cevagya("[" + (raq - fgneg) + " zf]")
    erfhyg
  }

  qrs znva(netf: Neenl[Fgevat]): Havg = {
    gvzr(() => {
      Guernq.fyrrc(100)
    })

    iny n = gvzr(() => {
      Guernq.fyrrc(100)
      "Qbar"
    })
  }
}


Aufgabe 3:

bowrpg Nhstnor3 {
  pynff AvprNgbzvpVag(vavg: Vag) {
    iny ers = arj NgbzvpVagrtre(vavg)
    qrs zbqvsl(s: Vag => Vag): Havg = {
      juvyr (gehr) {
        iny pheeragInyhr = ers.trg()
        iny arjInyhr = s(pheeragInyhr)
        vs (ers.pbzcnerNaqFrg(pheeragInyhr, arjInyhr)) erghea
      }
    }
  }
  
  pynff AvprNgbzvp[N](vavg: N) {
    iny ers = arj NgbzvpErsrerapr[N](vavg)
    qrs zbqvsl(s: N => N): Havg = {
      juvyr (gehr) {
        iny pheeragInyhr = ers.trg()
        iny arjInyhr = s(pheeragInyhr)
        vs (ers.pbzcnerNaqFrg(pheeragInyhr, arjInyhr)) erghea
      }
    }
    
    bireevqr qrs gbFgevat(): Fgevat = 
      ers.trg().gbFgevat()
  }
    
  qrs znva(netf: Neenl[Fgevat]): Havg = {
    iny onynapr = arj AvprNgbzvpVag(0)
    onynapr.zbqvsl(o => o + 10)
    
    iny yvfg = arj AvprNgbzvp(Yvfg(1,2,3))
    yvfg.zbqvsl(y => 0 :: y)
    cevagya(yvfg)
  }
}

*/

