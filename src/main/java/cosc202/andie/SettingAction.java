/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cosc202.andie;

import java.awt.Toolkit;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Provides language settings actions for the ANDIE menu.
 *
 * @author manuella
 */
public class SettingAction {

    /**
     * A @ResourceBundle that retrieves strings throughout the class in the
     * proper language
     */
    private static ResourceBundle bundle = LanguageUtil.getBundle();
    public int shortcut = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

    protected ArrayList<Action> actions;

    /**
     * Initialises the settings menu with available language options.
     */
    public SettingAction() {
        actions = new ArrayList<>();
        actions.add(new SetEnglishAction(bundle.getString("ENGLISH"), null, bundle.getString("ENGLISH"), null));
        actions.add(new SetGermanAction(bundle.getString("GERMAN"), null, bundle.getString("GERMAN"), null));

    }

    /**
     * Builds and returns the settings JMenu.
     *
     * @return the populated settings menu
     */
    public JMenu createMenu() {
        JMenu settingsMenu = new JMenu(bundle.getString("SETTINGS"));
        for (Action action : actions) {
            settingsMenu.add(new JMenuItem(action));
        }
        return settingsMenu;
    }

    /**
     * AbstractAction to switch the application language to English.
     */
    public class SetEnglishAction extends AbstractAction {

        /**
         * <p>
         * Create a new SetEnglishAction.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        SetEnglishAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, shortcut));

        }

        /**
         * <p>
         * Updates application to English using {@code LanguageUtil}. Then
         * displays a message dialog telling the user to restart the application
         * to see the language change.
         * <p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            LanguageUtil.setLanguage("NZ", "en");
            JOptionPane.showMessageDialog(null, bundle.getString("PLEASE RESTART ANDIE TO APPLY LANGUAGE CHANGE."));
        }
    }

    /**
     * AbstractAction to switch the application language to German.
     */
    public class SetGermanAction extends AbstractAction {

        /**
         * <p>
         * Create a new SetGermanAction.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        SetGermanAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_G, shortcut));

        }

        /**
         * <p>
         * Updates application to German using {@code LanguageUtil}. Then
         * displays a message dialog telling the user to restart the application
         * to see the language change.
         * <p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            LanguageUtil.setLanguage("DE", "de");
            JOptionPane.showMessageDialog(null, bundle.getString("PLEASE RESTART ANDIE TO APPLY LANGUAGE CHANGE."));
        }
    }
}
