package arithmethic;

import memory.RAM;
import memory.Registers;

public class ADD {
    public static RAM m = new RAM();
    public Registers reg = new Registers();

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

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void handleRegisterOperand(String op1, String op2, int address, String filePath) {
        if (isInteger(op2)) {
            handleRegisterInt(op1, op2, address);
        } else if (op2.startsWith("[")) {
            handleRegisterMemory(op1, op2, address);
        } else {
            handleRegisterRegister(op1, op2, address);
        }
    }

    private void handleRegisterInt(String op1, String op2, int address) {
        short opcode = 7;
        short dest_opcode = reg.registerToOpcode(op1);
        short src_opcode = Short.parseShort(op2);
        RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
        short x = 0;
        switch (dest_opcode) {
            case 0:
                x = (short) (reg.getAyb() + src_opcode);
                reg.setAyb(x);
                flag(x);
                break;
            case 1:
                x = (short) (reg.getBen() + src_opcode);
                reg.setBen(x);
                flag(x);
                break;
            case 2:
                x = (short) (reg.getGim() + src_opcode);
                reg.setGim(x);
                flag(x);
                break;
            case 3:
                x = (short) (reg.getDa() + src_opcode);
                reg.setDa(x);

                break;
            case 4:
                x = (short) (reg.getEch() + src_opcode);
                reg.setEch(x);
                flag(x);
                break;
            case 5:
                x = (short) (reg.getZa() + src_opcode);
                reg.setZa(x);
                flag(x);
                break;
            default:
                System.out.println("Invalid register");
        }
    }

    private void handleRegisterMemory(String op1, String op2, int address) {
        short opcode = 7;
        short dest_opcode = reg.registerToOpcode(op1);
        short src_opcode = Short.parseShort(op2.substring(1, op2.length() - 1));
        RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
        switch (dest_opcode) {
            case 0:
                reg.setAyb((short) (reg.getAyb() + RAM.memory[src_opcode]));
                flag((short) (reg.getAyb() + RAM.memory[src_opcode]));
                break;
            case 1:
                reg.setBen((short) (reg.getBen() + RAM.memory[src_opcode]));
                flag((short) (reg.getBen() + RAM.memory[src_opcode]));
                break;
            case 2:
                reg.setGim((short) (reg.getGim() + RAM.memory[src_opcode]));
                flag((short) (reg.getGim() + RAM.memory[src_opcode]));
                break;
            case 3:
                reg.setDa((short) (reg.getDa() + RAM.memory[src_opcode]));
                flag((short) (reg.getDa() + RAM.memory[src_opcode]));
                break;
            case 4:
                reg.setEch((short) (reg.getEch() + RAM.memory[src_opcode]));
                flag((short) (reg.getEch() + RAM.memory[src_opcode]));
                break;
            case 5:
                reg.setZa((short) (reg.getZa() + RAM.memory[src_opcode]));
                flag((short) (reg.getZa() + RAM.memory[src_opcode]));
                break;
            default:
                System.out.println("Invalid register");
        }
    }

