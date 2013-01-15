package gr.jkl.playUid

import play.api.Play.current

/** Provides an implicit Scheme and an Id Generator. */
trait IdHandling {

  /** The Id Scheme for the current Application. */
  implicit final val idScheme = UidPlugin.scheme

  /** The Id Generator for the current node. */
  final val idGenerator = UidPlugin.generator
}
