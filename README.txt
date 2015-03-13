The tarball has the following structure:

reactome-wikipathways-exchange
==============================

The files that are also available at http://bitbucket.org/leonth/reactome-wikipathways-exchange . The files in this repository are new files supposed to be relatively independent to the current Reactome codebase, hence the separate new repository.

Note that the size of lib/pathvisio.jar is reduced to zero to save space. Unbundle the Mercurial bundle below to get the original pathvisio.jar.

reactome-wikipathways-exchange.bundle
=====================================

Mercurial bundle of http://bitbucket.org/leonth/reactome-wikipathways-exchange .

org.gk.gpml
===========

Contains patches that were generated using the cvsps tool:

  cvsps -a leon -g -p outputdir
  
The repository that contain this code is not currently available to the public, but public snapshots are available at http://reactome.org/download/index.html . The code in these patches will eventually make way to those snapshots. One of the patches (17.patch) actually contains the files copied from the reactome-wikipathways-exchange Mercurial repository.


