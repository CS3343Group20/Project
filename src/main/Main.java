package main;
import java.text.ParseException;
import controlSystem.CMS;
import simulator.Simulator;

//Exceptions
import exceptions.InsufficientArgumentException;
public class Main {
	
	public static void main(String[] args) throws ParseException, InsufficientArgumentException {	
		CMS cms= CMS.getInstance(); // create a cms instance
		cms.createLift(1000); // create a lift with 1000 KG capacity
		cms.createLift(1000); // create a lift with 1000 KG capacity
        Simulator sim=new Simulator();
        FileReader fr= new FileReader();
        fr.readFile("../src/input.txt");
        sim.StartSimulation(cms.getBuilding(),fr.getInputList());
    }
}
