/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customViews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Jonathan Idy
 */
public class RoundJButton extends JButton {
    ImageIcon icon;
     public RoundJButton(ImageIcon icon) {
    super();
    this.icon = icon;
    setBackground(Color.white);
    ///setIcon(icon);
    setFocusable(false);
    /*
     These statements enlarge the button so that it 
     becomes a circle rather than an oval.
    */
    Dimension size = getPreferredSize();
    size.width = size.height = Math.max(20, Math.min(size.width, size.height));
    setPreferredSize(size);
    setMaximumSize(new Dimension(32,32));
    setMinimumSize(new Dimension(20,20));
 
    /*
     This call causes the JButton not to paint the background.
     This allows us to paint a round background.
    */
    setContentAreaFilled(false);
  }
 
    @Override
  protected void paintComponent(Graphics g) {
    if (getModel().isArmed()) {
      g.setColor(Color.gray);
    } else {
      g.setColor(getBackground());
    }
    g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
   /// g.fillOval(0, 0, 24, 24);
    g.drawImage(icon.getImage(), 2, 2, getSize().width - 5, getSize().height - 5, null);
    super.paintComponent(g);
  }
 
    @Override
  protected void paintBorder(Graphics g) {
    g.setColor(Color.gray.brighter());
    g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    ///g.drawOval(0, 0, 24, 24);
  }
 
  // Hit detection.
  Shape shape;
 
    @Override
  public boolean contains(int x, int y) {
    // If the button has changed size,  make a new shape object.
    if (shape == null || !shape.getBounds().equals(getBounds())) {
      shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
      ///shape = new Ellipse2D.Float(0, 0, 24, 24);
    }
    return shape.contains(x, y);
  }
}
