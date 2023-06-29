package arithmethic;

import memory.RAM;
import memory.Registers;

public class AND {
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

    public void andFromMemoryRegister(short dest_address, short src_opcode) {
        short x = 0;
        switch (src_opcode) {
            case 0:
                x = (short) (RAM.memory[dest_address] & (short) reg.getAyb());
                RAM.memory[dest_address] = x;
                flag(x);
                break;
            case 1:
                x = (short) (RAM.memory[dest_address] & (short) reg.getBen());
                RAM.memory[dest_address] = x;
                flag(x);
                break;
            case 2:
                x = (short) (RAM.memory[dest_address] & (short) reg.getGim());
                RAM.memory[dest_address] = x;
                flag(x);
                break;
            case 3:
                x = (short) (RAM.memory[dest_address] & (short) reg.getDa());
                RAM.memory[dest_address] = x;
                flag(x);
                break;
            case 4:
                x = (short) (RAM.memory[dest_address] & (short) reg.getEch());
                RAM.memory[dest_address] = x;
                flag(x);
                break;
            case 5:
                x = (short) (RAM.memory[dest_address] & (short) reg.getZa());
                RAM.memory[dest_address] = x;
                flag(x);
                break;
            default:
                System.out.println("Invalid register");
        }
    }

    public void andFromRegisterInt(short dest_opcode, short src_opcode) {
        short x = 0;
        switch (dest_opcode) {
            case 0:
                x = (short) (reg.getAyb() & src_opcode);
                reg.setAyb(x);
                flag(x);
                break;
            case 1:
                x = (short) (reg.getBen() & src_opcode);
                reg.setBen(x);
                flag(x);
                break;
            case 2:
                x = (short) (reg.getGim() & src_opcode);
                reg.setGim(x);
                flag(x);
                break;
            case 3:
                x = (short) (reg.getDa() & src_opcode);
                reg.setDa(x);
                flag(x);
                break;
            case 4:
                x = (short) (reg.getEch() & src_opcode);
                reg.setEch(x);
                flag(x);
                break;
            case 5:
                x = (short) (reg.getZa() & src_opcode);
                reg.setZa(x);
                flag(x);
                break;
            default:
                System.out.println("Invalid register");
        }
    }

    public void andFromRegisterMemory(short dest_opcode, short src_opcode) {
        short x = 0;
        switch (dest_opcode) {
            case 0:
                x = (short) (reg.getAyb() & RAM.memory[src_opcode]);
                reg.setAyb(x);
                flag(x);
                break;
            case 1:
                x = (short) (reg.getBen() & RAM.memory[src_opcode]);
                reg.setBen(x);
                flag(x);
                break;
            case 2:
                x = (short) (reg.getGim() & RAM.memory[src_opcode]);
                reg.setGim(x);
                flag(x);
                break;
            case 3:
                x = (short) (reg.getDa() & RAM.memory[src_opcode]);
                reg.setDa(x);
                flag(x);
                break;
            case 4:
                x = (short) (reg.getEch() & RAM.memory[src_opcode]);
                reg.setEch(x);
                flag(x);
                break;
            case 5:
                x = (short) (reg.getZa() & RAM.memory[src_opcode]);
                reg.setZa(x);
                flag(x);
                break;
            default:
                System.out.println("Invalid register");
        }
    }


