package controler;

import memory.RAM;
import memory.Registers;

public class CU {
    public Registers reg = new Registers();
    public RAM m = new RAM();

  
       public void mov(int address, String op1, String op2, String filePath) throws Exception {
        
        if (op1.startsWith("[")) {
            handleMemoryOperand(op1, op2, address, filePath);
        } else {
            handleRegisterOperand(op1, op2, address, filePath);
        }
    }

    private void handleMemoryOperand(String op1, String op2, int address, String filePath) throws Exception {
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
        RAM.memory[dest_address] = (short) src_opcode;
    }

    private void handleMemoryMemory(String op1, String op2, int address, String filePath) throws Exception {
        short dest_address = Short.parseShort(op1.substring(1, op1.length() - 1));
        if (dest_address < m.memoryStartAddress(filePath)) {
            throw new Exception("Invalid memory exception");
        }
        
        short opcode = 7;
        int src_address = Integer.parseInt(op2.substring(1, op2.length() - 1));
        RAM.memory[address] = (short) ((opcode << 12) | (dest_address << 6) | src_address);
        RAM.memory[dest_address] = RAM.memory[src_address];
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
                RAM.memory[dest_address] = (short) reg.getAyb();
                break;
            case 1:
                RAM.memory[dest_address] = (short) reg.getBen();
                break;
            case 2:
                RAM.memory[dest_address] = (short) reg.getGim();
                break;
            case 3:
                RAM.memory[dest_address] = (short) reg.getDa();
                break;
            case 4:
                RAM.memory[dest_address] = (short) reg.getEch();
                break;
            case 5:
                RAM.memory[dest_address] = (short) reg.getZa();
                break;
            default:
                System.out.println("Invalid register");
        }
    }

    private void handleRegisterOperand(String op1, String op2, int address, String filePath) {
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

        switch (dest_opcode) {
            case 0:
                reg.setAyb(src_opcode);
                break;
            case 1:
                reg.setBen(src_opcode);
                break;
            case 2:
                reg.setGim(src_opcode);
                break;
            case 3:
                reg.setDa(src_opcode);
                break;
            case 4:
                reg.setEch(src_opcode);
                break;
            case 5:
                reg.setZa(src_opcode);
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
                reg.setAyb(RAM.memory[src_opcode]);
                break;
            case 1:
                reg.setBen(RAM.memory[src_opcode]);
                break;
            case 2:
                reg.setGim(RAM.memory[src_opcode]);
                break;
            case 3:
                reg.setDa(RAM.memory[src_opcode]);
                break;
            case 4:
                reg.setEch(RAM.memory[src_opcode]);
                break;
            case 5:
                reg.setZa(RAM.memory[src_opcode]);
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
            reg.setAyb(reg.getAyb());
        } else if (dest_opcode == 0 && src_opcode == 1) {
            reg.setAyb(reg.getBen());
        } else if (dest_opcode == 0 && src_opcode == 2) {
            reg.setAyb(reg.getGim());
        } else if (dest_opcode == 0 && src_opcode == 3) {
            reg.setAyb(reg.getDa());
        } else if (dest_opcode == 0 && src_opcode == 4) {
            reg.setAyb(reg.getEch());
        } else if (dest_opcode == 0 && src_opcode == 5) {
            reg.setAyb(reg.getZa());
        } else if (dest_opcode == 1 && src_opcode == 0) {
            reg.setBen(reg.getAyb());
        } else if (dest_opcode == 1 && src_opcode == 1) {
            reg.setBen(reg.getBen());
        } else if (dest_opcode == 1 && src_opcode == 2) {
            reg.setBen(reg.getGim());
        } else if (dest_opcode == 1 && src_opcode == 3) {
            reg.setBen(reg.getDa());
        } else if (dest_opcode == 1 && src_opcode == 4) {
            reg.setBen(reg.getEch());
        } else if (dest_opcode == 1 && src_opcode == 5) {
            reg.setBen(reg.getZa());
        } else if (dest_opcode == 1 && src_opcode == 0) {
            reg.setBen(reg.getAyb());
        } else if (dest_opcode == 2 && src_opcode == 0) {
            reg.setGim(reg.getAyb());
        } else if (dest_opcode == 2 && src_opcode == 1) {
            reg.setGim(reg.getBen());
        } else if (dest_opcode == 2 && src_opcode == 2) {
            reg.setGim(reg.getGim());
        } else if (dest_opcode == 2 && src_opcode == 3) {
            reg.setGim(reg.getDa());
        } else if (dest_opcode == 2 && src_opcode == 4) {
            reg.setGim(reg.getEch());
        } else if (dest_opcode == 2 && src_opcode == 5) {
            reg.setGim(reg.getZa());
        } else if (dest_opcode == 3 && src_opcode == 0) {
            reg.setDa(reg.getAyb());
        } else if (dest_opcode == 3 && src_opcode == 1) {
            reg.setDa(reg.getBen());
        } else if (dest_opcode == 3 && src_opcode == 2) {
            reg.setDa(reg.getGim());
        } else if (dest_opcode == 3 && src_opcode == 3) {
            reg.setDa(reg.getDa());
        } else if (dest_opcode == 3 && src_opcode == 4) {
            reg.setDa(reg.getEch());
        } else if (dest_opcode == 3 && src_opcode == 5) {
            reg.setDa(reg.getZa());
        } else if (dest_opcode == 4 && src_opcode == 0) {
            reg.setEch(reg.getAyb());
        } else if (dest_opcode == 4 && src_opcode == 1) {
            reg.setEch(reg.getBen());
        } else if (dest_opcode == 4 && src_opcode == 2) {
            reg.setEch(reg.getGim());
        } else if (dest_opcode == 4 && src_opcode == 3) {
            reg.setEch(reg.getDa());
        } else if (dest_opcode == 4 && src_opcode == 4) {
            reg.setEch(reg.getEch());
        } else if (dest_opcode == 4 && src_opcode == 5) {
            reg.setEch(reg.getZa());
        } else if (dest_opcode == 5 && src_opcode == 0) {
            reg.setZa(reg.getAyb());
        } else if (dest_opcode == 5 && src_opcode == 1) {
            reg.setZa(reg.getBen());
        } else if (dest_opcode == 5 && src_opcode == 2) {
            reg.setZa(reg.getGim());
        } else if (dest_opcode == 5 && src_opcode == 3) {
            reg.setZa(reg.getDa());
        } else if (dest_opcode == 5 && src_opcode == 4) {
            reg.setZa(reg.getEch());
        } else if (dest_opcode == 5 && src_opcode == 5) {
            reg.setZa(reg.getZa());
        } else {
            System.out.println("Invalid register");
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
