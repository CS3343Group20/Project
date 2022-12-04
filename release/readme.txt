README - CS3343 Lift Simulator (Group 20)
=========================================

#Description
-----------------------------------------
- This program is a lift simulator, in the setup of 2 lift in a building with 20 floor.

- It aims to archieve shortest waiting time for passengers with its own distance calculation logic.

- It will show the lifting simulation when there are passenger in the system.

#Installation
-----------------------------------------
1. Install the latest Java SE Runtime Environment 
        -link-> http://www.oracle.com/technetwork/java/javase/downloads/index.html

2. Setup the path for java
        -Reference-> https://confluence.atlassian.com/doc/setting-the-java_home-variable-in-windows-8895.html

3. No extra installation is required, run the program by double clicking liftsimulator.bat

** The program is only tested on Window environment. Mac and Linux may not be supported**

#User Guide
-----------------------------------------
-Configure input data in "input.txt" under src folder

-test input are forming in lines, each line represent a seperate request from passenger, format should be as follow:
	[request time] [passenger current floor] [passenger target floor] [passenger weight]

-input constraints:
	-request time: in the format of hh:mm:ss
	-current floor and target floor: >=0 and <=20
	-weight: >=0 and <=1000

**for reality simulation, passenger weight should be under 150kg**

-run the program by double clicking liftsimulator.bat

#Versions
-----------------------------------------
Release 1.0 
	-with basic lifting function without waiting time optimization

Release 1.1
	-basic lifting without bugs
	-refactor

Release 1.2
	-request allocation optimization (beta)

Release 1.3
	-complete request allocation optimization

Final Release 1.4
	-refactor