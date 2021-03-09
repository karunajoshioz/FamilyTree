#Overview
The code is solution of the geek trust meet the family problem statement. https://www.geektrust.in/coding-problem/backend/family

#Steps to build the code
mvn clean install

#Steps to run the code
E:\Project_Workspace\FamilyTree>java -jar familytree.jar
Please enter your Initialzation and the inputData FileName<for eg initInput.txt, input1.txt>:
initInput.txt, input1.txt

Results: CHILD_ADDITION_SUCCEEDED
Asva Ketu
Vyas Ketu
CHILD_ADDITION_SUCCEEDED
Aria
Jnki Ahit


#Notes:
1. Application is built using Spring boot
2. Multiple service files are created for processing the file, and Family member service class
3. Security and Entitlement check not performed as this is not a prod like code.
4. I have tried to keep it simple and readable. Design pattern not followed for simplicity
