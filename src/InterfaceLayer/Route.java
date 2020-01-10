package InterfaceLayer;

import Utils.Enums.Instructions;

import java.util.ArrayList;

public class Route {

    private ArrayList<Instructions> instructions;
    private int instructionCounter;

    /**
     * simple constructor that sets the instruction counter to 0 and initializes a new arrayList where we keep the instructions stored
     */
    public Route() {
        this.instructionCounter = 0;
        this.instructions = new ArrayList<>();
    }

    /**
     * simple getter for the list of instructions
     *
     * @return ArrayList with instructions
     */
    public ArrayList<Instructions> getInstructions() {
        return instructions;
    }

    /**
     * this method sets the arrayList of instructions and resets the instructionCounter
     *
     * @param instructions ArrayList of Instructions Enums
     */
    public void setInstructions(ArrayList<Instructions> instructions) {
        this.instructions.addAll(instructions);
        this.instructionCounter = 0;
    }

    /**
     * this method will get an instruction on what to do at an intersection
     * if there is an available instruction it will return that one else it will idle at the intersection
     *
     * @return a single instruction from enums Instructions
     */
    public Instructions getInstruction() {
        Instructions instruction;

        try {
            instruction = this.instructions.get(this.instructionCounter);
        } catch (NullPointerException nullPointer) {
            instruction = Instructions.None;
        }

        this.instructionCounter++;
        return instruction;
    }

}
