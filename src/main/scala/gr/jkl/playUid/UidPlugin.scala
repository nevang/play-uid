package gr.jkl.playUid

import play.api.{ Plugin, Application, UnexpectedException }
import gr.jkl.uid.{ Scheme, Generator }
import scala.util.{ Try, Success, Failure }

/** Plugin for Id generation and handling. */
class UidPlugin(app: Application) extends Plugin {

  private[this] def getLong(path: String) = Try(app.configuration.underlying.getLong(path))

  private[playUid] val (scheme, generator): (Scheme, Generator) = (for {
    tb <- getLong("uid.timestampBits")
    nb <- getLong("uid.nodeBits")
    sb <- getLong("uid.sequenceBits")
    ep <- getLong("uid.epoch")
    no <- getLong("uid.node")
    sc <- Try(Scheme(tb, nb, sb, ep))
    ge <- Try(Generator(no)(sc))
  } yield (sc, ge)) match {
    case Success(p) => p
    case Failure(e) =>
      throw UnexpectedException(Some("Invalid configuration for UidPlugin. Edit your conf/application.conf file with valid values for the following fields: uid.timestampBits, uid.nodeBits, uid.sequenceBits, uid.epoch & uid.node."), Some(e))
  }
}

private[playUid] object UidPlugin {
  private[playUid] def scheme(implicit app: Application) = current.scheme

  private[playUid] def generator(implicit app: Application) = current.generator

  private[this] def current(implicit app: Application): UidPlugin = app.plugin[UidPlugin] match {
    case Some(plugin) => plugin
    case _ =>
      throw UnexpectedException(Some("The UidPlugin has not been initialized! Please edit your conf/play.plugins file and add the following line: '1000:gr.jkl.playUid.UidPlugin'."))
  }
}