    public void andFromRegisterRegister(short dest_opcode, short src_opcode) {
        short x = 0;
        if (dest_opcode == 0 && src_opcode == 0) {
            x = (short) (reg.getAyb() & reg.getAyb());
            reg.setAyb(x);
            flag(x);
        } else if (dest_opcode == 0 && src_opcode == 1) {
            x = (short) (reg.getAyb() & reg.getBen());
            reg.setAyb(x);
            flag(x);
        } else if (dest_opcode == 0 && src_opcode == 2) {
            x = (short) (reg.getAyb() & reg.getGim());
            reg.setAyb(x);
            flag(x);
        } else if (dest_opcode == 0 && src_opcode == 3) {
            x = (short) (reg.getAyb() & reg.getDa());
            reg.setAyb(x);
            flag(x);
        } else if (dest_opcode == 0 && src_opcode == 4) {
            x = (short) (reg.getAyb() & reg.getEch());
            reg.setAyb(x);
            flag(x);
        } else if (dest_opcode == 0 && src_opcode == 5) {
            x = (short) (reg.getAyb() & reg.getZa());
            reg.setAyb(x);
            flag(x);
        } else if (dest_opcode == 1 && src_opcode == 0) {
            x = (short) (reg.getBen() & reg.getAyb());
            reg.setBen(x);
            flag(x);
        } else if (dest_opcode == 1 && src_opcode == 1) {
            x = (short) (reg.getBen() & reg.getBen());
            reg.setBen(x);
            flag(x);
        } else if (dest_opcode == 1 && src_opcode == 2) {
            x = (short) (reg.getBen() & reg.getGim());
            reg.setBen(x);
            flag(x);
        } else if (dest_opcode == 1 && src_opcode == 3) {
            x = (short) (reg.getBen() & reg.getDa());
            reg.setBen(x);
            flag(x);
        } else if (dest_opcode == 1 && src_opcode == 4) {
            x = (short) (reg.getBen() & reg.getEch());
            reg.setBen(x);
            flag(x);
        } else if (dest_opcode == 1 && src_opcode == 5) {
            x = (short) (reg.getBen() & reg.getZa());
            reg.setBen(x);
            flag(x);
        } else if (dest_opcode == 2 && src_opcode == 0) {
            x = (short) (reg.getGim() & reg.getAyb());
            reg.setGim(x);
            flag(x);
        } else if (dest_opcode == 2 && src_opcode == 1) {
            x = (short) (reg.getGim() & reg.getBen());
            reg.setGim(x);
            flag(x);
        } else if (dest_opcode == 2 && src_opcode == 2) {
            x = (short) (reg.getGim() & reg.getGim());
            reg.setGim(x);
            flag(x);
        } else if (dest_opcode == 2 && src_opcode == 3) {
            x = (short) (reg.getGim() & reg.getDa());
            reg.setGim(x);
            flag(x);
        } else if (dest_opcode == 2 && src_opcode == 4) {
            x = (short) (reg.getGim() & reg.getEch());
            reg.setGim(x);
            flag(x);
        } else if (dest_opcode == 2 && src_opcode == 5) {
            x = (short) (reg.getGim() & reg.getZa());
            reg.setGim(x);
            flag(x);
        } else if (dest_opcode == 3 && src_opcode == 0) {
            x = (short) (reg.getDa() & reg.getAyb());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 3 && src_opcode == 1) {
            x = (short) (reg.getDa() & reg.getBen());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 3 && src_opcode == 2) {
            x = (short) (reg.getDa() & reg.getGim());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 3 && src_opcode == 3) {
            x = (short) (reg.getDa() & reg.getDa());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 3 && src_opcode == 4) {
            x = (short) (reg.getDa() & reg.getEch());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 3 && src_opcode == 5) {
            x = (short) (reg.getDa() & reg.getZa());
            reg.setDa(x);
            flag(x);
        } else if (dest_opcode == 4 && src_opcode == 0) {
            x = (short) (reg.getEch() & reg.getAyb());
            reg.setEch(x);
            flag(x);
        } else if (dest_opcode == 4 && src_opcode == 1) {
            x = (short) (reg.getEch() & reg.getBen());
            reg.setEch(x);
            flag(x);
        } else if (dest_opcode == 4 && src_opcode == 2) {
            x = (short) (reg.getEch() & reg.getGim());
            reg.setEch(x);
            flag(x);
        } else if (dest_opcode == 4 && src_opcode == 3) {
            x = (short) (reg.getEch() & reg.getDa());
            reg.setEch(x);
            flag(x);
        } else if (dest_opcode == 4 && src_opcode == 4) {
            x = (short) (reg.getEch() & reg.getEch());
            reg.setEch(x);
            flag(x);
        } else if (dest_opcode == 4 && src_opcode == 5) {
            x = (short) (reg.getEch() & reg.getZa());
            reg.setEch(x);
            flag(x);
        } else if (dest_opcode == 5 && src_opcode == 0) {
            x = (short) (reg.getZa() & reg.getAyb());
            reg.setZa(x);
            flag(x);
        } else if (dest_opcode == 5 && src_opcode == 1) {
            x = (short) (reg.getZa() & reg.getBen());
            reg.setZa(x);
            flag(x);
        } else if (dest_opcode == 5 && src_opcode == 2) {
            x = (short) (reg.getZa() & reg.getGim());
            reg.setZa(x);
            flag(x);
        } else if (dest_opcode == 5 && src_opcode == 3) {
            x = (short) (reg.getZa() & reg.getDa());
            reg.setZa(x);
            flag(x);
        } else if (dest_opcode == 5 && src_opcode == 4) {
            x = (short) (reg.getZa() & reg.getEch());
            reg.setZa(x);
            flag(x);
        } else if (dest_opcode == 5 && src_opcode == 5) {
            x = (short) (reg.getZa() & reg.getZa());
            reg.setZa(x);
            flag(x);
        } else {
            System.out.println("Invalid register");
        }
    }

}
