OWLViz
======

A Protege plug-in that provides a graphical representation of the class hierarchy in an OWL ontology.

<img src="https://raw.githubusercontent.com/protegeproject/github-wiki-resources/master/owlviz/README/owlviz-screenshot.png" alt="OWLViz Screenshot" width="650px"/>

Installation
------------

By default OWLViz comes together with Protege installation bundle. Any new release of the plugin will be automatically updated using Protege auto-update mechanism. Before running the plugin, you must install [GraphViz](http://www.graphviz.org) which is a third-party library that is available for free. 


GraphViz Dependency
-------------------

Go to [GraphViz download page](http://www.graphviz.org/Download.php) and select your operating system to start downloading.
  * Ubuntu: http://www.graphviz.org/Download_linux_ubuntu.php
  * Windows: http://www.graphviz.org/Download_windows.php
  * Mac OS: http://www.graphviz.org/Download_macos.php

**Note**: For Mac OS version newer than Mountain Lion (OSX >10.8) you can use [Homebrew](http://brew.sh/) package manager. Once you have Homebrew installed in your machine, install GraphViz using the command: `brew install graphviz`

Troubleshooting
---------------

**DOT Error**

![DOT Error](http://protegewiki.stanford.edu/images/4/46/OwlViz-dot-error.jpg)

If you got this error message, it means that OWLViz could not determine the location of GraphViz installation. Please install GraphViz first by following the instruction above. If you already installed GraphViz then open Protege and go to **Preferences...** menu and select **OWLViz** tab.

<img src="https://raw.githubusercontent.com/protegeproject/github-wiki-resources/master/owlviz/README/owlviz-pref.png" alt="OWLViz Preference" width="350px"/>

Add the correct path of your GraphViz installation in "Dot Application Path" field.