    private void handleRegisterRegister(String op1, String op2, int address) {
        short opcode = 7;
        short dest_opcode = reg.registerToOpcode(op1);
        short src_opcode = reg.registerToOpcode(op2);
        RAM.memory[address] = (short) ((opcode << 12) | (dest_opcode << 6) | src_opcode);
        if (dest_opcode == 0 && src_opcode == 0) {
            reg.setAyb((short) (reg.getAyb() + reg.getAyb()));
            flag((short) (reg.getAyb() + reg.getAyb()));
        } else if (dest_opcode == 0 && src_opcode == 1) {
            reg.setAyb((short) (reg.getAyb() + reg.getBen()));
            flag((short) (reg.getAyb() + reg.getBen()));
        } else if (dest_opcode == 0 && src_opcode == 2) {
            reg.setAyb((short) (reg.getAyb() + reg.getGim()));
            flag((short) (reg.getAyb() + reg.getGim()));
        } else if (dest_opcode == 0 && src_opcode == 3) {
            reg.setAyb((short) (reg.getAyb() + reg.getDa()));
            flag((short) (reg.getAyb() + reg.getDa()));
        } else if (dest_opcode == 0 && src_opcode == 4) {
            reg.setAyb((short) (reg.getAyb() + reg.getEch()));
            flag((short) (reg.getAyb() + reg.getEch()));
        } else if (dest_opcode == 0 && src_opcode == 5) {
            reg.setAyb((short) (reg.getAyb() + reg.getZa()));
            flag((short) (reg.getAyb() + reg.getZa()));
        } else if (dest_opcode == 1 && src_opcode == 0) {
            reg.setBen((short) (reg.getBen() + reg.getAyb()));
            flag((short) (reg.getBen() + reg.getAyb()));
        } else if (dest_opcode == 1 && src_opcode == 1) {
            reg.setBen((short) (reg.getBen() + reg.getBen()));
            flag((short) (reg.getBen() + reg.getBen()));
        } else if (dest_opcode == 1 && src_opcode == 2) {
            reg.setBen((short) (reg.getBen() + reg.getGim()));
            flag((short) (reg.getBen() + reg.getGim()));
        } else if (dest_opcode == 1 && src_opcode == 3) {
            reg.setBen((short) (reg.getBen() + reg.getDa()));
            flag((short) (reg.getBen() + reg.getDa()));
        } else if (dest_opcode == 1 && src_opcode == 4) {
            reg.setBen((short) (reg.getBen() + reg.getEch()));
            flag((short) (reg.getBen() + reg.getEch()));
        } else if (dest_opcode == 1 && src_opcode == 5) {
            reg.setBen((short) (reg.getBen() + reg.getZa()));
            flag((short) (reg.getBen() + reg.getZa()));
        } else if (dest_opcode == 1 && src_opcode == 0) {
            reg.setBen((short) (reg.getBen() + reg.getAyb()));
            flag((short) (reg.getBen() + reg.getAyb()));
        } else if (dest_opcode == 2 && src_opcode == 0) {
            reg.setGim((short) (reg.getGim() + reg.getAyb()));
            flag((short) (reg.getGim() + reg.getAyb()));
        } else if (dest_opcode == 2 && src_opcode == 1) {
            reg.setGim((short) (reg.getGim() + reg.getBen()));
            flag((short) (reg.getGim() + reg.getBen()));
        } else if (dest_opcode == 2 && src_opcode == 2) {
            reg.setGim((short) (reg.getGim() + reg.getGim()));
            flag((short) (reg.getGim() + reg.getGim()));
        } else if (dest_opcode == 2 && src_opcode == 3) {
            reg.setGim((short) (reg.getGim() + reg.getDa()));
            flag((short) (reg.getGim() + reg.getDa()));
        } else if (dest_opcode == 2 && src_opcode == 4) {
            reg.setGim((short) (reg.getGim() + reg.getEch()));
            flag((short) (reg.getGim() + reg.getEch()));
        } else if (dest_opcode == 2 && src_opcode == 5) {
            reg.setGim((short) (reg.getGim() + reg.getZa()));
            flag((short) (reg.getGim() + reg.getZa()));
        } else if (dest_opcode == 3 && src_opcode == 0) {
            reg.setDa((short) (reg.getDa() + reg.getAyb()));
            flag((short) (reg.getDa() + reg.getAyb()));
        } else if (dest_opcode == 3 && src_opcode == 1) {
            reg.setDa((short) (reg.getDa() + reg.getBen()));
            flag((short) (reg.getDa() + reg.getBen()));
        } else if (dest_opcode == 3 && src_opcode == 2) {
            reg.setDa((short) (reg.getDa() + reg.getGim()));
            flag((short) (reg.getDa() + reg.getGim()));
        } else if (dest_opcode == 3 && src_opcode == 3) {
            reg.setDa((short) (reg.getDa() + reg.getDa()));
            flag((short) (reg.getDa() + reg.getDa()));
        } else if (dest_opcode == 3 && src_opcode == 4) {
            reg.setDa((short) (reg.getDa() + reg.getEch()));
            flag((short) (reg.getDa() + reg.getEch()));
        } else if (dest_opcode == 3 && src_opcode == 5) {
            reg.setDa((short) (reg.getDa() + reg.getZa()));
            flag((short) (reg.getDa() + reg.getZa()));
        } else if (dest_opcode == 4 && src_opcode == 0) {
            reg.setEch((short) (reg.getEch() + reg.getAyb()));
            flag((short) (reg.getEch() + reg.getAyb()));
        } else if (dest_opcode == 4 && src_opcode == 1) {
            reg.setEch((short) (reg.getEch() + reg.getBen()));
            flag((short) (reg.getEch() + reg.getBen()));
        } else if (dest_opcode == 4 && src_opcode == 2) {
            reg.setEch((short) (reg.getEch() + reg.getGim()));
            flag((short) (reg.getEch() + reg.getGim()));
        } else if (dest_opcode == 4 && src_opcode == 3) {
            reg.setEch((short) (reg.getEch() + reg.getDa()));
            flag((short) (reg.getEch() + reg.getDa()));
        } else if (dest_opcode == 4 && src_opcode == 4) {
            reg.setEch((short) (reg.getEch() + reg.getEch()));
            flag((short) (reg.getEch() + reg.getEch()));
        } else if (dest_opcode == 4 && src_opcode == 5) {
            reg.setEch((short) (reg.getEch() + reg.getZa()));
            flag((short) (reg.getEch() + reg.getZa()));
        } else if (dest_opcode == 5 && src_opcode == 0) {
            reg.setZa((short) (reg.getZa() + reg.getAyb()));
            flag((short) (reg.getZa() + reg.getAyb()));
        } else if (dest_opcode == 5 && src_opcode == 1) {
            reg.setZa((short) (reg.getZa() + reg.getBen()));
            flag((short) (reg.getZa() + reg.getBen()));
        } else if (dest_opcode == 5 && src_opcode == 2) {
            reg.setZa((short) (reg.getZa() + reg.getGim()));
            flag((short) (reg.getZa() + reg.getGim()));
        } else if (dest_opcode == 5 && src_opcode == 3) {
            reg.setZa((short) (reg.getZa() + reg.getDa()));
            flag((short) (reg.getZa() + reg.getDa()));
        } else if (dest_opcode == 5 && src_opcode == 4) {
            reg.setZa((short) (reg.getZa() + reg.getEch()));
            flag((short) (reg.getZa() + reg.getEch()));
        } else if (dest_opcode == 5 && src_opcode == 5) {
            reg.setZa((short) (reg.getZa() + reg.getZa()));
            flag((short) (reg.getZa() + reg.getZa()));
        } else {
            System.out.println("Invalid register");
        }
    }

