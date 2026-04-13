package cosc202.andie;

import static cosc202.andie.EditActions.imageCheck;
import static cosc202.andie.ImageAction.target;
import java.awt.Toolkit;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the View menu.
 * </p>
 *
 * <p>
 * The View menu contains actions that affect how the image is displayed in the
 * application. These actions do not affect the contents of the image itself,
 * just the way it is displayed.
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
public class ViewActions {

    /** A @ResourceBundle that retrieves strings throughout the class in the proper language */
    private static ResourceBundle bundle = LanguageUtil.getBundle();
    public int shortcut = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();


    /**
     * A list of actions for the View menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of View menu actions.
     * </p>
     */
    public ViewActions() {
        actions = new ArrayList<>();
        actions.add(new ZoomInAction(bundle.getString("ZOOM IN"), null, bundle.getString("ZOOM IN"), KeyEvent.VK_PLUS));
        actions.add(new ZoomOutAction(bundle.getString("ZOOM OUT"), null, bundle.getString("ZOOM OUT"), KeyEvent.VK_MINUS));
        actions.add(new ZoomFullAction(bundle.getString("ZOOM FULL"), null, bundle.getString("ZOOM FULL"), KeyEvent.VK_1));
    }

    /**
     * <p>
     * Create a menu containing the list of View actions.
     * </p>
     *
     * @return The view menu UI element.
     */
    public JMenu createMenu() {
        JMenu viewMenu = new JMenu(bundle.getString("VIEW"));

        for (Action action : actions) {
            viewMenu.add(new JMenuItem(action));
        }

        return viewMenu;
    }

    /**
     * <p>
     * Action to zoom in on an image.
     * </p>
     *
     * <p>
     * Note that this action only affects the way the image is displayed, not
     * its actual contents.
     * </p>
     */
    public class ZoomInAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-in action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        ZoomInAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, shortcut | InputEvent.SHIFT_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the zoom-in action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ZoomInAction is triggered. It
         * increases the zoom level by 10%, to a maximum of 200%.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
              if(!imageCheck()){
                return;
            }
            target.setZoom(target.getZoom() + 10);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to zoom out of an image.
     * </p>
     *
     * <p>
     * Note that this action only affects the way the image is displayed, not
     * its actual contents.
     * </p>
     */
    public class ZoomOutAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-out action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        ZoomOutAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, shortcut | InputEvent.SHIFT_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the zoom-out action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ZoomOutAction is triggered. It
         * decreases the zoom level by 10%, to a minimum of 50%.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
              if(!imageCheck()){
                return;
            }
            target.setZoom(target.getZoom() - 10);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to reset the zoom level to actual size.
     * </p>
     *
     * <p>
     * Note that this action only affects the way the image is displayed, not
     * its actual contents.
     * </p>
     */
    public class ZoomFullAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-full action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        ZoomFullAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_0, shortcut | InputEvent.SHIFT_DOWN_MASK));

        }

        /**
         * <p>
         * Callback for when the zoom-full action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ZoomFullAction is triggered. It
         * resets the Zoom level to 100%.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
              if(!imageCheck()){
                return;
            }
            target.setZoom(100);
            target.repaint();
            target.getParent().revalidate();
        }

    }
    

}
