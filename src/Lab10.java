import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Implementor interface defining the method for painting a button.
 */
interface ButtonPainter {
    /**
     * Paints the button.
     * @param g The graphics context.
     * @param button The button to paint.
     */
    void paintButton(Graphics g, JButton button);
}

/**
 * ConcreteImplementor A for painting a button red.
 */
class RedButtonPainter implements ButtonPainter {
    /**
     * Paints the button red.
     * @param g The graphics context.
     * @param button The button to paint.
     */
    public void paintButton(Graphics g, JButton button) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, button.getWidth(), button.getHeight());
    }
}

/**
 * ConcreteImplementor B for painting a button green.
 */
class GreenButtonPainter implements ButtonPainter {
    /**
     * Paints the button green.
     * @param g The graphics context.
     * @param button The button to paint.
     */
    public void paintButton(Graphics g, JButton button) {
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, button.getWidth(), button.getHeight());
    }
}

/**
 * New ConcreteImplementor C for painting a button with a solid color.
 */
class SolidColorButtonPainter implements ButtonPainter {
    private Color color;

    /**
     * Constructs a SolidColorButtonPainter with the specified color.
     * @param color The color to paint the button.
     */
    public SolidColorButtonPainter(Color color) {
        this.color = color;
    }

    /**
     * Paints the button with the specified color.
     * @param g The graphics context.
     * @param button The button to paint.
     */
    public void paintButton(Graphics g, JButton button) {
        g.setColor(color);
        g.fillRect(0, 0, button.getWidth(), button.getHeight());
    }
}

/**
 * Abstraction abstract class defining the paint method for buttons.
 */
abstract class Button {
    protected ButtonPainter painter;

    /**
     * Constructs a Button with the specified painter.
     * @param painter The painter to use for painting the button.
     */
    public Button(ButtonPainter painter) {
        this.painter = painter;
    }

    /**
     * Paints the button using the specified graphics context.
     * @param g The graphics context.
     * @param button The button to paint.
     */
    public abstract void paint(Graphics g, JButton button);
}

/**
 * RefinedAbstraction A for a colored button.
 */
class ColorButton extends JButton {
    private Button button;

    /**
     * Constructs a ColorButton with the specified button.
     * @param button The button to paint.
     */
    public ColorButton(Button button) {
        this.button = button;
        this.addMouseListener(new CustomButtonMouseListener(this)); // Add mouse listener to track events
    }

    /**
     * Paints the component using the specified graphics context.
     * @param g The graphics context.
     */
    @Override
    protected void paintComponent(Graphics g) {
        button.paint(g, this);
        super.paintComponent(g);
    }
}

/**
 * Concrete Abstraction A for a red button.
 */
class RedButton extends Button {
    /**
     * Constructs a RedButton with the specified painter.
     * @param painter The painter to use for painting the button.
     */
    public RedButton(ButtonPainter painter) {
        super(painter);
    }

    /**
     * Paints the button using the specified graphics context.
     * @param g The graphics context.
     * @param button The button to paint.
     */
    @Override
    public void paint(Graphics g, JButton button) {
        painter.paintButton(g, button);
    }
}

/**
 * Concrete Abstraction B for a green button.
 */
class GreenButton extends Button {
    /**
     * Constructs a GreenButton with the specified painter.
     * @param painter The painter to use for painting the button.
     */
    public GreenButton(ButtonPainter painter) {
        super(painter);
    }

    /**
     * Paints the button using the specified graphics context.
     * @param g The graphics context.
     * @param button The button to paint.
     */
    @Override
    public void paint(Graphics g, JButton button) {
        painter.paintButton(g, button);
    }
}

/**
 * Custom MouseListener implementation for changing button color on mouse press and release.
 */
class CustomButtonMouseListener implements MouseListener {
    private ColorButton button;
    private Color originalColor; // Store original color

    /**
     * Constructs a CustomButtonMouseListener with the specified button.
     * @param button The button to track mouse events.
     */
    public CustomButtonMouseListener(ColorButton button) {
        this.button = button;
        this.originalColor = button.getBackground(); // Get original color
    }

    /**
     * Changes button color on mouse press.
     * @param e The MouseEvent.
     */
    public void mousePressed(MouseEvent e) {
        button.setBackground(Color.LIGHT_GRAY); // Change color when pressed
    }

    /**
     * Resets button color on mouse release.
     * @param e The MouseEvent.
     */
    public void mouseReleased(MouseEvent e) {
        button.setBackground(null); // Reset color when released
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}

/**
 * Main class to demonstrate the Bridge Pattern Example.
 */
public class Lab10 {
    /**
     * Main method to start the application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bridge Pattern Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());

            // Create buttons using different painters
            ButtonPainter redPainter = new RedButtonPainter();
            ButtonPainter greenPainter = new GreenButtonPainter();
            ButtonPainter solidPainter = new SolidColorButtonPainter(Color.BLUE); // New painter

            ColorButton redButton = new ColorButton(new RedButton(redPainter));
            ColorButton greenButton = new ColorButton(new GreenButton(greenPainter));
            ColorButton blueButton = new ColorButton(new RedButton(solidPainter)); // Use new painter

            redButton.setText("Red Button");
            greenButton.setText("Green Button");
            blueButton.setText("Blue Button");

            redButton.setPreferredSize(new Dimension(100, 50));
            greenButton.setPreferredSize(new Dimension(100, 50));
            blueButton.setPreferredSize(new Dimension(100, 50));

            frame.add(redButton);
            frame.add(greenButton);
            frame.add(blueButton); // Add blue button

            frame.setSize(350, 200);
            frame.setVisible(true);
        });
    }
}
