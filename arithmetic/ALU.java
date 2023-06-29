package arithmethic;
import memory.RAM;
import memory.Registers;

public class ALU {
    public Registers reg = new Registers();
    public static RAM m = new RAM();
    public ADD add = new ADD();
    public SUB sub = new SUB();
    public MUL mul = new MUL();
    public OR or = new OR();
    public AND and = new AND();
    public DIV div = new DIV();
    public CMP cmp = new CMP();

    
    public void flag(short val)
    {
        short x = 0;
        if(((val >> 15) & 1) == 1){ // sign flag
            x = (short) (x | 1);
        } 
        if(val == 0){ // zero flag 
            x = (short) (x | 2);
        } 
        if(val > 255){ // owerflov flag
            x = (short) (x | 4);
        }
        reg.setZa(x);
    }

// cmp = 0
    public void cmp(int address, String op1, String op2, String filePath) throws Exception {
    if (op1.startsWith("[")) { 
        if (isInteger(op2)) { 
            short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
            if (dest_address < m.memoryStartAddress(filePath)) {
                throw new Exception("Invalid memory exception");
            }
            short opcode = 0; 
            short src_opcode = Short.parseShort(op2);
            RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_opcode);
            cmp.compareMemoryInt(dest_address, src_opcode);
        } else if (op2.startsWith("[")) { // memory memory
            short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
            if (dest_address < m.memoryStartAddress(filePath)) {
                throw new Exception("Invalid memory exception");
            }
            short opcode = 0; // Comparison opcode
            short src_address = Short.parseShort(op2.substring(1, op2.length() - 1));
            RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_address);
            cmp.compareMemoryMemory(dest_address, src_address);
        } else { // memory register
            short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
            if (dest_address < m.memoryStartAddress(filePath)) {
                throw new Exception("Invalid memory exception");
            }
            short opcode = 0; // Comparison opcode
            short src_opcode = reg.registerToOpcode(op2);
            RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_opcode);
            cmp.compareMemoryRegister(dest_address, src_opcode);
        }
    } else { 
        if (isInteger(op2)) { 
            short opcode = 0; 
            short dest_opcode = reg.registerToOpcode(op1);
            short src_opcode = Short.parseShort(op2);
            RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
            cmp.compareRegisterInt(dest_opcode, src_opcode);
        } else if (op2.startsWith("[")) { 
            short opcode = 0; 
            short dest_opcode = reg.registerToOpcode(op1);
            short src_address = Short.parseShort(op2.substring(1, op2.length() - 1));
            RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_address);
            cmp.compareRegisterMemory(dest_opcode, src_address);
        } else {
            short opcode = 0; 
            short dest_opcode = reg.registerToOpcode(op1);
            short src_opcode = reg.registerToOpcode(op2);
            RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
            cmp.compareFromRegisterRegister(dest_opcode, src_opcode);
        }
    }
}

// div = 1
    public void div(int address, String op1, String op2, String filePath) throws Exception {
        if(Short.parseShort(op2) != 0){
            if (op1.startsWith("[")) { 
                if (isInteger(op2)) { 
                    short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
                    if (dest_address < m.memoryStartAddress(filePath)) {
                        throw new Exception("Invalid memory exception");
                    }
                    short opcode = 1; 
                    int src_opcode = Integer.parseInt(op2);
                    RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_opcode);
                    System.out.println(RAM.memory[address]);
                    RAM.memory[dest_address] /= (short) src_opcode;
                } else if (op2.startsWith("[")) { 
                    short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
                    if (dest_address < m.memoryStartAddress(filePath)) {
                        throw new Exception("Invalid memory exception");
                    }
                    short opcode = 1; 
                    int src_address = Integer.parseInt(op2.substring(1, op2.length() - 1));
                    RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_address);
                    RAM.memory[dest_address] /= RAM.memory[src_address];
                } else { 
                    short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
                    if (dest_address < m.memoryStartAddress(filePath)) {
                        throw new Exception("Invalid memory exception");
                    }
                    short opcode = 1; 
                    short src_opcode = (short) (reg.registerToOpcode(op2));
                    RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_opcode);
                    div.divFromMemoryRegister(dest_address, src_opcode);
                }
            } else {
                if (isInteger(op2)) { 
                    short opcode = 1; 
                    short dest_opcode = reg.registerToOpcode(op1);
                    short src_opcode = Short.parseShort(op2);
                    RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
                    div.divFromRegisterInt(dest_opcode, src_opcode);
                } else if (op2.startsWith("[")) { 
                    short opcode = 1; 
                    short dest_opcode = reg.registerToOpcode(op1);
                    short src_opcode = Short.parseShort(op2.substring(1, op2.length() - 1));
                    RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
                    div.divFromRegisterMemory(dest_opcode, src_opcode);
                } else { 
                    short opcode = 1; 
                    short dest_opcode = reg.registerToOpcode(op1);
                    short src_opcode = reg.registerToOpcode(op2);
                    RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
                    div.divFromRegisterRegister(dest_opcode, src_opcode);
                }
            }
        }
    }

