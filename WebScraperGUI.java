package WebScraperGUI.java;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

public class WebScraperGUI extends JFrame {

    private JComboBox<String> siteSelector;
    private JButton scrapeButton;
    private JTextArea outputArea;

    public WebScraperGUI() {
        setTitle("Web Scraper & Analyzer");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        siteSelector = new JComboBox<>(new String[]{"BBC News", "Wikipedia (Featured)", "Amazon (Sample)"});
        scrapeButton = new JButton("Scrape");
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        scrapeButton.addActionListener(e -> scrapeSite());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Site:"));
        topPanel.add(siteSelector);
        topPanel.add(scrapeButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void scrapeSite() {
        String selection = (String) siteSelector.getSelectedItem();
        String url = "";
        String selector = "h3";

        switch (selection) {
            case "BBC News":
                url = "https://www.bbc.com";
                selector = "h3";
                break;
            case "Wikipedia (Featured)":
                url = "https://en.wikipedia.org/wiki/Main_Page";
                selector = "#mp-tfa p";
                break;
            case "Amazon (Sample)":
                url = "https://www.amazon.in/s?k=phone";
                selector = "span.a-text-normal";
                break;
        }

        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
            Elements elements = doc.select(selector);

            List<String> items = new ArrayList<>();
            for (Element e : elements) {
                String text = e.text().trim();
                if (!text.isEmpty()) items.add(text);
            }

            outputArea.setText("");
            outputArea.append("🔎 Scraped Items from " + selection + "\n\n");
            for (String item : items) {
                outputArea.append("• " + item + "\n");
            }

            analyze(items);

        } catch (IOException ex) {
            outputArea.setText("❌ Error fetching data: " + ex.getMessage());
        }
    }

    private void analyze(List<String> data) {
        Map<String, Integer> freq = new HashMap<>();
        String longest = "";

        for (String line : data) {
            if (line.length() > longest.length()) longest = line;

            String[] words = line.toLowerCase().replaceAll("[^a-z ]", "").split("\\s+");
            for (String word : words) {
                if (word.length() > 2) {
                    freq.put(word, freq.getOrDefault(word, 0) + 1);
                }
            }
        }

        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(freq.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());

        outputArea.append("\n\n📊 Top Words:\n");
        for (int i = 0; i < Math.min(5, sorted.size()); i++) {
            Map.Entry<String, Integer> entry = sorted.get(i);
            outputArea.append(" - " + entry.getKey() + ": " + entry.getValue() + "\n");
        }

        outputArea.append("\n📝 Longest Text:\n" + longest + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WebScraperGUI::new);
    }
}
//javac -cp jsoup-1.16.1.jar WebScraperGUI.java
//java -cp .:jsoup-1.16.1.jar WebScraperGUI
