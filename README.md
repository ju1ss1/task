# Cucumber-Java-task

This task opens amazon web page.<br>
Makes search from Amazon.com products which contains Nikon.<br>
Sorts them from highest to lowest and selects second product from the list.<br>
Finally asserts that does string NikonD3X exist in product page.

## Get the code

Git:

    git clone https://github.com/ju1ss1/task.git
    cd task


## Use Maven

Open a command window and run:

   mvn clean test
    

## Overriding options

It's possible to override test url by givin url as a parameter in command line when executing the test.

	mvn cleant test -Durl=<your url>

