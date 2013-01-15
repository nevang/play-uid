play-uid
========

Play plugin for [uid][1].

Install
-------

Current version is 1.1.1-SNAPSHOT and is released for Play 2.10-RC2. 
You can install it via the Sonatype Snapshot repository. Add the following
lines in your sbt built: 
```scala
resolvers += Opts.resolver.sonatypeSnapshots

libraryDependencies += "gr.jkl" %% "playuid" % "1.1.1-SNAPSHOT"
```

Setup
-----

In Play's `application.conf` you have to define the following parameters. See 
[uid project's page][1] for more information.
* `uid.timestampBits`: The number of bits devoted to the id's timestamp of your id Scheme
* `uid.nodeBits`: The number of bits devoted to the id's node of your id Scheme
* `uid.sequenceBits`: The number of bits devoted to the id's sequence of your id Scheme
* `uid.epoch`: The epoch of your Id Scheme
* `uid.node`: The id of the machine

You also have to add the following line in `conf/play.plugins`.

```
1000:gr.jkl.playUid.UidPlugin
```

[1]: http://nevang.github.com/uid/ "gr.jkl.uid"
