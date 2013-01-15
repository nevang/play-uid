package gr.jkl.playUid

import org.specs2.mutable.Specification
import play.api.test.{ WithApplication, FakeApplication }
import gr.jkl.uid.Scheme

class UidPluginSpec extends Specification {
  "uid Play plugin" should {

    "be configurable by timestamp, sequence & node bits, epoch and node id" in new WithUidPlugin(44, 12, 8, 1351728000000L, 543) {
      val scheme = Scheme(44, 12, 8, 1351728000000L)
      UidPlugin.scheme mustEqual scheme
      UidPlugin.generator.scheme mustEqual scheme
      UidPlugin.generator.node mustEqual 543
    }
  }
}

class IdHandlingSpec extends Specification {
  "Id Handling" should {
    "provide the uid Play plugin Scheme and Generator" in new WithUidPlugin(44, 12, 8, 1351728000000L, 543) {
      val handler = new AnyRef with IdHandling
      handler.idScheme mustEqual UidPlugin.scheme
      handler.idGenerator mustEqual UidPlugin.generator
    }
  }
}

abstract class WithUidPlugin(tb: Long, nb: Long, sb: Long, e: Long, n: Long)
  extends WithApplication(FakeApplication(
    additionalPlugins = Seq("gr.jkl.playUid.UidPlugin"),
    additionalConfiguration = Map(
      "uid.timestampBits" -> tb,
      "uid.nodeBits" -> nb,
      "uid.sequenceBits" -> sb,
      "uid.epoch" -> e,
      "uid.node" -> n)))
