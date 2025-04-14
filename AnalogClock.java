import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

public class AnalogClock extends JPanel implements Runnable {

    private Thread clockThread;
    private boolean darkMode = true;

    public AnalogClock() {
        setPreferredSize(new Dimension(400, 400));
        setBackground(darkMode ? Color.BLACK : Color.WHITE);

        // Thread for real-time clock
        clockThread = new Thread(this);
        clockThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Enable anti-aliasing for smoother graphics
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Calendar cal = Calendar.getInstance();
        int hours = cal.get(Calendar.HOUR);
        int minutes = cal.get(Calendar.MINUTE);
        int seconds = cal.get(Calendar.SECOND);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = 150;

        Color backgroundColor = darkMode ? Color.BLACK : Color.WHITE;
        Color numberColor = darkMode ? Color.WHITE : Color.BLACK;
        Color handColor = darkMode ? Color.WHITE : Color.BLACK;

        setBackground(backgroundColor);

        // Clock circle
        g2.setColor(numberColor);
        g2.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

        // Clock numbers
        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(i * 30 - 90);
            int x = (int)(centerX + Math.cos(angle) * (radius - 30));
            int y = (int)(centerY + Math.sin(angle) * (radius - 30));
            g2.drawString(String.valueOf(i), x - 5, y + 5);
        }

        // Calculate angles
        double secondAngle = Math.toRadians(seconds * 6 - 90);
        double minuteAngle = Math.toRadians(minutes * 6 - 90);
        double hourAngle = Math.toRadians((hours % 12 + minutes / 60.0) * 30 - 90);

        // Draw hands
        drawHand(g2, centerX, centerY, secondAngle, radius - 20, Color.RED);       // Second
        drawHand(g2, centerX, centerY, minuteAngle, radius - 40, Color.BLUE);      // Minute
        drawHand(g2, centerX, centerY, hourAngle, radius - 60, handColor);         // Hour
    }

    private void drawHand(Graphics2D g, int x, int y, double angle, int length, Color color) {
        int xEnd = (int)(x + Math.cos(angle) * length);
        int yEnd = (int)(y + Math.sin(angle) * length);
        g.setColor(color);
        g.drawLine(x, y, xEnd, yEnd);
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void toggleTheme() {
        darkMode = !darkMode;
        setBackground(darkMode ? Color.BLACK : Color.WHITE);
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Analog Clock - Dark/Light Mode");

        AnalogClock clock = new AnalogClock();
        JButton toggleButton = new JButton("Toggle Theme");

        toggleButton.addActionListener(e -> {
            clock.toggleTheme();
            toggleButton.setBackground(clock.darkMode ? Color.DARK_GRAY : Color.LIGHT_GRAY);
            toggleButton.setForeground(clock.darkMode ? Color.WHITE : Color.BLACK);
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(toggleButton);

        frame.setLayout(new BorderLayout());
        frame.add(clock, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
