# Git Commit Convention

Simple verbs followed by their subject or file or function name are helpful.

### Examples

```
Fix database getUser
```

Commit has a fix for a bug in the getUser function of the database class.

```
Update jacoco reporting xml #15
```

Commit enhances the jacoco code coverage reports with XML output. Also includes a #15 issue reference which is nice.

```
Remove codeclimate
```

Commit deletes codeclimate files, references, etc.

### Do not do this

```
updated for #103
```

Commit is vague on its own, even with the issue #103 reference. It forces users to hunt down what the commit was about. This is not ideal.

### Extras

#### Message convention

 - Past or present tense "Update" or "Updated" is not a big issue, but we prefer "Update" present tense.

 - Capitalization is not an issue, but we prefer the first letter capitalized of a commit message. "Update" not "update".

 - No period "." at the end is necessary.

Ultimately, we are not very picky about how you contribute to our project. We are just happy you're interested in helping in the first place!

#### Gradle

 - Build before committing `./gradlew clean build` and make sure it passes.
 - Test before commmitting `./gradlew test` and make sure it passes.

 Any new functions/classes should have a corresponding unit test if possible. If you don't know how please ask, or include a brief message in your Pull Request that you need help adding tests. Thank you!
 