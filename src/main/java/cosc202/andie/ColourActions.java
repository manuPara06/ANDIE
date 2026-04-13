package cosc202.andie;

import static cosc202.andie.EditActions.imageCheck;
import static cosc202.andie.ImageAction.target;
import java.awt.Toolkit;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 *
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel
 * directly without reference to the rest of the image. This includes conversion
 * to greyscale in the sample code, threshold, invert image and colour channel swap.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 * @author Steven Mills
 * @version 1.0
 */
public class ColourActions {
    
    /** A @ResourceBundle that retrieves strings throughout the class in the proper language */
    private static ResourceBundle bundle = LanguageUtil.getBundle();
    public static int shortcut = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();


    /**
     * A list of actions for the Colour menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<>();
        actions.add(new ConvertToGreyAction(bundle.getString("GREYSCALE"), null, bundle.getString("CONVERT TO GREYSCALE"), KeyEvent.VK_G));
        actions.add(new ThresholdAction(bundle.getString("THRESHOLD"), null, bundle.getString("APPLY THRESHOLD"), KeyEvent.VK_T));
        actions.add(new InversionAction(bundle.getString("INVERSION"), null, bundle.getString("APPLY INVERSION"), KeyEvent.VK_I));
        actions.add(new ColorChannelSwapAction(bundle.getString("COLOR CHANNEL SWAP"), null, bundle.getString("COLOR CHANNEL SWAP"), KeyEvent.VK_Z));
    }

    /**
     * <p>
     * Create a menu containing the list of Colour actions.
     * </p>
     *
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("COLOUR"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to invert an image.
     * </p>
     *
     * @see ImageInversion
     */
    private static class InversionAction extends ImageAction {
        
        /**
         * <p>
         * Create a new invert image action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        public InversionAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_I, shortcut | InputEvent.SHIFT_DOWN_MASK));
        }
        
        /**
         * <p>
         * Callback for when the invert image action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the InversionAction is triggered.
         * It inverts the image.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!imageCheck()){
                return;
            }
            target.getImage().apply(new ImageInversion());
            target.repaint();
            target.getParent().revalidate();
        }

    }
    
    /**
     * <p>
     * ImageAction to apply the threshold conversion to an image
     * </p>
     * @see ImageThresholding
     */
    public class ThresholdAction extends ImageAction {
        
        /**
         * <p>
         * Create a new threshold image action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        ThresholdAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_T, shortcut | InputEvent.SHIFT_DOWN_MASK));

        }

        /**
         * <p>
         * Callback for when the threshold action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ThresholdAction is triggered. It asks the user for a threshold 
         * between 0-255 and checks to make sure the input is valid. If the input is outside of this 
         * range or is not an integer, the program informs the user of the issue and prompts them to enter
         * an appropriate threshold value. With a valid input, it applies the threshold conversion to the given image. 
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!imageCheck()){
                return;
            }
            while (true) {
                String input = JOptionPane.showInputDialog("Enter threshold value between 0-255: ");
                if (input == null) {
                    return;
                }
                try {
                    int threshold = Integer.parseInt(input);
                    if (threshold < 0 || threshold > 255) {
                        JOptionPane.showMessageDialog(null, "Integer must be between 0 and 255");
                        continue;
                    }
                    target.getImage().apply(new ImageThresholding(threshold));
                    target.repaint();
                    target.getParent().revalidate();
                    break;

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter an integer");

                }
            }
        }
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     *
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_Y, shortcut | InputEvent.SHIFT_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!imageCheck()){
                return;
            }
            target.getImage().apply(new ConvertToGrey());
            target.repaint();
            target.getParent().revalidate();
        }

    }
    
    /**
     * <p>
     * Action to swap color channels in an image
     * </p>
     *
     * @see ColorChannelSwap
     */
    private static class ColorChannelSwapAction extends ImageAction {
        
         /**
         * <p>
         * Create a new color channel swap action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        public ColorChannelSwapAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        
        /**
         * <p>
         * Callback for when the Color channel swap action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ColorChannelSwapAction is triggered.
         * It changes the image color channels.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!imageCheck()){
                return;
            }
            String[] options = {"RGB","RBG", "GRB", "GBR", "BRG", "BGR"};

            String choice = (String) JOptionPane.showInputDialog(
                    null,
                    bundle.getString("CHOOSE CHANNEL ORDER: "),
                    bundle.getString("COLOR CHANNEL SWAP"),
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice != null) {
                target.getImage().apply(new ColorChannelSwap(choice));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }

    

}