// not = 2
    public void not(int address, String operand, String filePath) throws Exception {
        if (operand.startsWith("[")) { 
            short dest_address = Short.parseShort(operand.substring(1, operand.length() - 1));
            if (dest_address < m.memoryStartAddress(filePath)) {
                throw new Exception("Invalid memory exception");
            }
            short opcode = 2; 
            RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6));
            RAM.memory[dest_address] = (short) ~RAM.memory[dest_address];
            } else { 
                short opcode = 2; 
                short dest_opcode = reg.registerToOpcode(operand);
                RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6));
                switch (dest_opcode) {
                case 0:
                    reg.setAyb((short) ~reg.getAyb());
                    break;
                case 1:
                    reg.setBen((short) ~reg.getBen());
                    break;
                case 2:
                    reg.setGim((short) ~reg.getGim());
                    break;
                case 3:
                    reg.setDa((short) ~reg.getDa());
                    break;
                case 4:
                    reg.setEch((short) ~reg.getEch());
                    break;
                case 5:
                    reg.setZa((short) ~reg.getZa());
                    break;
                default:
                    System.out.println("Invalid register");
            }
        }
    }

// and = 3
    public void and(int address, String op1, String op2, String filePath) throws Exception {
        if (op1.startsWith("[")) { 
            if (isInteger(op2)) { 
                short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
                if (dest_address < m.memoryStartAddress(filePath)) {
                    throw new Exception("Invalid memory exception");
                }
                short opcode = 3; 
                int src_opcode = Integer.parseInt(op2);
                RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_opcode);
                RAM.memory[dest_address] &= (short) src_opcode;
            } else if (op2.startsWith("[")) { 
                short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
                if (dest_address < m.memoryStartAddress(filePath)) {
                    throw new Exception("Invalid memory exception");
                }
                short opcode = 3; 
                int src_address = Integer.parseInt(op2.substring(1, op2.length() - 1));
                RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_address);
                RAM.memory[dest_address] &= RAM.memory[src_address];
            } else {
                short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
                if (dest_address < m.memoryStartAddress(filePath)) {
                    throw new Exception("Invalid memory exception");
                }
                short opcode = 3; 
                short src_opcode = (short) (reg.registerToOpcode(op2));
                RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_opcode);
                and.andFromMemoryRegister(dest_address, src_opcode);
            }
        } else { 
            if (isInteger(op2)) { 
                short opcode = 3; 
                short dest_opcode = reg.registerToOpcode(op1);
                short src_opcode = Short.parseShort(op2);
                RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
                and.andFromRegisterInt(dest_opcode, src_opcode);
            } else if (op2.startsWith("[")) { 
                short opcode = 3; 
                short dest_opcode = reg.registerToOpcode(op1);
                short src_opcode = Short.parseShort(op2.substring(1, op2.length() - 1));
                RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
                and.andFromRegisterMemory(dest_opcode, src_opcode);
            } else { 
                short opcode = 3; 
                short dest_opcode = reg.registerToOpcode(op1);
                short src_opcode = reg.registerToOpcode(op2);
                RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
                and.andFromRegisterRegister(dest_opcode, src_opcode);
            }
        }
    }


// or = 4
    public void or(int address, String op1, String op2, String filePath) throws Exception {
        if (op1.startsWith("[")) { 
            if (isInteger(op2)) { 
                short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
                if (dest_address < m.memoryStartAddress(filePath)) {
                    throw new Exception("Invalid memory exception");
                }
                short opcode = 4; 
                int src_opcode = Integer.parseInt(op2);
                RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_opcode);
                RAM.memory[dest_address] |= (short) src_opcode;
            } else if (op2.startsWith("[")) { 
                short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
                if (dest_address < m.memoryStartAddress(filePath)) {
                    throw new Exception("Invalid memory exception");
                }
                short opcode = 4; 
                int src_address = Integer.parseInt(op2.substring(1, op2.length() - 1));
                RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_address);
                RAM.memory[dest_address] |= RAM.memory[src_address];
            } else { 
                short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
                if (dest_address < m.memoryStartAddress(filePath)) {
                    throw new Exception("Invalid memory exception");
                }
                short opcode = 4; 
                short src_opcode = (short) (reg.registerToOpcode(op2));
                RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_opcode);
                or.orFromMemoryRegister(dest_address, src_opcode);
            }
        } else { 
            if (isInteger(op2)) { 
                short opcode = 4; 
                short dest_opcode = reg.registerToOpcode(op1);
                short src_opcode = Short.parseShort(op2);
                RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
                or.orFromRegisterInt(dest_opcode, src_opcode);
            } else if (op2.startsWith("[")) { 
                short opcode = 4; 
                short dest_opcode = reg.registerToOpcode(op1);
                short src_opcode = Short.parseShort(op2.substring(1, op2.length() - 1));
                short x = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
                RAM.memory[address] = x;
                or.orFromRegisterMemory(dest_opcode, src_opcode);
            } else { 
                short opcode = 4; 
                short dest_opcode = reg.registerToOpcode(op1);
                short src_opcode = reg.registerToOpcode(op2);
                RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
                or.orFromRegisterRegister(dest_opcode, src_opcode);
            }
        }
    }

