
.PHONY: search
search:
	javac src/Annotator.java
	java src.Search $(query) $(count)

.PHONY: annotate
annotate: 
		javac src/Annotator.java
		java src.Annotator $(file)