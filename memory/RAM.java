package memory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RAM {

    private static final int MEMORY_SIZE = 32;
    public static short[] memory = new short[MEMORY_SIZE];

    public int getMemorySize() { return MEMORY_SIZE; }

    public static String calculateBinary(short number) {
        StringBuilder binary = new StringBuilder();
        
        for (int i = 15; i >= 0; i--) {
            int mask = 1 << i;
            int bit = (number & mask) != 0 ? 1 : 0;
            binary.append(bit);
        }
        
        return binary.toString();
    }

    public void dumpMemory() {
        for (int i = 0; i < MEMORY_SIZE; i++) {
            String binaryString = String.format("%16s", calculateBinary(memory[i])).replace(' ', '0');
            System.out.println("[" + i + "]: " + binaryString);
        }
    }


    public short memoryStartAddress(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        short rowCount = 0;

        while (reader.readLine() != null) {
            rowCount++;
        }

        reader.close();
        return rowCount;
    }

    public void writeMemory(int address, byte value) {
        if (address >= 0 && address < MEMORY_SIZE) {
            memory[address] = value;
        } else {
            System.out.println("Invalid memory address!");
        }
    }

}
