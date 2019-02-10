# Team 19 CodeU Project

# Tests
We use Java's junit for testing. This is optional if you want to learn about testing.
Open up `src/test/java/com/google/codeu/data/MessageTest.java` for a simple example.
[junit documentation](http://junit.sourceforge.net/javadoc/)

To execute the tests:

`mvn test`

The tests will also get run when we open Pull Requests, and whenever you push
something to the Git server.

# Checkystle linter
We are using a linter to ensure standard formatting and consistency with our
java code.

This will be enabled when you try to commit your code. If there are lint errors,
you will have to resolve them before your code can be committed.

Pre-commit example:

`git commit -m "This is my commit message"`

Maven example (if you want to lint before committing):

`mvn checkstyle:check`

## Setup
There are a few required steps to do before it can be used on your local machine.
In your terminal, navigate to the root directory of the project.

`cd ~Desktop/codeu-starter-project`

Now, let's set executable permissions on the `pre-commit` file.

`chmod +x hooks/pre-commit`

We must now tell Git to use the `pre-commit` file as our pre-commit hook.

`git config core.hooksPath hooks`
