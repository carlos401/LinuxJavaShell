# LinuxJavaShell
The first project of CI-1310, consists on a simple shell interpreter developed under Linux Fedora OS.

## Developer

Carlos A Delgado Rojas <carlos.delgadorojas@ucr.ac.cr> 

## License & Copyright

This code is free. You can use, modify and distribute like you want.

---

# Instructions

## Check the files
1. Check that you have received the file called SimpleShell.jar
2. If you are reading this, you have README.md file too

## Run the executable

### Operating System
 I recommend the following operating systems:
* Linux Fedora 26
* Linux Ubuntu 17

This app running under other OS are not supporting.

### Open the application
1. Open the app called **Terminal**, and search the directory where you have the SimpleShell.jar
2. Then, run the SimpleShell using the command: 
  ```
  java -jar SimpleShell.jar
  ```

### Application commands
SimpleShell supports the major part of **standard commands** that you may use with the linux shell.
Some of this are:
* ls: lists the files in the current directory.
* pwd: prints the working directory.
* cal: shows the current month calendar.
* man: prints the manual of any installed app.

Also, the app includes **special commands** like:
1. cd: Allows to change the current directory to an specific.
  ```
  cd [directory]
  ```
  The parameter *directory* is the name of available directory, this may be optional:
   * If you don't type a parameter, by default, changes the current working directory to the user’s home directory.
   * "..", changes the current working directory to the parent's directory.
2. history: Prints out the contents of the history of commands that have been entered.
  ```
  history
  ```
  To runs an specific previous command in the history, type
    ```
    ![x]
    ```
    Where, the parameter *x* should be:
    
    * "!", to execute the last command
    * n, to execute the n-th command
### Close the application
To close the application, only type the command
```
exit
```

---

# Source code
You can get the source code via:
* The file called SRC-SS.zip
* Using this github repository <https://github.com/carlos401/LinuxJavaShell>
