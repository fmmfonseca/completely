# Description

*Completely* is a Java autocomplete library.

Autocomplete involves predicting a word or phrase that the user may type based on a partial query. The goal is to provide instant feedback and avoid unnecessary typing as the user formulates queries. Performance is a key issue since each keystroke from the user could invoke a query, and each query should be answered within a few milliseconds. What's more, because users often make spelling mistakes while typing, autocomplete should tolerate errors and differences in representation.

Needless to say, a standard sequential search is bound to be ineffective for anything other than small data sets. By contrast, *Completely* relies on text preprocessing to create an in-memory index for efficiently answering searches in large data sets. All in all, there are three fundamental components at play:

* [Analyzer](https://github.com/fmmfonseca/completely/tree/master/core/src/main/java/com/miguelfonseca/completely/text/analyze) function to filter, tokenize and/or transform text prior to indexing;
* [Index](https://github.com/fmmfonseca/completely/tree/master/core/src/main/java/com/miguelfonseca/completely/text/index) data structure for storing the mapping of text to the corresponding sources;
* [Automaton](https://github.com/fmmfonseca/completely/tree/master/core/src/main/java/com/miguelfonseca/completely/text/match) engine for text matching when searching;

Together these can used to tackle a variety of use cases, wherein the choice of components or combination thereof depends solely on the application at hand.

# Download

All release artifacts are available for download from the [Maven central repository](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.miguelfonseca.completely%22).

# Build from source

Building *Completely* requires Maven 3 and Java 11, or newer.

Download the source code:

    git clone https://github.com/fmmfonseca/completely.git

Build the JAR package:

    mvn clean package -DskipTests

# Run the sample

Install artifacts to the local repository:

    mvn install

Execute the main class:

    mvn exec:java -pl sample

# References

* Bořivoj Melichar. Approximate String Matching by Finite Automata;
* Gonzalo Navarro. A Guided Tour to Approximate String Matching;
* Leonid Boytsov. Indexing Methods for Approximate Dictionary Searching: Comparative Analysis;
* Marios Hadjieleftheriou and Divesh Srivastava. Approximate String Processing;
* Surajit Chaudhuri and Raghav Kaushik. Extending Autocompletion To Tolerate Errors;

# License

Released under The Apache Software License, Version 2.0
