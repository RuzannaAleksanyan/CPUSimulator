# CPU Simulator Program in Java
This is a CPU simulator program implemented in Java. The program simulates a pseudo-abstract CPU with six registers and a pseudo-abstract memory. The CPU supports basic operations such as moving data between registers, performing arithmetic operations, comparing values, and jumping to different instructions based on conditions.
# CPU Registers
## The CPU has six registers:
- AYB (Accumulator Register): The result of any operation is automatically saved in this register.
- BEN: General-purpose register.
- GIM: General-purpose register.
- DA: General-purpose register.
- EC: General-purpose register.
- ZA (Flagger Register): Stores the results of arithmetic operations and comparison results.
## Memory
- The memory consists of 32 addresses, each representing two byte of data. The CPU does not work with values larger than one byte.
# Supported Instructions
## The CPU supports the following instructions:
- MOV: Move data between registers or between a register and memory.
- ADD: Add two values and store the result in the AYB register.
- SUB: Subtract one value from another and store the result in the AYB register.
- MUL: Multiply two values and store the result in the AYB register.
- DIV: Divide one value by another and store the result in the AYB register.
- AND: Perform a bitwise AND operation between two values and store the result in the AYB register.
- OR: Perform a bitwise OR operation between two values and store the result in the AYB register.
- NOT: Perform a bitwise NOT operation on a value and store the result in the AYB register.
- CMP: Compare two values and store the result in the DA register.
- JMP: Unconditionally jump to a specified address.
- JG: Jump to a specified address if the result of the previous comparison is greater than zero.
- JL: Jump to a specified address if the result of the previous comparison is less than zero.
- JE: Jump to a specified address if the result of the previous comparison is equal to zero.
# Execution
The program reads an assembly code file as an input argument. Each instruction is a value occupying two byte of space. The program size cannot exceed 32 bytes.  After the execution, the contents of the memory are printed to the screen using the dumpMemory() function.
# Usage
## To run the program, follow these steps:
- Compile the Java file: javac CPU.java
- Run the program, providing the assembly code file as an argument: java CPU