// mul = 5
    public void mul(int address, String op1, String op2, String filePath) throws Exception {
        if (op1.startsWith("[")) { 
            if (isInteger(op2)) { 
                short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
                if (dest_address < m.memoryStartAddress(filePath)) {
                    throw new Exception("Invalid memory exception");
                }
                short opcode = 5; 
                int src_opcode = Integer.parseInt(op2);
                RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_opcode);
                RAM.memory[dest_address] *= (short) src_opcode;
            } else if (op2.startsWith("[")) { 
                short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
                if (dest_address < m.memoryStartAddress(filePath)) {
                    throw new Exception("Invalid memory exception");
                }
                short opcode = 5; 
                int src_address = Integer.parseInt(op2.substring(1, op2.length() - 1));
                RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_address);
                RAM.memory[dest_address] *= RAM.memory[src_address];
            } else { 
                short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
                if (dest_address < m.memoryStartAddress(filePath)) {
                    throw new Exception("Invalid memory exception");
                }
                short opcode = 5; 
                short src_opcode = (short) (reg.registerToOpcode(op2));
                RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_opcode);
                mul.mulFromMemoryRegister(dest_address, src_opcode);
            }
        } else { 
            if (isInteger(op2)) { 
                short opcode = 5; 
                short dest_opcode = reg.registerToOpcode(op1);
                short src_opcode = Short.parseShort(op2);
                RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
                mul.mulFromRegisterInt(dest_opcode, src_opcode);
            } else if (op2.startsWith("[")) {
                short opcode = 5; 
                short dest_opcode = reg.registerToOpcode(op1);
                short src_opcode = Short.parseShort(op2.substring(1, op2.length() - 1));
                RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
                mul.mulFromRegisterMemory(dest_opcode, src_opcode);
            } else { 
                short opcode = 5; 
                short dest_opcode = reg.registerToOpcode(op1);
                short src_opcode = reg.registerToOpcode(op2);
                RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
                mul.mulFromRegisterRegister(dest_opcode, src_opcode);
            }
        }
    }

// sub = 6
    public void sub(int address, String op1, String op2, String filePath) throws Exception {
        if (op1.startsWith("[")) { 
            if (isInteger(op2)) { 
                short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
                if (dest_address < m.memoryStartAddress(filePath)) {
                    throw new Exception("Invalid memory exception");
                }
                short opcode = 6; 
                int src_opcode = Integer.parseInt(op2);
                RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_opcode);
                RAM.memory[dest_address] -= (short) src_opcode;
            } else if (op2.startsWith("[")) { 
                short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
                if (dest_address < m.memoryStartAddress(filePath)) {
                    throw new Exception("Invalid memory exception");
                }
                short opcode = 6; 
                int src_address = Integer.parseInt(op2.substring(1, op2.length() - 1));
                RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_address);
                RAM.memory[dest_address] -= RAM.memory[src_address];
            } else { 
                short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
                if (dest_address < m.memoryStartAddress(filePath)) {
                    throw new Exception("Invalid memory exception");
                }
                short opcode = 6; 
                short src_opcode = (short) (reg.registerToOpcode(op2));
                RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_opcode);
                sub.subtractFromMemoryRegister(dest_address, src_opcode);
            }
        } else { 
            if (isInteger(op2)) { 
                short opcode = 6; 
                short dest_opcode = reg.registerToOpcode(op1);
                short src_opcode = Short.parseShort(op2);
                RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
                sub.subtractFromRegisterInt(dest_opcode, src_opcode);
            } else if (op2.startsWith("[")) { 
                short opcode = 6; 
                short dest_opcode = reg.registerToOpcode(op1);
                short src_opcode = Short.parseShort(op2.substring(1, op2.length() - 1));
                RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
                sub.subtractFromRegisterMemory(dest_opcode, src_opcode);
            } else { 
                short opcode = 6; 
                short dest_opcode = reg.registerToOpcode(op1);
                short src_opcode = reg.registerToOpcode(op2);
                RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
                sub.subtractFromRegisterRegister(dest_opcode, src_opcode);
            }
        }
    }

// add = 7
   public void add(int address, String op1, String op2, String filePath) throws Exception {
        
        if (op1.startsWith("[")) {
          add.handleMemoryOperand(op1, op2, address, filePath);
        } else {
            add.handleRegisterOperand(op1, op2, address, filePath);
        }
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
