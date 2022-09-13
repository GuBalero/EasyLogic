package classes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import javax.swing.text.Utilities;


public class TextLineNumber extends JPanel implements CaretListener, DocumentListener, PropertyChangeListener {

    private static final long serialVersionUID = 1;

    public final static float LEFT = 0.0f;
    public final static float CENTER = 0.5f;
    public final static float RIGHT = 1.0f;

    private final static Border OUTER = new MatteBorder(0, 0, 0, 2, Color.GRAY);

    private final static int HEIGHT = Integer.MAX_VALUE - 1000000;

    private JTextComponent component;

    private boolean updateFont;
    private int borderGap;
    private Color currentLineForeground;
    private float digitAlignment;
    private int minimumDisplayDigits;

    private int lastDigits;
    private int lastHeight;
    private int lastLine;

    private HashMap<String, FontMetrics> fonts;
    
    public static void main(String[] args) {
    	try {
            JTextArea areaDeTexto = new JTextArea();
            JScrollPane painelComBarraDeRolagem = new JScrollPane(areaDeTexto);
            TextLineNumber contadorLinhas = new TextLineNumber(areaDeTexto);
            painelComBarraDeRolagem.setRowHeaderView(contadorLinhas);

            JFrame janela = new JFrame("Exemplo");
            janela.add(BorderLayout.CENTER, painelComBarraDeRolagem);
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            janela.setSize(640, 480);
            janela.setLocationRelativeTo(null);
            janela.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
    public TextLineNumber(JTextComponent component) {
        this(component, 3);
    }

    public TextLineNumber(JTextComponent component, int minimumDisplayDigits) {
        this.component = component;
        setFont(component.getFont());
        setBorderGap(5);
        setCurrentLineForeground(Color.RED);
//        setBackground(Color.BLACK);
        setDigitAlignment(RIGHT);
        setMinimumDisplayDigits(minimumDisplayDigits);
        component.getDocument().addDocumentListener(this);
        component.addCaretListener(this);
        component.addPropertyChangeListener("font", this);
    }

    public boolean getUpdateFont() {
        return updateFont;
    }

    public void setUpdateFont(boolean updateFont) {
        this.updateFont = updateFont;
    }

    public int getBorderGap() {
        return borderGap;
    }

    public void setBorderGap(int borderGap) {
        this.borderGap = borderGap;
        Border inner = new EmptyBorder(0, borderGap, 0, borderGap);
        setBorder(new CompoundBorder(OUTER, inner));
        lastDigits = 0;
        setPreferredWidth();
    }

    public Color getCurrentLineForeground() {
        return currentLineForeground == null ? getForeground() : currentLineForeground;
    }

    public void setCurrentLineForeground(Color currentLineForeground) {
        this.currentLineForeground = currentLineForeground;
    }

    public float getDigitAlignment() {
        return digitAlignment;
    }

    public void setDigitAlignment(float digitAlignment) {
        this.digitAlignment = digitAlignment > 1.0f ? 1.0f : digitAlignment < 0.0f ? -1.0f : digitAlignment;
    }

    public int getMinimumDisplayDigits() {
        return minimumDisplayDigits;
    }

    public void setMinimumDisplayDigits(int minimumDisplayDigits) {
        this.minimumDisplayDigits = minimumDisplayDigits;
        setPreferredWidth();
    }

    private void setPreferredWidth() {
        Element root = component.getDocument().getDefaultRootElement();
        int lines = root.getElementCount();
        int digits = Math.max(String.valueOf(lines).length(), minimumDisplayDigits);
        if (lastDigits != digits) {
            lastDigits = digits;
            FontMetrics fontMetrics = getFontMetrics(getFont());
            int width = fontMetrics.charWidth('0') * digits;
            Insets insets = getInsets();
            int preferredWidth = insets.left + insets.right + width;

            Dimension d = getPreferredSize();
            d.setSize(preferredWidth, HEIGHT);
            setPreferredSize(d);
            setSize(d);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        FontMetrics fontMetrics = component.getFontMetrics(component.getFont());
        Insets insets = getInsets();
        int availableWidth = getSize().width - insets.left - insets.right;
        Rectangle clip = g.getClipBounds();
        int rowStartOffset = component.viewToModel(new Point(0, clip.y));
        int endOffset = component.viewToModel(new Point(0, clip.y + clip.height));
        while (rowStartOffset <= endOffset) {
            try {
                if (isCurrentLine(rowStartOffset)) {
                    g.setColor(getCurrentLineForeground());
                } else {
                    g.setColor(getForeground());
                }
                String lineNumber = getTextLineNumber(rowStartOffset);
                int stringWidth = fontMetrics.stringWidth(lineNumber);
                int x = getOffsetX(availableWidth, stringWidth) + insets.left;
                int y = getOffsetY(rowStartOffset, fontMetrics);
                g.drawString(lineNumber, x, y);
                rowStartOffset = Utilities.getRowEnd(component, rowStartOffset) + 1;
            } catch (Exception e) {
                break;
            }
        }
    }

    private boolean isCurrentLine(int rowStartOffset) {
        int caretPosition = component.getCaretPosition();
        Element root = component.getDocument().getDefaultRootElement();
        if (root.getElementIndex(rowStartOffset) == root.getElementIndex(caretPosition)) {
            return true;
        } else {
            return false;
        }
    }

    protected String getTextLineNumber(int rowStartOffset) {
        Element root = component.getDocument().getDefaultRootElement();
        int index = root.getElementIndex(rowStartOffset);
        Element line = root.getElement(index);
        if (line.getStartOffset() == rowStartOffset) {
            return String.valueOf(index + 1);
        } else {
            return "";
        }
    }

    private int getOffsetX(int availableWidth, int stringWidth) {
        return (int) ((availableWidth - stringWidth) * digitAlignment);
    }

    private int getOffsetY(int rowStartOffset, FontMetrics fontMetrics) throws BadLocationException {
        Rectangle r = component.modelToView(rowStartOffset);
        int lineHeight = fontMetrics.getHeight();
        int y = r.y + r.height;
        int descent = 0;
        if (r.height == lineHeight) {
            descent = fontMetrics.getDescent();
        } else {
            if (fonts == null)
                fonts = new HashMap<String, FontMetrics>();
            Element root = component.getDocument().getDefaultRootElement();
            int index = root.getElementIndex(rowStartOffset);
            Element line = root.getElement(index);
            for (int i = 0; i < line.getElementCount(); i++) {
                Element child = line.getElement(i);
                AttributeSet as = child.getAttributes();
                String fontFamily = (String) as.getAttribute(StyleConstants.FontFamily);
                Integer fontSize = (Integer) as.getAttribute(StyleConstants.FontSize);
                String key = fontFamily + fontSize;
                FontMetrics fm = fonts.get(key);
                if (fm == null) {
                    Font font = new Font(fontFamily, Font.PLAIN, fontSize);
                    fm = component.getFontMetrics(font);
                    fonts.put(key, fm);
                }
                descent = Math.max(descent, fm.getDescent());
            }
        }
        return y - descent;
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        int caretPosition = component.getCaretPosition();
        Element root = component.getDocument().getDefaultRootElement();
        int currentLine = root.getElementIndex(caretPosition);
        if (lastLine != currentLine) {
            repaint();
            lastLine = currentLine;
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        documentChanged();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        documentChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        documentChanged();
    }

    private void documentChanged() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    int endPos = component.getDocument().getLength();
                    Rectangle rect = component.modelToView(endPos);

                    if (rect != null && rect.y != lastHeight) {
                        setPreferredWidth();
                        repaint();
                        lastHeight = rect.y;
                    }
                } catch (BadLocationException ex) {}
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof Font) {
            if (updateFont) {
                Font newFont = (Font) evt.getNewValue();
                setFont(newFont);
                lastDigits = 0;
                setPreferredWidth();
            } else {
                repaint();
            }
        }
    }
}