# Description

*Completely* is a Java autocomplete library.

Autocomplete involves predicting a word or phrase that the user may type based on a partial query. The goal is to provide instant feeback and avoid unecessary typing as the user formulates queries. Performance is a key issue since each keystroke from the user could invoke a query, and each query should be answered within few milliseconds. *Completely* relies on text preprocessing to create an in-memory index data structure for efficiently answering searches.

# References

* Gonzalo Navarro. A Guided Tour to Approximate String Matching;
* Leonid Boytsov. Indexing Methods for Approximate Dictionary Searching: Comparative Analysis;
* Marios Hadjieleftheriou and Divesh Srivastava. Approximate String Processing;

# License

Released under The Apache Software License, Version 2.0