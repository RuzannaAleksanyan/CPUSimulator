package arithmethic;

import memory.RAM;
import memory.Registers;

public class CMP {
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

    public void compareMemoryInt(short dest_address, short src_opcode) {
        short dest_value = RAM.memory[dest_address];
        short result = (short) (dest_value - src_opcode);
        reg.setDa(result);
        flag(result);
    }

    public void compareMemoryMemory(short dest_address, short src_address) {
        short dest_value = RAM.memory[dest_address];
        short src_value = RAM.memory[src_address];
        short result = (short)(dest_value - src_value);
        reg.setDa(result);
        flag(result);
    }

    public void compareMemoryRegister(short dest_address, short src_opcode) {
        short x = 0;
        switch (src_opcode) {
            case 0:
                x = (short) (RAM.memory[dest_address] - reg.getAyb());
                reg.setDa(x);
                flag(x);
                break;
            case 1:
                x = (short) (RAM.memory[dest_address] - (short) reg.getBen());
                reg.setDa(x);
                flag(x);
                break;
            case 2:
                x = (short) (RAM.memory[dest_address] - (short) reg.getGim());
                reg.setDa(x);
                flag(x);
                break;
            case 3:
                x = (short) (RAM.memory[dest_address] - (short) reg.getDa());
                reg.setDa(x);
                flag(x);
                break;
            case 4:
                x = (short) (RAM.memory[dest_address] - (short) reg.getEch());
                reg.setDa(x);
                flag(x);
                break;
            case 5:
                x = (short) (RAM.memory[dest_address] - (short) reg.getZa());
                reg.setDa(x);
                flag(x);
                break;
            default:
                System.out.println("Invalid register");
        }
    }

    public void compareRegisterInt(short dest_opcode, short src_opcode) {
        short x = 0;
        switch (dest_opcode) {
            case 0:
                x = (short) (reg.getAyb() - src_opcode);
                reg.setDa(x);
                flag(x);
                break;
            case 1:
                x = (short) (reg.getBen() - src_opcode);
                reg.setDa(x);
                flag(x);
                break;
            case 2:
                x = (short) (reg.getGim() - src_opcode);
                reg.setDa(x);
                flag(x);
                break;
            case 3:
                x = (short) (reg.getDa() - src_opcode);
                reg.setDa(x);
                flag(x);
                break;
            case 4:
                x = (short) (reg.getEch() - src_opcode);
                reg.setDa(x);
                flag(x);
                break;
            case 5:
                x = (short) (reg.getZa() - src_opcode);
                reg.setDa(x);
                flag(x);
                break;
            default:
                System.out.println("Invalid register");
        }
    }

    public void compareRegisterMemory(short dest_opcode, short src_opcode) {
        short x = 0;
        switch (dest_opcode) {
            case 0:
                x = (short) (reg.getAyb() - RAM.memory[src_opcode]);
                reg.setDa(x);
                flag(x);
                break;
            case 1:
                x = (short) (reg.getBen() - RAM.memory[src_opcode]);
                reg.setDa(x);
                flag(x);
                break;
            case 2:
                x = (short) (reg.getGim() - RAM.memory[src_opcode]);
                reg.setDa(x);
                flag(x);
                break;
            case 3:
                x = (short) (reg.getDa() - RAM.memory[src_opcode]);
                reg.setDa(x);
                flag(x);
                break;
            case 4:
                x = (short) (reg.getEch() - RAM.memory[src_opcode]);
                reg.setDa(x);
                flag(x);
                break;
            case 5:
                x = (short) (reg.getZa() - RAM.memory[src_opcode]);
                reg.setDa(x);
                flag(x);
                break;
            default:
                System.out.println("Invalid register");
        }
    }

    public void compareFromRegisterRegister(short dest_opcode, short src_opcode) {
        short x = 0;
        if (dest_opcode == 0 && src_opcode == 0) {
            x = (short) (reg.getAyb() - reg.getAyb());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 0 && src_opcode == 1) {
            x = (short) (reg.getAyb() - reg.getBen());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 0 && src_opcode == 2) {
            x = (short) (reg.getAyb() - reg.getGim());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 0 && src_opcode == 3) {
            x = (short) (reg.getAyb() - reg.getDa());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 0 && src_opcode == 4) {
            x = (short) (reg.getAyb() - reg.getEch());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 0 && src_opcode == 5) {
            x = (short) (reg.getAyb() - reg.getZa());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 1 && src_opcode == 0) {
            x = (short) (reg.getBen() - reg.getAyb());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 1 && src_opcode == 1) {
            x = (short) (reg.getBen() - reg.getBen());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 1 && src_opcode == 2) {
            x = (short) (reg.getBen() - reg.getGim());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 1 && src_opcode == 3) {
            x = (short) (reg.getBen() - reg.getDa());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 1 && src_opcode == 4) {
            x = (short) (reg.getBen() - reg.getEch());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 1 && src_opcode == 5) {
            x = (short) (reg.getBen() - reg.getZa());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 2 && src_opcode == 0) {
            x = (short) (reg.getGim() - reg.getAyb());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 2 && src_opcode == 1) {
            x = (short) (reg.getGim() - reg.getBen());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 2 && src_opcode == 2) {
            x = (short) (reg.getGim() - reg.getGim());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 2 && src_opcode == 3) {
            x = (short) (reg.getGim() - reg.getDa());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 2 && src_opcode == 4) {
            x = (short) (reg.getGim() - reg.getEch());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 2 && src_opcode == 5) {
            x = (short) (reg.getGim() - reg.getZa());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 3 && src_opcode == 0) {
            x = (short) (reg.getDa() - reg.getAyb());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 3 && src_opcode == 1) {
            x = (short) (reg.getDa() - reg.getBen());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 3 && src_opcode == 2) {
            x = (short) (reg.getDa() - reg.getGim());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 3 && src_opcode == 3) {
            x = (short) (reg.getDa() - reg.getDa());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 3 && src_opcode == 4) {
            x = (short) (reg.getDa() - reg.getEch());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 3 && src_opcode == 5) {
            x = (short) (reg.getDa() - reg.getZa());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 4 && src_opcode == 0) {
            x = (short) (reg.getEch() - reg.getAyb());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 4 && src_opcode == 1) {
            x = (short) (reg.getEch() - reg.getBen());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 4 && src_opcode == 2) {
            x = (short) (reg.getEch() - reg.getGim());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 4 && src_opcode == 3) {
            x = (short) (reg.getEch() - reg.getDa());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 4 && src_opcode == 4) {
            x = (short) (reg.getEch() - reg.getEch());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 4 && src_opcode == 5) {
            x = (short) (reg.getEch() - reg.getZa());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 5 && src_opcode == 0) {
            x = (short) (reg.getZa() - reg.getAyb());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 5 && src_opcode == 1) {
            x = (short) (reg.getZa() - reg.getBen());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 5 && src_opcode == 2) {
            x = (short) (reg.getZa() - reg.getGim());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 5 && src_opcode == 3) {
            x = (short) (reg.getZa() - reg.getDa());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 5 && src_opcode == 4) {
            x = (short) (reg.getZa() - reg.getEch());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 5 && src_opcode == 5) {
            x = (short) (reg.getZa() - reg.getZa());
            reg.setDa(x);
            flag(x);
        } else {
            System.out.println("Invalid register");
        }
    }

}
