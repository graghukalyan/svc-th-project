
## Project Title
`svc-th-project` is a java based application which lets you create a lightweight in-memory key/value datastore which supports transaction requests from multiple clients.

## Key Design Considerations
The following are the key design considerations for this release : 
```
 - This release leverages an individual Staging DataStore/Cache for each respective client connection
while having one Primary DataStore/Cache which holds all the committed data.
      
```

## Assumptions (In Scope & Out of Scope)

```
In Scope

   - No upper limit on the size or the number of entries in the datastore.
   - No upper limit on the number of data modification commands in each transaction request
   - Any data modification command must be part of a transaction preceeding with a START command & ending with either a COMMIT (or) a ROLLBACK command
   - DELETE functionality would throw an exception if the requested key doesn't exist. Code would not fail silently.   
   
```

```
Out of Scope

   - No dirty reads available
   - No periodic backups to the disk via I/O
   - No eviction policy has been implemented for current release
   - No authentication mechanism has been implemented for the current release
   - Issuing data modification commands individually without a START and COMMIT/ROLLBACK

```
## Prerequisites

Things you need to install the application and how to install them.

```
Java 15

   - Goto https://jdk.java.net/archive/
   - Look for Release 15 (15.0.2, 15.0.1, 15 GA)
   - Download & Install based on your underlying OS

```

```
Gradle 6.7.1

   - Goto the release page https://services.gradle.org/distributions/gradle-6.7.1-bin.zip
   - If you are a Linux & MacOS user, 
unzip the distribution zip file in the directory of your choosing, e.g.:
      $ mkdir /opt/gradle
      $ unzip -d /opt/gradle gradle-6.7.1-bin.zip
      $ ls /opt/gradle/gradle-6.7.1

   - Alternatively if you are a Windows user, you can unpack the Gradle distribution ZIP file into C:\Gradle or another directory of your choice.

```

Configure your system environment to include **bin** directory of the gradle & java distributions in the PATH variable




## Run Locally

Clone the project & checkout the **_develop_** branch

```bash
git clone --single-branch --branch develop https://github.com/graghukalyan/svc-th-project.git

```

Go to the project directory

```bash
  cd svc-th-project
```

Build the project

```bash
  

```

Start one instance of project as _SERVER_

```bash
  
```

Start one (or more) instances of project as _CLIENT_

```bash
  
```

## Appendix

Any additional information goes here

