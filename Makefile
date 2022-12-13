.PHONY: run-search
run-search:
	javac -cp 'utils-1.0-jar-with-dependencies.jar' Search.java
	java Search $(query) $(count)


.PHONY: run
run:
	 /usr/bin/env /Library/Java/JavaVirtualMachines/jdk1.8.0_241.jdk/Contents/Home/bin/java -cp /var/folders/rh/3ypkz9qs2t53sfhw7n7h_n640000gn/T/cp_cabgezhnf7rnb9ylsrsnaq1ao.jar src.Search $(query) $(count)


.PHONY: annotate
annotate: 
		/usr/bin/env /Library/Java/JavaVirtualMachines/jdk1.8.0_241.jdk/Contents/Home/bin/java -cp /var/folders/rh/3ypkz9qs2t53sfhw7n7h_n640000gn/T/cp_6e6s4uig7ydaseq2rzem58q2d.jar src.Annotador $(path) $(instance)