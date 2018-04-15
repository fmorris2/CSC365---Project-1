# CSC-365 Classwork
This repository was not set-up correctly. It contains every assignment that was done in this class. Since each assignment built on the last,
the most recent commit represents the final assignment. You can see snapshots of the other assignments by looking at the specific commit when
that assignment was completed.

## Assignment #1
This assignment asks you to create a data categorization program.
The program reads 10 (or more) data sources -- web pages or other data accessible with a url. The urls can be hardwired in the program, and (re)read when the program starts. For each source, the program maintains frequencies of keys (normally space-delimited words, but possibly other features).
The user can enter any other URL, and the program reports which other known source is most closely related, using a similarity metric (some possible metrics will be discussed in class)
The presentation details are up to you. The implementation restrictions are:

Use java.util.collections for all data structures, except for a custom hash table class you implement for maintaining key frequencies. Use other existing JDK classes for any other purposes. Read through the Collections tutorial first.
Use Swing components for the GUI. Read through the relevant parts of the Swing tutorial first.
Use Java networking components for accessing web pages. See the Java networking with URLs tutorial
Test your program thoroughly before submitting, and arrange a demo within 48 hours of submitting. (Demoing before submitting is strongly encouraged.)

## Assignment #2
This extends Assignment 1 using persistent data structures and additional similarity metrics. It requires two programs.

Loader
Create a hash-based cache for web pages, using any of the techniques described in class. Also create a persistent BTree class with words as keys and sets of (page, frequency) as values.
Preload these by selecting a set of at least 20 root pages, and expand by adding pages linked from each.

Optionally, pre-divide these pages into clusters using k-means or a similar metric.

Application
Extend Assignment 1 to recommend a set of similar pages (using cluster membership or other metrics) from the above data structures. Check if any cached pages change since loading and update if necessary.

## Assignment #3
Write a program that collects at least 500 Wikipedia pages and links from these pages to other Wikipedia pages. For each page, collect similarity data (for example word frequencies or word2vec). (It is OK to use other linked data sources, but ask first.) As a connectivity check, report the number of spanning trees (for any arbitrary node as initial starting point). Store persistently (possibly just in a Serialized file). Use caching as appropriate to avoid unnecessary reconstruction.
Write a program (either GUI or web-based) that reads the graph from step 1, allows a user to select any two pages (by title) and displays graphically the shortest (weighted by any similarity metric) path between them, if one exists, as well as the most similar node for each.
Extra credit (for up to 60 lateness points for previous assignments) (1): use a custom non-naive string search algorithm for embedded URLs. (2): Persist data using a custom compression/decompression algorithm.
