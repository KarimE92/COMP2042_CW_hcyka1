
package test;

import java.awt.*;

/**
 * Created by Karim on 09/12/2021
 * @author Karim
 * @since 2021/12/09
 */
public class GraphicsMain {
    /**
     * main function is ran to run the entire game
     * @param args an array of string arguments that are passed into main
     */
    public static void main(String[] args){
        EventQueue.invokeLater(() -> new GameFrame().initialize());
    }

}
