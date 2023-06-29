import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.MediaSize.NA;

import memory.Registers;
import arithmethic.ALU;
import controller.CU;
import memory.RAM;

public class CPU {    
    public ALU alu = new ALU();
    public CU cu = new CU();
    // public CPU cpu = new CPU();
    public static RAM m = new RAM();
    public static Registers reg = new Registers();

    public static void main(String[] args) 
    {
        String filePath = "file.txt";
        CPU cpu = new CPU();
        try 
        {
            String[] fileContent = {readFileToString(filePath)};
            cpu.loadProgram(filePath);
            // System.out.println(fileContent[0]);
            m.dumpMemory();

            reg.displeyRegisters();
        

        } 
        catch (Exception e) 
        {
            // System.err.println("Error reading the file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String readFileToString(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] fileBytes = Files.readAllBytes(path);
        return new String(fileBytes);
    }


    public Map<String, Integer> lable(String[] str)
    {
        String[] lines = str[0].split("\n");
        int length = lines.length;
        Map<String, Integer> map = new HashMap<>();
        int x = 0;
        for(Integer i = 0; i < length; ++i)
        {
            if(lines[i].contains(":"))
            {
                x = lines[i].indexOf(":");
                lines[i] = lines[i].trim();
                map.put(lines[i].substring(0, x), i);
            }
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String lbl = entry.getKey();
            str[0] = str[0].replace(lbl + ":", "");
            str[0] = str[0].trim();
        }
        return map;
    }


    public void loadProgram(String filePath) throws IOException {
        String[] reader = {readFileToString(filePath)};

        Map<String, Integer> lables = lable(reader); 
    
        String[] lines = reader[0].split("\n");

        for(int i = 0; i < lines.length; ++i)
        {
            lines[i] = lines[i].trim();
        }
      
        short address = reg.getProgremmStartAddress();
        int i = 0;
        while (i != lines.length) {
            lines[i] = lines[i].trim();
            if (!lines[i].isEmpty()) {
                String newline = lines[i].replace(", ", " ");
                String[] tokens = newline.split(" ");
                String instruction = "";
                String op1 = "";
                String op2 = "";
                if(tokens.length == 3)
                {
                    instruction = tokens[0];
                    op1 = tokens[1];
                    op2 = tokens[2];
                } else {
                    instruction = tokens[0];
                    op1 = tokens[1];
                }
                
                if (instruction.equals("mov")) {
                    try{
                        cu.mov(address, op1, op2, filePath);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (instruction.equals("add")) {
                    try{
                        alu.add(address, op1, op2, filePath); 
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (instruction.equals("sub")) {
                    try{
                        alu.sub(address, op1, op2, filePath); 
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (instruction.equals("mul")) {
                    try{
                        alu.mul(address, op1, op2, filePath); 
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (instruction.equals("div")) {
                    try{
                        alu.div(address, op1, op2, filePath); 
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (instruction.equals("and")) {
                    try{
                        alu.and(address, op1, op2, filePath); 
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (instruction.equals("or")) {
                    try{
                        alu.or(address, op1, op2, filePath); 
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (instruction.equals("not")) {
                    try{
                        alu.not(address, op1, filePath); 
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (instruction.equals("cmp")) {
                    try{
                        alu.cmp(address, op1, op2, filePath); 
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (instruction.equals("jmp")) {
                    if(lables.get(op1) != null)
                    {
                        i = lables.get(op1) - 1;
                    } else {
                        throw new RuntimeException("Invalid Lable ");
                    }    
                } else if (instruction.equals("jg")) {
                    if(reg.getDa() > 0)
                    {
                        if(lables.get(op1) != null)
                        {
                            i = lables.get(op1) - 1;
                        } else {
                            throw new RuntimeException("Invalid Lable ");
                        }
                    } 
                } else if (instruction.equals("jl")) {
                    if(reg.getDa() < 0)
                    {
                        if(lables.get(op1) != null)
                        {
                            i = lables.get(op1) - 1;
                        } else {
                            throw new RuntimeException("Invalid Lable ");
                        }
                    }
                } else if (instruction.equals("je")) {
                    if(reg.getDa() ==  0)
                    {
                        if(lables.get(op1) != null)
                        {
                            i = lables.get(op1) - 1;
                        } else {
                            throw new RuntimeException("Invalid Lable ");
                        }
                    }
                    System.out.println("3");
                } else {
                    System.out.println("Invalid instruction: " + instruction);
                    throw new RuntimeException("Invalid instruction: " + instruction);
                }
                ++i;
                address++;
                reg.setGh(address);
            }
        }
    }
}
