/*
         * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
         * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prolab.test;

import info.InfoFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import prolab.grid.Grid;
import prolab.model.Location;
import prolab.service.GameObjectManager;
import prolab.service.PathManager;

/**
 *
 * @author kaan
 */
public class Test {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Otonom Hazine Avcisi");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setSize(2000, 2000);

            JPanel startingPanel = new JPanel();

            JTextField height = new JTextField("Height");

            JTextField width = new JTextField("Width");

            JButton createMapButton = new JButton();

            JButton startButton = new JButton();

            createMapButton.setText("Create Random Map");

            startButton.setText("Start");

            startingPanel.add(height);

            startingPanel.add(width);

            startingPanel.add(createMapButton);

            startingPanel.add(startButton);

            frame.add(startingPanel, BorderLayout.EAST);

            Grid tablo = Grid.getInstance((byte) 5, 0, 0);

            GameObjectManager gom = GameObjectManager.getInstance();

            createMapButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int heightValue = Integer.parseInt(height.getText());
                        int widthValue = Integer.parseInt(width.getText());
                        if (heightValue <= 0 || widthValue <= 0) {
                            throw new NumberFormatException();
                        }
                        tablo.setWidth(widthValue);
                        tablo.setHeight(heightValue);
                        tablo.setGridMatrixDimension();
                        if (gom.getGameObject().isEmpty()) {
                            gom.createGameObjectsRandomly("kaan", tablo);
                        } else {
                            gom.clearObjects(tablo);
                            gom.createGameObjectsRandomly("kaan", tablo);
                        }
                        frame.getContentPane().add(tablo, BorderLayout.CENTER);
                    } catch (NumberFormatException | InputMismatchException | IOException ex) {
                        if (ex instanceof IOException) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        } else {
                            JOptionPane.showMessageDialog(null, "Please enter valid number");
                        }
                    }

                    tablo.setVisible(true);

                    frame.setVisible(true);

                }

            });

            startButton.addActionListener(new ActionListener() {

                private JButton button = createMapButton;

                @Override
                public void actionPerformed(ActionEvent e) {
                    createMapButton.setEnabled(false);
                    PathManager pm = PathManager.getInstance();
                    List<Location> path = pm.search(gom.getTreasureList());
                    tablo.setPath(path);
                    new InfoFrame().setVisible(true);

                }
            });

            tablo.setVisible(true);

            frame.setVisible(true);

        }
        );

    }
}
