/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cosc202.andie;

import static cosc202.andie.EditActions.imageCheck;
import static cosc202.andie.ImageAction.target;
import java.awt.Toolkit;
import java.util.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Rotate menu.
 * </p>
 *
 * <p>
 * The Rotate menu contains actions that rotate the image by a given angle.
 * This includes 90 degrees clockwise, 90 degrees anticlockwise, and 180 degrees.
 * </p>
 *
 * @author leuhe253
 */
public class RotateActions {

     /** A @ResourceBundle that retrieves strings throughout the class in the proper language */
    private static ResourceBundle bundle = LanguageUtil.getBundle();
    public static int shortcut = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();


    /**
     * A list of actions for the Rotate menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Rotate menu actions.
     * </p>
     */
    public RotateActions() {
        actions = new ArrayList<>();
        actions.add(new CWAction(bundle.getString("90° CLOCKWISE"), null, bundle.getString("ROTATE CLOCKWISE 90"), KeyEvent.VK_C));
        actions.add(new ACWAction(bundle.getString("90° ANTICLOCKWISE"), null, bundle.getString("ROTATE ANTICLOCKWISE 90"), KeyEvent.VK_A));
        actions.add(new One80Action(bundle.getString("180° ROTATE"), null, bundle.getString("180° ROTATE"), KeyEvent.VK_O));

    }

    /**
     * <p>
     * Create a menu containing the list of Rotate actions.
     * </p>
     *
     * @return The rotate menu UI element.
     */
    public JMenu createMenu() {
        JMenu rotateMenu = new JMenu(bundle.getString("ROTATE"));

        for (Action action : actions) {
            rotateMenu.add(new JMenuItem(action));
        }

        return rotateMenu;
    }

    /**
     * <p>
     * ImageAction to rotate image 180°.
     * </p>
     *
     */
    private static class One80Action extends ImageAction {
        /**
         * <p>
         * Create a new One80 action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        One80Action(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, shortcut | InputEvent.SHIFT_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the rotate 180 action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the rotate 180 Action is triggered. It rotates an image 
         * 180°. 
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if(!imageCheck()){
                return;
            }
            target.getImage().apply(new ImageRotation180());
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * ImageAction to rotate image 90° anticlockwise.
     * </p>
     *
     */
    private static class ACWAction extends ImageAction {
        /**
         * <p>
         * Create a new ACW action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        ACWAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, shortcut));

        }
        /**
         * <p>
         * Callback for when the rotate 90° anticlockwise action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the rotate 90 ACW Action is triggered. It rotates an image 90° anticlockwise. 
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if(!imageCheck()){
                return;
            }
            target.getImage().apply(new ImageRotation90ACW());
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * ImageAction to rotate 90° image clockwise.
     * </p>
     *
     */
    public class CWAction extends ImageAction {

        /**
         * <p>
         * Create a rotate 90° clockwise action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        CWAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, shortcut));

        }

        /**
         * <p>
         * Callback for when the rotate 90° clockwise action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the rotate 90 CW Action is triggered. It rotates an image 90° clockwise.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!imageCheck()){
                return;
            }
            target.getImage().apply(new ImageRotation90clockwise());
            target.repaint();
            target.getParent().revalidate();
        }

    }

    
}
