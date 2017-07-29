import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.lang.*;

class GeneFunctions extends GraphGenes {
	@Override
	double f(int x) {
		return 100 * RobotGene.TotalPerf[x]; 
	}
}