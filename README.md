MD to HTML Converter
====================
Interface
---------
### Program Compile with **CMD**
> 1. Make directory md_converter
>> $C:\Users\user> mkdir md_converter
> 2. Change current workspace in md_converter
>> $C:\Users\user> cd md_converter
> 3. Put md_converter.java, MDElementVisitor.java, README.md(input) file in md_converter directory
> 4. Compile java file (md_converter.java, MDElementVisitor.java)
>> $C:\Users\user\md_converter> javac -d . *.java
> 5. Execute java program with 3 input option
> - first input is html name file for output
> - second input is README.md file for input
> - third input is conversion option : plain, stylish, slide
>> $C:\Users\user\md_converter> java -classpath . md_converter.md_converter output.html README.md stylish
