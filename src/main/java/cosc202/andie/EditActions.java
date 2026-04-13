package cosc202.andie;

import static cosc202.andie.ImageAction.target;
import java.awt.Toolkit;
import java.util.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Edit menu.
 * </p>
 *
 * <p>
 * The Edit menu is very common across a wide range of applications. There are a
 * lot of operations that a user might expect to see here. In the sample code
 * there are Undo and Redo actions, but more may need to be added.
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

public class EditActions {

    /**
     * A @ResourceBundle that retrieves strings throughout the class in the
     * proper language
     */
    private static ResourceBundle bundle = LanguageUtil.getBundle();
    public int shortcut = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

    /**
     * A list of actions for the Edit menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     */
    public EditActions() {
        actions = new ArrayList<>();
        actions.add(new UndoAction(bundle.getString("UNDO"), null, bundle.getString("UNDO"), KeyEvent.VK_Z));
        actions.add(new RedoAction(bundle.getString("REDO"), null, bundle.getString("REDO"), KeyEvent.VK_Y));
        actions.add(new ResizeAction(bundle.getString("RESIZE"), null, bundle.getString("RESIZE"), KeyEvent.VK_X));
        actions.add(new HorizontalFlipAction(bundle.getString("FLIP - HORIZONTAL"), null, bundle.getString("FLIP IMAGE HORIZONTALLY"), KeyEvent.VK_H));
        actions.add(new VerticalFlipAction(bundle.getString("FLIP - VERTICAL"), null, bundle.getString("FLIP IMAGE VERTICALLY"), KeyEvent.VK_V));

    }

    /**
     * <p>
     * Create a menu containing the list of Edit actions.
     * </p>
     *
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        JMenu editMenu = new JMenu(bundle.getString("EDIT"));

        for (Action action : actions) {
            editMenu.add(new JMenuItem(action));
        }

        return editMenu;
    }

    /**
     * <p>
     * ImageAction to resize an image
     * </p>
     *
     * @see ImageResize
     */
    public class ResizeAction extends ImageAction {

        /**
         * <p>
         * Create a new resize action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        ResizeAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_R, shortcut));

        }

        /**
         * <p>
         * Callback for when the resize action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ResizeAction is triggered. It asks
         * the user for a scale factor given as a percent and checks to make
         * sure the input is between 0-300, if it is outside of this range or is
         * not an integer, the program informs the user of the issue and prompts
         * them to enter an appropriate scale factor. With a valid input it
         * resizes the given image by the scale factor.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!imageCheck()) {
                return;
            }
            while (true) {
                String input = JOptionPane.showInputDialog(bundle.getString("ENTER SCALE FACTOR: "));
                try {
                    if (input == null) {
                        return;
                    }
                    int scaleFactor = Integer.parseInt(input);
                    if (scaleFactor <= 0) {
                        JOptionPane.showMessageDialog(null, bundle.getString("SCALE FACTOR CANNOT BE NEGATIVE"));
                        continue;
                    }
                    if (scaleFactor > 300) {
                        JOptionPane.showMessageDialog(null, bundle.getString("SCALE FACTOR TOO LARGE"));
                        continue;
                    }
                    target.getImage().apply(new ImageResize(scaleFactor));
                    target.repaint();
                    target.getParent().revalidate();
                    break;

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, bundle.getString("PLEASE ENTER AN INTEGER"));
                }

            }
        }
    }

    /**
     * <p>
     * Action to undo an {@link ImageOperation}.
     * </p>
     *
     * @see EditableImage#undo()
     */
    public class UndoAction extends ImageAction {

        /**
         * <p>
         * Create a new undo action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        UndoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_Z, shortcut));

        }

        /**
         * <p>
         * Callback for when the undo action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the UndoAction is triggered. It undoes
         * the most recently applied operation.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!imageCheck()) {
                return;
            }
            target.getImage().undo();
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to redo an {@link ImageOperation}.
     * </p>
     *
     * @see EditableImage#redo()
     */
    public class RedoAction extends ImageAction {

        /**
         * <p>
         * Create a new redo action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        RedoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_Z, shortcut | InputEvent.SHIFT_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the redo action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the RedoAction is triggered. It redoes
         * the most recently undone operation.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!imageCheck()) {
                return;
            }
            target.getImage().redo();
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * ImageAction to flips an image horizontally
     * </p>
     *
     * @see HorizontalFlip
     */
    public class HorizontalFlipAction extends ImageAction {

        /**
         * <p>
         * Create a new HorizontalFlip action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        HorizontalFlipAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_F, shortcut));
        }

        /**
         * <p>
         * Callback for when the HorizontalFlip action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the HorizontalFlip is triggered. It
         * flips the image horizontally.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!imageCheck()) {
                return;
            }
            target.getImage().apply(new HorizontalFlip());
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * ImageAction to flips an image vertically
     * </p>
     *
     * @see VerticalFlip
     */
    public class VerticalFlipAction extends ImageAction {

        /**
         * <p>
         * Create a new VerticalFlip action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        VerticalFlipAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_F, shortcut | InputEvent.SHIFT_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the VerticalFlip action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the VerticalFlip is triggered. It
         * flips an image vertically.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!imageCheck()) {
                return;
            }
            target.getImage().apply(new VerticalFlip());
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * tracks the number of times a user tries to apply a filter without opening
     * an image, set to 0 at the start
     */
    private static int errorCount = 0;

    /**
     * List of meme image paths used for error feedback.
     * Cycles through these when user repeatedly triggers actions without an image.
     */
    private static final String[] MEMES = {"/meme1.jpg", "/meme.jpg", "/meme2.jpg", "/meme3.jpg"};
    /**
     * Keeps track of which meme to show next
     */
    private static int memeIndex = 0;

    /**
     * <p>
     * Checks if an image has been uploaded and keeps track of how many times an
     * image has not been uploaded
     * </p>
     *
     * <p>
     * This checks if an image has been uploaded and tells the user to open an
     * image if it has not been opened the first three times. If the user does
     * not open an image more than three times then a meme shows up instead.
     * </p>
     *
     * @return true if an image is open, false otherwise
     */
    public static boolean imageCheck() {
        if (!target.getImage().hasImage()) {
            errorCount++;

            if (errorCount >= 3) {
                ImageIcon meme = new ImageIcon(ImageAction.class.getResource(MEMES[memeIndex]));
                JOptionPane.showMessageDialog(
                        target,
                        meme,
                        "Bro...",
                        JOptionPane.PLAIN_MESSAGE
                );

                //rotate to next meme
                memeIndex = (memeIndex + 1) % MEMES.length;

            } else {
                JOptionPane.showMessageDialog(target, bundle.getString("PLEASE OPEN AN IMAGE"));
            }
            return false;
        }

        // reset once an image is opened :)
        errorCount = 0;
        return true;

    }
}