    public void handleMemoryOperand(String op1, String op2, int address, String filePath) throws Exception {
        if (isInteger(op2)) {
            handleMemoryInt(op1, op2, address, filePath);
        } else if (op2.startsWith("[")) {
            handleMemoryMemory(op1, op2, address, filePath);
        } else {
            handleMemoryRegister(op1, op2, address, filePath);
        }
    }

    private void handleMemoryInt(String op1, String op2, int address, String filePath) throws Exception {
        short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
        if (dest_address < m.memoryStartAddress(filePath)) {
            throw new Exception("Invalid memory exception");
        }
        
        short opcode = 7;
        int src_opcode = Integer.parseInt(op2);
        RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_opcode);
        RAM.memory[dest_address] += (short) src_opcode;
        flag((short) (RAM.memory[dest_address] + (short) src_opcode));
    }

    private void handleMemoryMemory(String op1, String op2, int address, String filePath) throws Exception {
        short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
        if (dest_address < m.memoryStartAddress(filePath)) {
            throw new Exception("Invalid memory exception");
        }
        
        short opcode = 7;
        int src_address = Integer.parseInt(op2.substring(1, op2.length() - 1));
        RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_address);
        RAM.memory[dest_address] += RAM.memory[src_address];
        flag((short) (RAM.memory[dest_address] + RAM.memory[src_address]));
    }

    private void handleMemoryRegister(String op1, String op2, int address, String filePath) throws Exception {
        short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
        if (dest_address < m.memoryStartAddress(filePath)) {
            throw new Exception("Invalid memory exception");
        }
        
        short opcode = 7;
        short src_opcode = (short) (reg.registerToOpcode(op2));
        RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_opcode);

        switch (src_opcode) {
            case 0:
                RAM.memory[dest_address] += (short) reg.getAyb();
                flag(RAM.memory[dest_address]);
                break;
            case 1:
                RAM.memory[dest_address] += (short) reg.getBen();
                flag(RAM.memory[dest_address]);
                break;
            case 2:
                RAM.memory[dest_address] += (short) reg.getGim();
                flag(RAM.memory[dest_address]);
                break;
            case 3:
                RAM.memory[dest_address] += (short) reg.getDa();
                flag(RAM.memory[dest_address]);
                break;
            case 4:
                RAM.memory[dest_address] += (short) reg.getEch();
                flag(RAM.memory[dest_address]);
                break;
            case 5:
                RAM.memory[dest_address] += (short) reg.getZa();
                flag(RAM.memory[dest_address]);
                break;
            default:
                System.out.println("Invalid register");
        }
    }
}
