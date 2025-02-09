# Compiler
>This project is a simple compiler for the subject of Compilers at the university.
  This project has implemented the Scanner, Parser, Semantic Analyzer and the Intermediate Code Generator. 
  Part of the cover was done alone, and as the project progressed, other parts were added. 
  The language for which this compiler was written is simple and was created by the professor.

## Prerequisites
  1. You need install **Java** to do this project.
  2. You need to know how the compiler is divided.
  3. You need to understand how each compiler part works: **Scanner, Parser, Semantic Analyzer and ICG**.
  
  [Compilers:Principles, Techniques, and Tools](https://www.amazon.com/Compilers-Principles-Techniques-Tools-2nd/dp/0321486811)
  
 ## How to run
   ### To run in the terminal:
   1. In the src folder, go to each .java and delete all packages;
   2. after deleting the packages, open the terminal in the src folder;
   3. run the commands.
       > javac Main.java
       
       > java Main file.txt (programing language file)
        
   ### To run in te IDE:
   1. Put the name of the txt file in place of args[0].
      
      > Parser parser = new Parser(args[0]);
      
      Example:
      > Parser parser = new Parser("file.txt");
      
 If you have generated a _3-address language program_, then the compiler is working.
 
 **Note**: Do not forget, that you must have a txt file that is the program, in the programming language for the project.
 
 ## Author
 > - **Ingrid Barbosa**
