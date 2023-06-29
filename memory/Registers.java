package memory;
public class Registers {
    RAM r = new RAM();
    private static final int PROGRAM_SIZE = 32;
    private static final short PROGRAM_START_ADDRESS = 0;

    private static short ayb = 0;
    private static short ben = 0;
    private static short gim = 0;
    private static short da = 0;
    private static short ech = 0;
    private static short za = 0;
    private static short gh = 0;


    public short registerToOpcode(String register) {
        switch (register) {
            case "ayb":
                return 0;
            case "ben":
                return 1;
            case "gim":
                return 2;
            case "da":
                return 3;
            case "ech":
                return 4;
            case "za":
                return 5;
            default:
                throw new IllegalArgumentException("Invalid register: " + register);
        }
    }

    public void setAyb(short a) { ayb = a; }
    public void setBen(short b) { ben = b; }
    public void setGim(short g) { gim = g; }
    public void setDa(short d)  { da = d;  }
    public void setEch(short e) { ech = e; }
    public void setZa(short z)  { za = z;  }
    public void setGh(short g)  { gh = g;  }

    public short getAyb() { return ayb; }
    public short getBen() { return ben; }
    public short getGim() { return gim; }
    public short getDa()  { return da;  }
    public short getEch() { return ech; }
    public short getZa()  { return za;  }
    public short getGh()  { return gh;  }
    public short getProgremmStartAddress() { return PROGRAM_START_ADDRESS; }

    public void displeyRegisters()
    {
        System.out.println("AYB" + " = " + getAyb());
        System.out.println("BEN" + " = " + getBen());
        System.out.println("GIM" + " = " + getGim());
        System.out.println("DA" + "  = " + getDa());
        System.out.println("ECH" + " = " + getEch());
        System.out.println("ZA" + "  = " + getZa());
        System.out.println("GH" + "  = " + getGh());
    }
}
