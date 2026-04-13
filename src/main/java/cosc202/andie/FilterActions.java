package cosc202.andie;

import static cosc202.andie.EditActions.imageCheck;
import static cosc202.andie.ImageAction.target;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 *
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood. This includes a mean filter (a simple blur)
 * in the sample code, but more operations will need to be added.
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
public class FilterActions {

    /**
     * A @ResourceBundle that retrieves strings throughout the class in the
     * proper language
     */
    private static ResourceBundle bundle = LanguageUtil.getBundle();
    public int shortcut = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

    /**
     * A list of actions for the Filter menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */
    public FilterActions() {
        actions = new ArrayList<>();
        actions.add(new MeanFilterAction(bundle.getString("MEAN FILTER"), null, bundle.getString("APPLY A MEAN FILTER"), KeyEvent.VK_M));
        actions.add(new SharpenAction(bundle.getString("SHARPEN FILTER"), null, bundle.getString("APPLY A SHARPEN FILTER"), KeyEvent.VK_S));
        actions.add(new MedianFilterAction(bundle.getString("MEDIAN FILTER"), null, bundle.getString("APPLY A MEDIAN FILTER"), KeyEvent.VK_D));
        actions.add(new GaussianFilterAction(bundle.getString("GAUSSIAN FILTER"), null, bundle.getString("APPLY A GAUSSIAN BLUR FILTER"), KeyEvent.VK_G));

    }

    /**
     * <p>
     * Create a menu containing the list of Filter actions.
     * </p>
     *
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("FILTER"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     *
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean filter action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_M, shortcut | InputEvent.SHIFT_DOWN_MASK));

        }

        /**
         * <p>
         * Callback for when the mean filter action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the MeanFilterAction is triggered. It
         * prompts the user for a filter radius, then applies an appropriately
         * sized filter {@link MeanFilter}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!imageCheck()) {
                return;
            }
            // Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);

            //disable typing
            JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) radiusSpinner.getEditor();
            editor.getTextField().setEditable(false);

            int option = JOptionPane.showOptionDialog(null, radiusSpinner, bundle.getString("ENTER FILTER RADIUS"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }

            // Create and apply the filter
            target.getImage().apply(new MeanFilter(radius));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * ImageAction to sharpen an image.
     * </p>
     *
     * @see SharpenFilter
     */
    public class SharpenAction extends ImageAction {

        /**
         * <p>
         * Create a new sharpen action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        SharpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_H, shortcut | InputEvent.SHIFT_DOWN_MASK));

        }

        /**
         * <p>
         * Callback for when the sharpen filter action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the SharpenFilterAction is triggered.
         * It sharpens the pixels in an image {@link SharpenFilter}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!imageCheck()) {
                return;
            }
            target.getImage().apply(new SharpenFilter());
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to blur an image with a median filter.
     * </p>
     *
     * @see MedianFilter
     */
    public class MedianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new median filter action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        MedianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, shortcut | InputEvent.SHIFT_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the median filter action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the MedianFilterAction is triggered.
         * It applies the median filter to the entire image
         * {@link MedianFilter}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!imageCheck()) {
                return;
            }
            target.getImage().apply(new MedianFilter());
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * ImageAction to blur an image with a gaussian filter.
     * </p>
     *
     * @see GaussianFilter
     */
    public class GaussianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new Gaussian Filter action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        GaussianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_G, shortcut | InputEvent.SHIFT_DOWN_MASK));

        }

        /**
         * <p>
         * Callback for when the gaussian filter action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the GaussianFilterAction is triggered.
         * It prompts the user for a filter radius, then applies an
         * appropriately sized filter {@link GaussianFilter}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!imageCheck()) {
                return;
            }
            // Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);

            //disable text editing
            ((JSpinner.DefaultEditor) radiusSpinner.getEditor()).getTextField().setEditable(false);
            //((JSpinner.DefaultEditor) radiusSpinner.getEditor()).getTextField().setFocusable(false);
            //wrap panel to increase size
            JPanel panel = new JPanel();
            panel.add(radiusSpinner);
            panel.setPreferredSize(new Dimension(170, 50));
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, bundle.getString("ENTER FILTER RADIUS"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }

            // Create and apply the filter
            target.getImage().apply(new GaussianFilter(radius));
            target.repaint();
            target.getParent().revalidate();
        }
    }

}
